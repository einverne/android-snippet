package info.einverne.exercise100.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import info.einverne.exercise100.R;
import timber.log.Timber;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAlpha;
    private Button btnScale;
    private View view;
    private Button btnObject;
    private Button btnValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        btnAlpha = (Button) findViewById(R.id.btnAlpha);
        btnAlpha.setOnClickListener(this);
        btnScale = (Button) findViewById(R.id.btnScale);
        btnScale.setOnClickListener(this);
        btnObject = (Button) findViewById(R.id.btnObject);
        btnObject.setOnClickListener(this);
        btnValue = (Button) findViewById(R.id.btnValueAnimator);
        btnValue.setOnClickListener(this);
        view = findViewById(R.id.view);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btnAlpha:
                Timber.d("alpha clicked");
                Animation alphaAnimation = new AlphaAnimation(1f, 0.0f);
                alphaAnimation.setDuration(2000);
                alphaAnimation.setRepeatCount(2);
                view.startAnimation(alphaAnimation);
                break;
            case R.id.btnScale:
                Timber.d("scale clicked");
                Animation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
                scaleAnimation.setDuration(2000);
                view.startAnimation(scaleAnimation);
                break;
            case R.id.btnObject:
                ObjectAnimator anim = ObjectAnimator.ofFloat(view, "einverne", 0f, 1f);
                anim.setDuration(2000);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float val = (float) animation.getAnimatedValue();
                        view.setAlpha(val);
                        view.setScaleX(val);
                        view.setScaleY(val);
                        view.setTranslationX(val * 100);
                        view.setTranslationY(val * 100);
                    }
                });
                anim.start();
                break;
            case R.id.btnValueAnimator:
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100f);
                valueAnimator.setDuration(2000);
                valueAnimator.setTarget(view);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Timber.d("Animation Update");
                        view.setTranslationX((Float) animation.getAnimatedValue());
                    }
                });
                valueAnimator.start();
                break;
        }
    }
}
