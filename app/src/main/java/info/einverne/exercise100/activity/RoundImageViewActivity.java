package info.einverne.exercise100.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedDrawable;
import com.makeramen.roundedimageview.RoundedImageView;

import info.einverne.exercise100.R;
import info.einverne.exercise100.utils.Utils;

public class RoundImageViewActivity extends AppCompatActivity {

    private ImageView mImageView;
    RoundedImageView mRoundedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mRoundedImageView = (RoundedImageView) findViewById(R.id.roundImageView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blur_test);
        RoundedDrawable roundedDrawable = RoundedDrawable.fromBitmap(bitmap);
        roundedDrawable.setCornerRadius(0, Utils.convertDpToPixel(50f, this), Utils.convertDpToPixel(50f, this), 0);
        mImageView.setImageDrawable(roundedDrawable);
    }


}
