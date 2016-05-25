package info.einverne.exercise100.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import info.einverne.exercise100.R;

public class BasicActivity extends AppCompatActivity {

    public static final String TAG = "EV_BASIC_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()) {
            case R.id.radio1:
                if (checked) {
                    Log.d(TAG, "radio1 is checked");
                }
                break;
            case R.id.radio2:
                if (checked) {
                    Log.d(TAG, "radio2 is checked");
                }
                break;
            case R.id.radio3:
                if (checked) {
                    Log.d(TAG, "radio3 is checked");
                }
                break;
        }
    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()) {
            case R.id.checkBox1:
                if (checked) {
                    Log.d(TAG, "checkbox1 is checked");
                }else {
                    Log.d(TAG, "checkbox1 is unchecked");
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    Log.d(TAG, "checkbox2 is checked");
                }else {
                    Log.d(TAG, "checkbox2 is unchecked");
                }
                break;
        }
    }
}
