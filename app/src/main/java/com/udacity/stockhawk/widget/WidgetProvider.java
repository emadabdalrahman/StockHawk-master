package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by emad on 3/6/2017.
 */

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds){

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget );

            Intent intentService = new Intent(context,RemoteViewService.class);
            intentService.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            intentService.setData(Uri.parse(intentService.toUri(intentService.URI_INTENT_SCHEME)));

            Intent mainIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,mainIntent,0);

            views.setRemoteAdapter(R.id.widget_list_view,intentService);

            views.setOnClickPendingIntent(R.id.widget_frame,pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
}
