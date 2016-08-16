package info.einverne.exercise100.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.pavelsikun.seekbarpreference.SeekBarPreference;

import java.util.Objects;

import info.einverne.exercise100.CustomView;
import info.einverne.exercise100.R;

import static android.view.View.generateViewId;

public class SettingsActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        if (Build.VERSION.SDK_INT > 16) frameLayout.setId(generateViewId());
        else //noinspection ResourceType
            frameLayout.setId(676442);

        setContentView(frameLayout);

        getFragmentManager().beginTransaction().add(frameLayout.getId(),
                new SettingsFragment()).commit();


    }


    public static class SettingsFragment extends PreferenceFragment {

        private ListPreference colorOfCircle;
        private SeekBarPreference seekBarPreference;


        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            colorOfCircle = (ListPreference) findPreference("colorOfCircle");
            seekBarPreference = (SeekBarPreference) findPreference("sizeOfCircle");


        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        Log.d("EV_TAG", preference.getKey());

        if (Objects.equals(preference.getKey(), CustomView.KEY_SIZE_OF_CIRCLE)) {
            Log.d("TAG", "change size of circle");
        }

        //如果返回false表示不允许被改变
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return true;
    }
}
