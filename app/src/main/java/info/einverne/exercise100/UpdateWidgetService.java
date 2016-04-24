package info.einverne.exercise100;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.Random;

public class UpdateWidgetService extends Service {

    private static final int UPDATE_TIME = 5000;    // 更新时间 5s

    private Context context;
    private WidgetThread widgetThread;

    public UpdateWidgetService() {
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        widgetThread = new WidgetThread();
        widgetThread.start();

        super.onCreate();
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
//
//        int[] allwidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
//
//        for (int widgetId : allwidgetIds){
//            int number = (new Random()).nextInt(100);
//
//            RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.my_app_widget);
//
//            remoteViews.setTextViewText(R.id.appwidget_text, "Random: "+number);
//
////            Intent clickIntent = new Intent(this.getApplicationContext(), )
//        }
//    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private class WidgetThread extends Thread{
        @Override
        public void run() {
            try {
                while(true){
                    Intent intent = new Intent(MyAppWidget.UPDATE_WIDGET);
                    context.sendBroadcast(intent);

                    sleep(UPDATE_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
