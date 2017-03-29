package com.udacity.stockhawk.ui;

import android.os.AsyncTask;
import android.os.BadParcelableException;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by emad on 3/9/2017.
 */

public class FindStackAsyncTask extends AsyncTask<String,Void,Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        Stock mStock = null;
        try {
            mStock = YahooFinance.get(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mStock.getQuote().getPrice() == null){
            return false;
        }else{
            return true;
        }
    }
}
