package info.einverne.exercise100.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;

import info.einverne.exercise100.R;

public class RoundImageViewActivity extends AppCompatActivity {

    RoundedImageView mRoundedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view);

        mRoundedImageView = (RoundedImageView) findViewById(R.id.roundImageView);

    }


}
