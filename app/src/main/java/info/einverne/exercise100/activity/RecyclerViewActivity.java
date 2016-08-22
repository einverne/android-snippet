package info.einverne.exercise100.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import info.einverne.exercise100.R;
import info.einverne.exercise100.adapter.NormalRecyclerViewAdapter;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScaleInAnimationAdapter scaleInAnimationAdapter =
                new ScaleInAnimationAdapter(new NormalRecyclerViewAdapter(this));
        scaleInAnimationAdapter.setInterpolator(new DecelerateInterpolator(2f));
        recyclerView.setAdapter(scaleInAnimationAdapter);
    }
}
