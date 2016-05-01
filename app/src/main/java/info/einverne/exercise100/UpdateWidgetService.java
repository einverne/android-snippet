package info.einverne.exercise100;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

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

    @Override
    public void onDestroy() {
        widgetThread.interrupt();

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private class WidgetThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) { //非阻塞过程中通过判断中断标志来退出
                Intent intent = new Intent(MyAppWidget.ACTION_WIDGET_TEXT);
                context.sendBroadcast(intent);

                try {
                    sleep(UPDATE_TIME);//阻塞过程捕获中断异常来退出
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;//捕获到异常之后，执行break跳出循环。
                }
            }
        }
    }
}
