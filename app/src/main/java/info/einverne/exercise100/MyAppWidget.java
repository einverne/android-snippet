package info.einverne.exercise100;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

import info.einverne.exercise100.activity.GravityCircleActivity;
import info.einverne.exercise100.activity.WidgetSettingsActivity;
import info.einverne.exercise100.service.UpdateWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    /**
     * 起动时AppWidgetProvider的执行流程：
     * 第一步：onReceive()
     * 接到广播事件：android.appwidget.action.APPWIDGET_ENABLED
     * 第二步：onEnabled()
     * 第三步：onReceive()
     * 接到广播事件：android.appwidget.action.APPWIDGET_UPDATE
     * 第四步：onUpdate()
     * <p/>
     * 被删除时AppWidgetProvider
     * 第一步：onReceive()
     * 接到广播事件：android.appwidget.action.APPWIDGET_DELETED
     * 第二步：onDelete();
     * 第三步：onReceive()
     * 接到广播事件：android.appwidget.action.APPWIDGET_DISABLED
     * 第四步：onDisabled()
     * <p/>
     * 要得到一个pendingIntent对象，使用方法类的静态方法 getActivity(Context, int, Intent, int),
     * getBroadcast(Context, int, Intent, int) ,  getService(Context, int, Intent, int)
     * 分别对应着Intent的3个行为，跳转到一个activity组件、打开一个广播组件和打开一个服务组件。
     * 参数有4个，比较重要的事第三个和第一个，其次是第四个和第二个。可以看到，要得到这个对象，
     * 必须传入一个Intent作为参数，必须有context作为参数。
     * <p/>
     * pendingIntent是一种特殊的Intent。主要的区别在于Intent的执行立刻的，而 pendingIntent的执行不是立刻的。
     * pendingIntent执行的操作实质上是参数传进来的Intent的操作，但是使用 pendingIntent的目的在于
     * 它所包含的Intent的操作的执行是需要满足某些条件的。
     * <p/>
     * 接受来自AppWidget的广播
     * 在AndroidMainfest.xml当中为AppWidgetProvider注册新的intent-filter
     */

    public final static String TAG = "EV_APPWIDGET";

    public final static String ACTION_WIDGET_TEXT = "info.einverne.exercise100.ACTION_WIDGET_TEXT";
    public final static String ACTION_WIDGET_IMAGEBUTTON = "info.einverne.exercise100.ACTION_WIDGET_IMAGEBUTTON";
    public final static String PREFS_CLICK_NUM = "PREFS_CLICK_NUM";
    public final static String PREFS_CLICK_NUM_KEY = "click_num";

    private static HashSet<Integer> widgetIds = new HashSet<>();
    private static Intent service_intent;

    static void updateText(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "updateText");
        int number = new Random().nextInt(100);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        views.setTextViewText(R.id.appwidget_text, "Test: " + String.valueOf(number));
        views.setTextColor(R.id.appwidget_text, Color.RED);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateImageBtn(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "updateImageBtn");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        // set onclick imagebutton       imagebtn open app
