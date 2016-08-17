package info.einverne.exercise100.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import info.einverne.exercise100.R;
import info.einverne.exercise100.detector.ScreenFaceDetector;
import info.einverne.exercise100.detector.ShakeDetector;
import timber.log.Timber;

public class HeadsUpActivity extends AppCompatActivity implements SensorEventListener,
        ShakeDetector.Listener, ScreenFaceDetector.Listener{

    private TextView showFaceFlip;
    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heads_up);

        showFaceFlip = (TextView) findViewById(R.id.showFaceFlip);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        shakeDetector = new ShakeDetector(this);
        shakeDetector.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
        shakeDetector.start(sensorManager);

        ScreenFaceDetector screenFaceDetector = new ScreenFaceDetector(this);
        screenFaceDetector.start(sensorManager);
    }

    @Override
    protected void onStop() {
        super.onStop();
        shakeDetector.stop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Sensor.TYPE_ACCELEROMETER == event.sensor.getType()) {
            float ax = event.values[0];
            Timber.d("x value: " + ax);
            float ay = event.values[1];
            Timber.d("y value: " + ay);
            float az = event.values[2];
            Timber.d("z value: " + az);
//            float gz = event.values[2];
//            if (mGZ == 0) {
//                mGZ = gz;
//            } else {
//                if ((mGZ * gz) < 0) {
//                    mEventCountSinceGZChanged++;
//                    if (mEventCountSinceGZChanged == MAX_COUNT_GZ_CHANGE) {
//                        mGZ = gz;
//                        mEventCountSinceGZChanged = 0;
//                        if (gz > 0) {
//                            Log.d(TAG, "now screen is facing up.");
//                        } else if (gz < 0) {
//                            Log.d(TAG, "now screen is facing down.");
//                        }
//                    }
//                } else {
//                    if (mEventCountSinceGZChanged > 0) {
//                        mGZ = gz;
//                        mEventCountSinceGZChanged = 0;
//                    }
//                }
//            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void hearShake() {
        Timber.d("Shaked");
    }

    @Override
    public void FaceUp() {
        Timber.d("FaceUp");
    }

    @Override
    public void FaceBottom() {

    }
}
