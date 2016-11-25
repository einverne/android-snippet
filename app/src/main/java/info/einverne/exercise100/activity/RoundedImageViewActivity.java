package info.einverne.exercise100.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import info.einverne.exercise100.R;
import info.einverne.exercise100.view.CustomRoundedImageView;

public class RoundedImageViewActivity extends AppCompatActivity {

    private ImageView iv_rounded_bitmap_drawable;
    private CustomRoundedImageView iv_bitmap_shader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounded_image_view);

        iv_rounded_bitmap_drawable = (ImageView) findViewById(R.id.imageView);
        iv_bitmap_shader = (CustomRoundedImageView) findViewById(R.id.iv_bitmap_shader);

        setNormalRound();
        setCustomImageView();
    }

    private void setCustomImageView() {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.blur_test);
        iv_bitmap_shader.setImage(src, 20, CustomRoundedImageView.CORNER_BOTTOM_LEFT | CustomRoundedImageView.CORNER_TOP_RIGHT);
    }

    private void setNormalRound() {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.blur_test);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), src);

        roundedBitmapDrawable.setCornerRadius(100);
        roundedBitmapDrawable.setAntiAlias(true);
        iv_rounded_bitmap_drawable.setImageDrawable(roundedBitmapDrawable);
    }
}
