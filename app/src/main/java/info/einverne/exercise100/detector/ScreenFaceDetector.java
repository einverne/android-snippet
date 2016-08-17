package info.einverne.exercise100.detector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by einverne on 8/17/16.
 */
public class ScreenFaceDetector implements SensorEventListener {

    private static final int DETAULT_ACCELERATION_THRESHOLD = 7;

    private int accelerationThreshold = DETAULT_ACCELERATION_THRESHOLD;

    /**
     * Listener for screen face
     */
    public interface Listener {
        void FaceUp();

        void FaceBottom();
    }

    private final Listener listener;
    private final SampleQueue queue = new SampleQueue();
    private SensorManager sensorManager;
    private Sensor accelerometer;

    public ScreenFaceDetector(Listener listener) {
        this.listener = listener;
    }

    public boolean start(SensorManager sensorManager) {
        if (accelerometer != null) {
            return true;
        }

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            this.sensorManager = sensorManager;
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
        return accelerometer != null;
    }

    public void stop() {
        if (accelerometer != null) {
            sensorManager.unregisterListener(this, accelerometer);
            sensorManager = null;
            accelerometer = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean faceChanged = isScreenFaceChange(event);
        long timestmp = event.timestamp;
        queue.add(timestmp, faceChanged);
        if (queue.isFaceChanging()) {
            queue.clear();
            listener.FaceUp();
        }
    }

    private boolean isScreenFaceChange(SensorEvent event) {
        float az = event.values[2];

        final double magnitudeSquared = az * az;
        return magnitudeSquared > accelerationThreshold * accelerationThreshold;
    }


    /** Queue of samples. Keeps a running average. */
    static class SampleQueue {

        /** Window size in ns. Used to compute the average. */
        private static final long MAX_WINDOW_SIZE = 500000000; // 0.5s
        private static final long MIN_WINDOW_SIZE = MAX_WINDOW_SIZE >> 1; // 0.25s

        /**
         * Ensure the queue size never falls below this size, even if the device
         * fails to deliver this many events during the time window. The LG Ally
         * is one such device.
         */
        private static final int MIN_QUEUE_SIZE = 4;

        private final SamplePool pool = new SamplePool();

        private Sample oldest;
        private Sample newest;
        private int sampleCount;
        private int acceleratingCount;

        /**
         * Adds a sample.
         *
         * @param timestamp    in nanoseconds of sample
         * @param accelerating true if > {@link #accelerationThreshold}.
         */
        void add(long timestamp, boolean accelerating) {
            // Purge samples that proceed window.
            purge(timestamp - MAX_WINDOW_SIZE);

            // Add the sample to the queue.
            Sample added = pool.acquire();
            added.timestamp = timestamp;
            added.accelerating = accelerating;
            added.next = null;
            if (newest != null) {
                newest.next = added;
            }
            newest = added;
            if (oldest == null) {
                oldest = added;
            }

            // Update running average.
            sampleCount++;
            if (accelerating) {
                acceleratingCount++;
            }
        }

        /** Removes all samples from this queue. */
        void clear() {
            while (oldest != null) {
                Sample removed = oldest;
                oldest = removed.next;
                pool.release(removed);
            }
            newest = null;
            sampleCount = 0;
            acceleratingCount = 0;
        }

        /** Purges samples with timestamps older than cutoff. */
        void purge(long cutoff) {
            while (sampleCount >= MIN_QUEUE_SIZE
                    && oldest != null && cutoff - oldest.timestamp > 0) {
                // Remove sample.
                Sample removed = oldest;
                if (removed.accelerating) {
                    acceleratingCount--;
                }
                sampleCount--;

                oldest = removed.next;
                if (oldest == null) {
                    newest = null;
                }
                pool.release(removed);
            }
        }

        /** Copies the samples into a list, with the oldest entry at index 0. */
        List<Sample> asList() {
            List<Sample> list = new ArrayList<Sample>();
            Sample s = oldest;
            while (s != null) {
                list.add(s);
                s = s.next;
            }
            return list;
        }

        /**
         * Returns true if we have enough samples and more than 3/4 of those samples
         * are accelerating.
         */
        boolean isFaceChanging() {
            return newest != null
                    && oldest != null
                    && newest.timestamp - oldest.timestamp >= MIN_WINDOW_SIZE
                    && acceleratingCount >= (sampleCount >> 1) + (sampleCount >> 2);
        }
    }

    /** An accelerometer sample. */
    static class Sample {
        /** Time sample was taken. */
        long timestamp;

        /** If acceleration > {@link #accelerationThreshold}. */
        boolean accelerating;

        /** Next sample in the queue or pool. */
        Sample next;
    }

    /** Pools samples. Avoids garbage collection. */
    static class SamplePool {
        private Sample head;

        /** Acquires a sample from the pool. */
        Sample acquire() {
            Sample acquired = head;
            if (acquired == null) {
                acquired = new Sample();
            } else {
                // Remove instance from pool.
                head = acquired.next;
            }
            return acquired;
        }

        /** Returns a sample to the pool. */
        void release(Sample sample) {
            sample.next = head;
            head = sample;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
