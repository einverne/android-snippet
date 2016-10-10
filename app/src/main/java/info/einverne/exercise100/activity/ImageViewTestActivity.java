package info.einverne.exercise100.activity;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import info.einverne.exercise100.R;

/**
 * ImageView scaleType test, check layout for more details
 */
public class ImageViewTestActivity extends AppCompatActivity {

    private ImageView ivMatrix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_matrix);
        ivMatrix = (ImageView) findViewById(R.id.ivMatrix);

        float image_width = ivMatrix.getWidth();
        float image_height = ivMatrix.getHeight();
        float screen_width = getWindowManager().getDefaultDisplay().getWidth();
        float screen_height = getWindowManager().getDefaultDisplay().getHeight();
        RectF drawableRect = new RectF(0, 0, image_width, image_height);
        RectF viewRect = new RectF(0, 0, screen_width, screen_height);

        Matrix matrix = new Matrix();
        matrix.postTranslate(screen_width/2, screen_height/2);
        ivMatrix.setImageMatrix(matrix);
    }
}
