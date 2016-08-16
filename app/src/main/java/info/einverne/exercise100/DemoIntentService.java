package info.einverne.exercise100;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import info.einverne.exercise100.activity.ServiceDemoActivity;
import timber.log.Timber;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DemoIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_DOWNLOAD = "info.einverne.exercise100.action.DOWNLOAD_IMAGE";

    public static final String EXTRA_DOWNLOAD_RESULT = "info.einverne.exercise100.extra.IMG_PATH";

    public DemoIntentService() {
        super("DemoIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startDownload(Context context, String path1) {
        Intent intent = new Intent(context, DemoIntentService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_DOWNLOAD_RESULT, path1);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("Service onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String downloadPath = intent.getStringExtra(EXTRA_DOWNLOAD_RESULT);
                handleActionDownload(downloadPath);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDownload(String downloadpath) {
        try {
            Thread.sleep(2000);

            Intent intent = new Intent(ServiceDemoActivity.DOWNLOAD_RESULT);
            intent.putExtra(EXTRA_DOWNLOAD_RESULT, downloadpath + " downloadResult");
            sendBroadcast(intent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
