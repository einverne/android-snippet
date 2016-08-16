package info.einverne.exercise100.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import info.einverne.exercise100.R;

public class BasicActivity extends AppCompatActivity {

    public static final String TAG = "EV_BASIC_TAG";
    private SeekBar seekBar1;
    private TextView tvSeekBar1;

    private SeekBar seekBarWithThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        tvSeekBar1 = (TextView) findViewById(R.id.tv_seekbar1);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekBar1.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "start track touch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "stop track touch");
            }
        });

        seekBarWithThumb = (SeekBar) findViewById(R.id.seekBarWithThumb);
        seekBarWithThumb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekBar1.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
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
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkBox1:
                if (checked) {
                    Log.d(TAG, "checkbox1 is checked");
                } else {
                    Log.d(TAG, "checkbox1 is unchecked");
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    Log.d(TAG, "checkbox2 is checked");
                } else {
                    Log.d(TAG, "checkbox2 is unchecked");
                }
                break;
        }
    }
}
