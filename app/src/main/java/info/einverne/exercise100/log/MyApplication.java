package info.einverne.exercise100.log;

import android.app.Application;

import info.einverne.exercise100.BuildConfig;
import timber.log.Timber;

/**
 * Created by einverne on 8/16/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}
