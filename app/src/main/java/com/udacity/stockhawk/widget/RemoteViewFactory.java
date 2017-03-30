package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;
import com.udacity.stockhawk.data.StockProvider;
import com.udacity.stockhawk.ui.MainActivity;
import com.udacity.stockhawk.ui.StockDetailsActivity.StockDetailsActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by emad on 3/6/2017.
 */

public class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    public Context mContext;
    public int mWidgetId;
    public Cursor mCursor;
    private  DecimalFormat dollarFormatWithPlus;
    private  DecimalFormat dollarFormat;
    private  DecimalFormat percentageFormat;

    public RemoteViewFactory(Context context,Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
        mCursor = mContext.getContentResolver().query(Contract.Quote.URI,null,null,null,null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views ;

        mCursor.moveToPosition(position);

        float rawAbsoluteChange = mCursor.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        float percentageChange = mCursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);

        String change = dollarFormatWithPlus.format(rawAbsoluteChange);
        String percentage = percentageFormat.format(percentageChange / 100);

        if (rawAbsoluteChange > 0) {
            views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item_quote_green );
        } else {
            views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item_quote_red );
        }


        views.setTextViewText(R.id.symbol,mCursor.getString(Contract.Quote.POSITION_SYMBOL));
        views.setTextViewText(R.id.price,dollarFormat.format(mCursor.getFloat(Contract.Quote.POSITION_PRICE)));

        if (PrefUtils.getDisplayMode(mContext)
                .equals(mContext.getString(R.string.pref_display_mode_absolute_key))) {
            views.setTextViewText(R.id.change,change);
        } else {
            views.setTextViewText(R.id.change,percentage);
        }

        Intent detailsIntent = new Intent(mContext, StockDetailsActivity.class);
        detailsIntent.putExtra("symbol",mCursor.getString(Contract.Quote.POSITION_SYMBOL));

        views.setOnClickFillInIntent(R.id.list_item_frame,detailsIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
