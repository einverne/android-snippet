package info.einverne.exercise100.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import info.einverne.exercise100.R;

public class SharedElementActivity extends AppCompatActivity {

    private Scene scene1, scene2;
    private TransitionSet sharedTransitionSet;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element);

        final ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
        final TextView tvDemo = (TextView) findViewById(R.id.tvDemo);

        CardView cardView = (CardView) findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 16) {
                    Intent intent = new Intent(SharedElementActivity.this, DetailsActivity.class);
                    Pair<View, String> s1 = Pair.create((View) ivProfile, "profile");
                    Pair<View, String> s2 = Pair.create((View) tvDemo, "tvDemo");
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(SharedElementActivity.this, s1, s2);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        ViewGroup baseLayout = (ViewGroup) findViewById(R.id.base);

        View startViews = getLayoutInflater().inflate(R.layout.transition_scene1,
                baseLayout, false);
        View endViews = getLayoutInflater().inflate(R.layout.transition_scene2,
                baseLayout, false);

        scene1 = new Scene(baseLayout, startViews);
        scene2 = new Scene(baseLayout, endViews);
        scene2.setEnterAction(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SharedElementActivity.this, "scene2 enter", Toast.LENGTH_SHORT).show();
            }
        });
//        scene2 = Scene.getSceneForLayout(baseLayout, R.layout.transition_scene2, this);
        sharedTransitionSet = new AutoTransition();        // min api 19
        sharedTransitionSet.addTarget("profile");
        sharedTransitionSet.addTarget("demoText");
//        sharedTransitionSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        Fade fade = new Fade();
//        fade.excludeTarget("profile", true);
//        fade.excludeTarget("demoText", true);
//        sharedTransitionSet.addTransition(fade);

        sharedTransitionSet.addTransition(new Fade());

        Transition fadeTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.fade_transition);
        sharedTransitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void ToScene2(View view) {
        if (Build.VERSION.SDK_INT > 19) {
            Transition sceneTransitionSet = TransitionInflater.from(this).inflateTransition(R.transition.scene_transitionset);
            TransitionManager.go(scene2, sceneTransitionSet);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void ToScene1(View view) {
        TransitionManager.go(scene1, sharedTransitionSet);
    }
}
