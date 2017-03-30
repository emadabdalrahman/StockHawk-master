package com.udacity.stockhawk.ui.StockDetailsActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StockDetailsActivity extends AppCompatActivity {
    @BindView(R.id.details_symbol)
    TextView symbol_tv;
    @BindView(R.id.details_price)
    TextView price_tv;
    @BindView(R.id.details_absolute_change)
    TextView absolute_change_tv;
    @BindView(R.id.details_percentage_change)
    TextView percentage_change_tv;
    private DecimalFormat dollarFormatWithPlus;
    private DecimalFormat dollarFormat;
    private DecimalFormat percentageFormat;
    public StockData stockData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        ButterKnife.bind(this);

        stockData = new StockData();
        stockData = getStockData();
        initializeHeader();

        GraphView graphView = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoint = new DataPoint[stockData.getStockHistories().size()];
        for (int i = 0; i < stockData.getStockHistories().size(); i++) {
            String price = stockData.getStockHistories().get(i).getClosedAt();
            Double x = Double.parseDouble(price);
            dataPoint[i] = new DataPoint(i,x);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);
        graphView.addSeries(series);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setGridColor(Color.WHITE);
        graphView.getGridLabelRenderer().setHumanRounding(false);


        initializeRecyclerView();
    }

    public ArrayList<StockHistory> getHistory(String history) {
        ArrayList<StockHistory> stockHistories = new ArrayList<>();

        for (int i = 0; i < history.length(); i++) {

            StockHistory stockHistory = new StockHistory();
            String date = history.substring(i, i + 12);
            stockHistory.setDate(date);

            Long dateInMilliSecond = Long.parseLong(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMilliSecond);
            Date Time = calendar.getTime();
            stockHistory.setTime(Time);

            i = i + 15;
            StringBuilder stringBuilder = new StringBuilder();
            while (!Objects.equals("" + history.charAt(i) + "", "\n")) {
                stringBuilder.append(history.charAt(i));
                i++;
            }
            stockHistory.setClosedAt(stringBuilder.toString());
            stockHistories.add(stockHistory);
        }
        return stockHistories;
    }

    public StockData getStockData() {

        StockData stockData = new StockData();
        String symbol = getIntent().getStringExtra("symbol");
        Cursor cursor = getContentResolver().query(Contract.Quote.URI, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(Contract.Quote.POSITION_SYMBOL).equals(symbol)) {

                stockData.setSymbol(cursor.getString(Contract.Quote.POSITION_SYMBOL));
                stockData.setPrice(cursor.getFloat(Contract.Quote.POSITION_PRICE));
                stockData.setAbsoluteChange(cursor.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE));
                stockData.setPercentageChange(cursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE));
                stockData.setStockHistories(getHistory(cursor.getString(Contract.Quote.POSITION_HISTORY)));

            }
        }
        return stockData;

    }

    public void initializeHeader(){
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

        symbol_tv.setText(stockData.getSymbol());
        price_tv.setText(dollarFormat.format(stockData.getPrice()));

        float absoluteChange = stockData.getAbsoluteChange();
        float percentageChange = stockData.getPercentageChange();

        if (absoluteChange>0){
            absolute_change_tv.setBackgroundResource(R.drawable.percent_change_pill_green);
            percentage_change_tv.setBackgroundResource(R.drawable.percent_change_pill_green);
        }else {
            absolute_change_tv.setBackgroundResource(R.drawable.percent_change_pill_red);
            percentage_change_tv.setBackgroundResource(R.drawable.percent_change_pill_red);
        }

        absolute_change_tv.setText(dollarFormatWithPlus.format(absoluteChange));
        percentage_change_tv.setText(percentageFormat.format(percentageChange/1000));

    }

    public void initializeRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.details_history);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        HistoryAdapter adapter = new HistoryAdapter(this, stockData.getStockHistories());
        recyclerView.setAdapter(adapter);
    }

}