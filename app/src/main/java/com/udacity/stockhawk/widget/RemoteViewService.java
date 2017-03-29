package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;

/**
 * Created by emad on 3/6/2017.
 */

public class RemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Cursor cursor = getContentResolver().query(Contract.Quote.URI,null,null,null,null);
        return new RemoteViewFactory(this.getApplicationContext(),intent);
    }
}