//        Intent intent = new Intent(context, GravityCircleActivity.class);
//        intent.setAction(MyAppWidget.ACTION_WIDGET_IMAGEBUTTON);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.widget_imageButton, pendingIntent);
        // 发送广播
        Intent intent = new Intent(context, MyAppWidget.class);
        intent.setAction(ACTION_WIDGET_IMAGEBUTTON);
        intent.putExtra("click", 12);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_imageButton, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateUrl(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        // btn open url
        Intent openURL = new Intent(Intent.ACTION_VIEW, Uri.parse("http://einverne.github.io"));
        PendingIntent urlPendingIntent = PendingIntent.getActivity(context, 1, openURL, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_btn, urlPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences isconfig = context.getSharedPreferences(WidgetSettingsActivity.PREFS_NAME,
                Context.MODE_PRIVATE);
        boolean b_isconfig = isconfig.getBoolean("" + appWidgetId, false);
        if (!b_isconfig) return;         // 只有配置成功才开始更新
        Log.d(TAG, "updateAppWidget");

        updateAppWidget(context, appWidgetManager, appWidgetId);
        updateImageBtn(context, appWidgetManager, appWidgetId);
        updateUrl(context, appWidgetManager, appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * 第一次添加widget时调用,这里可以开启Service
     *
     * @param context context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // Enter relevant functionality for when the first widget is created
        // called only the first instance of widget is created
        // 接收到 action android.appwidget.action.APPWIDGET_ENABLED
        Log.d(TAG, "First widget is created");

        service_intent = new Intent(context, UpdateWidgetService.class);
        service_intent.setPackage(context.getPackageName());
        context.startService(service_intent);
    }

    /**
     * 当 widget 更新时被执行。同样，当用户首次添加 widget 时，onUpdate() 也会被调用，
     * 这样 widget 就能进行必要的设置工作(如果需要的话) 。
     * 但是，如果定义了 widget 的 configure属性(即android:config，后面会介绍)，
     * 那么当用户首次添加 widget 时，onUpdate()不会被调用；之后更新 widget 时，onUpdate才会被调用。
     *
     * @param context          context
     * @param appWidgetManager appWidgetManager
     * @param appWidgetIds     ids
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate widget added or widget updated");
        for (int appWidgetId : appWidgetIds) {
            widgetIds.add(appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

//        ComponentName thiswidget = new ComponentName(context, MyAppWidget.class);
//        int[] widgetIds = appWidgetManager.getAppWidgetIds(thiswidget);
    }

    /**
     * 当 widget 被删除时被触发。
     *
     * @param context      context
     * @param appWidgetIds ids
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int appid : appWidgetIds) {
            widgetIds.remove(appid);
        }
    }


    /**
     * 最后一个实例被删除时调用
     *
     * @param context context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled Last widget is deleted");
        context.stopService(service_intent);

    }

    /**
     * 接收到任意广播时触发，并且会在上述的方法之前被调用
     *
     * @param context context
     * @param intent  intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);

        Log.d(TAG, "action " + action);
        if (action.equals(ACTION_WIDGET_TEXT)) {
            for (int id : widgetIds) {
                updateText(context, AppWidgetManager.getInstance(context), id);
            }
        }
        if (action.equals(ACTION_WIDGET_IMAGEBUTTON)) {
            Log.d(TAG, "img button clicked");

            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_CLICK_NUM, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int click_num = sharedPreferences.getInt(PREFS_CLICK_NUM_KEY, 0);
            click_num++;
            editor.putInt(PREFS_CLICK_NUM_KEY, click_num);
            editor.apply();

            Toast.makeText(context, "Click: " + click_num + " times", Toast.LENGTH_SHORT).show();

            //        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

//            Intent clickedIntent = new Intent(context, GravityCircleActivity.class);
//            clickedIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|
//                Intent.FLAG_ACTIVITY_CLEAR_TOP|
//                Intent.FLAG_ACTIVITY_NEW_TASK);
//            clickedIntent.setData(Uri.parse(clickedIntent.toUri(Intent.URI_INTENT_SCHEME)));
//            PendingIntent clickedPI = PendingIntent.getActivity(context, 0, clickedIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//            views.setOnClickPendingIntent(R.id.widget_imageButton, clickedPI);
            Intent intentActivity = new Intent(context, GravityCircleActivity.class);
            intentActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentActivity);

        }
        // 这几行一定要写,否则widget不能更新
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, MyAppWidget.class);
        appWidgetManager.updateAppWidget(componentName, views);
        super.onReceive(context, intent);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.d(TAG, "onAppWidgetOptionsChanged");

        int min_widh = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int min_height = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int max_width = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int max_height = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        Log.d(TAG, min_widh + " " + min_height);
        Log.d(TAG, max_width + " " + max_height);

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

