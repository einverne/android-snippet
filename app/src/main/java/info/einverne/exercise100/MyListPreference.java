package info.einverne.exercise100;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by einverne on 4/21/16.
 * <p/>
 * 想要通过自定义 ListPreference 实现改变 Summary ，但是后来发现只要在Summary 中写入 %s 就行
 */

public class MyListPreference extends ListPreference {
    public MyListPreference(Context context) {
        super(context);
    }

    public MyListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public CharSequence getSummary() {
        final CharSequence entry = getEntry();
        Log.d("EV_TAG", entry.toString());
        final CharSequence summary = super.getSummary();
        Log.d("EV_TAG", summary.toString());
        if (summary == null || entry == null) {
            return null;
        } else {
            return String.format(summary.toString(), entry);
        }
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        notifyChanged();
    }
}
