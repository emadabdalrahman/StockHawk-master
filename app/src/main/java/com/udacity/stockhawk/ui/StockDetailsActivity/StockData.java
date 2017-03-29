package com.udacity.stockhawk.ui.StockDetailsActivity;

import java.util.ArrayList;

/**
 * Created by emad on 3/14/2017.
 */

public class StockData {
    private String mSymbol;
    private float mPrice;
    private float mAbsoluteChange;
    private float mPercentageChange;
    private ArrayList<StockHistory> mStockHistories;

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public float getAbsoluteChange() {
        return mAbsoluteChange;
    }

    public void setAbsoluteChange(float mAbsoluteChange) {
        this.mAbsoluteChange = mAbsoluteChange;
    }

    public float getPercentageChange() {
        return mPercentageChange;
    }

    public void setPercentageChange(float mPercentageChange) {
        this.mPercentageChange = mPercentageChange;
    }

    public ArrayList<StockHistory> getStockHistories() {
        return mStockHistories;
    }

    public void setStockHistories(ArrayList<StockHistory> mStockHistories) {
        this.mStockHistories = mStockHistories;
    }
}
