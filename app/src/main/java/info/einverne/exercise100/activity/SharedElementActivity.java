package info.einverne.exercise100.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.einverne.exercise100.R;

public class SharedElementActivity extends AppCompatActivity {

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

    }
}
