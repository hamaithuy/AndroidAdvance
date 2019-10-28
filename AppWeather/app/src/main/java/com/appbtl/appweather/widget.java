package com.appbtl.appweather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.appbtl.appweather.model.OpenWeatherJson;
import com.google.gson.Gson;

/**
 * Implementation of App Widget functionality.
 */
public class widget extends AppWidgetProvider {

    private static final String TAG = "MyActivity";
    private String resultDailys;
    private IOFile ioFile;
    private String data;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String a) {

        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        //Extract the data…
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, data);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        OpenWeatherJson result = new Gson().fromJson(MainActivity.reSultMain, OpenWeatherJson.class);
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
//        remoteViews.setTextViewText(R.id.textView2, result.getVisibility() + "m");
//        remoteViews.setTextViewText(R.id.textView3, result.getMain().getPressure() + " hpa");
//        remoteViews.setTextViewText(R.id.textView4, result.getMain().getHumidity() + "%");
//        remoteViews.setTextViewText(R.id.textView5, result.getWind().getSpeed() + "km/h");
//        remoteViews.setTextViewText(R.id.tvTemp, (int) (result.getMain().getTemp() - 273.15) + "°C");
//        String des = result.getWeather().get(0).getDescription();
//        remoteViews.setTextViewText(R.id.textView13, MainActivity.Des.get(des));
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

