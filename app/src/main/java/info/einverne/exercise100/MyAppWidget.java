package info.einverne.exercise100;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.HashSet;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    public final static String TAG = "EV_APPWIDGET";

    public final static String UPDATE_WIDGET = "info.einverne.exercise100.UPDATE_WIDGET";

    private static HashSet<Integer> widgetIds = new HashSet<>();

    private static Intent service_intent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        int number = new Random().nextInt(100);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        views.setTextViewText(R.id.appwidget_text, "Test: " + String.valueOf(number));
        views.setTextColor(R.id.appwidget_text, Color.RED);

        // imagebtn open app

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra("click", 2);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_imageButton, pendingIntent);

        // btn open url
        Intent openURL = new Intent(Intent.ACTION_VIEW, Uri.parse("http://einverne.github.io"));
        PendingIntent urlPendingIntent = PendingIntent.getActivity(context, 0, openURL, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_btn, urlPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);
        // There may be multiple widgets active, so update all of them
        Log.d( TAG , "onUpdate widget added or widget updated");
        for (int appWidgetId : appWidgetIds) {
            widgetIds.add(Integer.valueOf(appWidgetId));
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // Enter relevant functionality for when the first widget is created
        // called only the first instance of widget is created
        Log.d(TAG, "First widget is created");

        service_intent = new Intent(context, UpdateWidgetService.class);
        service_intent.setPackage(context.getPackageName());
        context.startService(service_intent);

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG,"Last widget is deleted");

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int appid : appWidgetIds){
            widgetIds.remove(appid);
        }
        context.stopService(service_intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        if (action.equals(UPDATE_WIDGET)){
            for (int id : widgetIds){
                updateAppWidget(context, AppWidgetManager.getInstance(context), id);
            }
        }else{

        }

    }

}

