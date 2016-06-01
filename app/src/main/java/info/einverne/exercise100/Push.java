package info.einverne.exercise100;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import info.einverne.exercise100.activity.DemoActivity;

/**
 * Created by einverne on 16/6/1.
 */

public class Push {

    public static void sendNotification(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, DemoActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        // 关闭 Demo 上Activity

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Title");
        builder.setContentText("Text");

        builder.setTicker("Ticker text");
        builder.setContentIntent(pendingIntent);

        PendingIntent deletePendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setDeleteIntent(deletePendingIntent);

        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

    public static void sendCustomNotification(Context context, Intent intent) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_widget);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContent(remoteViews);

        Intent resultIntent = new Intent(context, DemoActivity.class);
        // TaskStackBuilder object contains an artificaial back stack for
        // started Activity. This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DemoActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.button_notification, resultPendingIntent);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
