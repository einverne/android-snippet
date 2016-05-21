package info.einverne.exercise100;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by einverne on 5/21/16.
 * https://plus.google.com/u/0/+LennoardRai/posts/cuyZEYFF638
 */
public class ThemeUtils {
    private static int mTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_GREEN = 1;

    public static void changeToTheme(Activity activity, int theme){
        mTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch (mTheme){
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.AppTheme_Green);
                break;
        }
    }
}
