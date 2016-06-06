package info.einverne.exercise100;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        builder.setContentTitle("Normal Notification Title");
        builder.setContentText("Normal Notification Text");

        builder.setTicker("Ticker text");               // 5.0 之前的版本才会显示, 通知栏快速显示通知内容并消失
        builder.setContentIntent(pendingIntent);

        PendingIntent deletePendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setDeleteIntent(deletePendingIntent);   // 删除通知时动作s

        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

    /**
     * 设置自定义View通知
     * @param context context
     * @param intent intent
     */
    public static void sendCustomNotification(Context context, Intent intent) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_widget);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContent(remoteViews);                // add custom RemoteViews

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

    public static void sendBigPictureNotification(Context context) {
        // retrieve image from res folder
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_pic);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Big Picture");
        builder.setContentText("text");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // 构造 Big Picture Style
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(bitmap);
        bigPictureStyle.setBigContentTitle("Big Picture Title");
        bigPictureStyle.setSummaryText("Big Picture Summary");
        builder.setStyle(bigPictureStyle);

        Uri uriImage = Uri.parse("android.resource://" + "/" + context.getPackageName() + "/drawable/big_pic.jpg");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_STREAM, uriImage);
        intent.setType("*/*");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "big_pic.jpg");

        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/big_pic.jpg"));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,
                "Title", pendingIntent);
        builder.addAction(actionBuilder.build());

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);

    }

    public static void sendBigTextNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Big Text");
        builder.setContentText("big text");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        // 构造 Big Text Style
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("The declaration of the application. This element contains subelements that declare each of the application's components and has attributes that can affect all the components. Many of these attributes (such as icon, label, permission, process, taskAffinity, and allowTaskReparenting) set default values for corresponding attributes of the component elements. Others (such as debuggable, enabled, description, and allowClearUserData) set values for the application as a whole and cannot be overridden by the components.");
        bigText.setBigContentTitle("Big Content Title");
        bigText.setSummaryText("Big Text summary text");

        builder.setStyle(bigText);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
