package com.udacity.stockhawk.ui.StockDetailsActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by emad on 3/14/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public Context mContext;
    public ArrayList<StockHistory> mStockHistory;

    public HistoryAdapter(Context context, ArrayList<StockHistory> stockHistory) {
        mContext = context;
        mStockHistory = stockHistory;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.date.setText("Date : " + mStockHistory.get(position).getTime().toString());
        holder.closed_at.setText("Closed At : $"+mStockHistory.get(position).getClosedAt());
    }

    @Override
    public int getItemCount() {
        return mStockHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.history_date)
        TextView date;
        @BindView(R.id.history_closed_at)
        TextView closed_at;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
