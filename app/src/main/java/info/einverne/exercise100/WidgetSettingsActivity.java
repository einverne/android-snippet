package info.einverne.exercise100;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;

public class WidgetSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String PREFS_NAME = "WidgetSettingsActivity";
    private static final String PREF_PREFIX_KEY = "prefix_";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ImageButton off_btn;
    private ImageButton on_btn;
    private AppWidgetManager appWidgetManager;
    private RemoteViews views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * In onCreate() we have to ensure that if the user presses BACK or cancelled the activity, then we should not add app widget.
         */
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_widget_settings);

        off_btn = (ImageButton)findViewById(R.id.off_button);
        on_btn = (ImageButton)findViewById(R.id.on_button);
        off_btn.setOnClickListener(this);
        on_btn.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
            return;
        }
        appWidgetManager = AppWidgetManager.getInstance(this);

        // https://www.youtube.com/watch?v=t4kDM-SudLo
        views = new RemoteViews(this.getPackageName(), R.layout.my_app_widget);

    }

    static void savePref(Context context, int appWidgetId, int res_id){
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putInt(PREF_PREFIX_KEY+appWidgetId, res_id);
        prefs.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.off_button:

                views.setImageViewResource(R.id.widget_imageButton, R.drawable.off_button);

                break;
            case R.id.on_button:
                views.setImageViewResource(R.id.widget_imageButton, R.drawable.on_button);
                break;
            default:

        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_imageButton, pendingIntent);


        Intent widgetIntent = new Intent();
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK,widgetIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        finish();

    }
}
