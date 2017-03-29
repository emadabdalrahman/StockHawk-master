package com.udacity.stockhawk.ui.StockDetailsActivity;

import java.util.Date;

/**
 * Created by emad on 3/14/2017.
 */

public class StockHistory {
    private String mDate;
    private String mClosedAt;
    private Date mTime;

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getClosedAt() {
        return mClosedAt;
    }

    public void setClosedAt(String closedAt) {
        mClosedAt = closedAt;
    }
}
