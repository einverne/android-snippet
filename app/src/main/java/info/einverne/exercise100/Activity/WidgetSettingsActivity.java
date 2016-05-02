package info.einverne.exercise100.Activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import info.einverne.exercise100.MyAppWidget;
import info.einverne.exercise100.R;

public class WidgetSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_NAME = "WidgetSettingsActivity";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ImageButton off_btn;
    private ImageButton on_btn;
    private AppWidgetManager appWidgetManager;
    private RemoteViews views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * In onCreate() we have to ensure that if the user presses BACK or cancelled the activity,
         * then we should not add app widget.
         */
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_widget_settings);

        off_btn = (ImageButton) findViewById(R.id.off_button);
        on_btn = (ImageButton) findViewById(R.id.on_button);
        off_btn.setOnClickListener(this);
        on_btn.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        appWidgetManager = AppWidgetManager.getInstance(this);

        // https://www.youtube.com/watch?v=t4kDM-SudLo
        views = new RemoteViews(this.getPackageName(), R.layout.my_app_widget);

    }

    /**
     * 保存配置
     *
     * @param context     context
     * @param appWidgetId app id
     * @param isconfig    是否配置
     */
    static void savePref(Context context, int appWidgetId, boolean isconfig) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putBoolean("" + appWidgetId, isconfig);
        prefs.apply();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.off_button:

                views.setImageViewResource(R.id.widget_imageButton, R.drawable.off_button);

                break;
            case R.id.on_button:
                views.setImageViewResource(R.id.widget_imageButton, R.drawable.on_button);
                break;
            default:

        }

        // set onclick imagebutton       imagebtn open app
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setAction(MyAppWidget.ACTION_WIDGET_IMAGEBUTTON);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        intent.putExtra("click",1);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.widget_imageButton, pendingIntent);
        Intent intent = new Intent(this, MyAppWidget.class);
        intent.setAction(MyAppWidget.ACTION_WIDGET_IMAGEBUTTON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_imageButton, pendingIntent);


        // btn open url
        Intent openURL = new Intent(Intent.ACTION_VIEW, Uri.parse("http://einverne.github.io"));
        PendingIntent urlPendingIntent = PendingIntent.getActivity(this, 1, openURL, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_btn, urlPendingIntent);

        Intent widgetIntent = new Intent();
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, widgetIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        savePref(this, appWidgetId, true);          // 配置成功
        finish();

    }
}
