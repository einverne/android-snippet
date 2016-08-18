package info.einverne.exercise100.activity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import info.einverne.exercise100.R;
import info.einverne.exercise100.detector.ScreenFaceDetector;
import info.einverne.exercise100.detector.ShakeDetector;
import timber.log.Timber;

public class HeadsUpActivity extends AppCompatActivity implements
        ShakeDetector.Listener, ScreenFaceDetector.Listener {

    private TextView showText;
    private ShakeDetector shakeDetector;
    private ScreenFaceDetector screenFaceDetector;
    private SensorManager sensorManager;

    private String[] texts = {
            "Intent",
            "Activity",
            "Broadcast",
            "Service"
    };
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heads_up);

        showText = (TextView) findViewById(R.id.showFaceFlip);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        initSensor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSensor();
    }

    private void initSensor() {
        if (shakeDetector == null) {
            shakeDetector = new ShakeDetector(this);
            shakeDetector.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
            shakeDetector.start(sensorManager);
        }

        if (screenFaceDetector == null) {
            screenFaceDetector = new ScreenFaceDetector(this);
            screenFaceDetector.start(sensorManager);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopSensor();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSensor();
    }

    private void stopSensor() {
        if (shakeDetector != null) {
            shakeDetector.stop();
        }
        if (screenFaceDetector != null) {
            screenFaceDetector.stop();
        }
    }

    @Override
    public void hearShake() {
        Timber.d("Shaked");
    }

    @Override
    public void FaceUp() {
        Timber.d("FaceUp");
        index ++;
    }

    @Override
    public void FaceDown() {
        Timber.d("Face Down Next");

        if (index < showText.length()) {
            showText.setText(texts[index]);
            index ++;
        }

    }

}
