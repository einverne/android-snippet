package info.einverne.exercise100.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import info.einverne.exercise100.R;

public class TextViewTestActivity extends AppCompatActivity {

    private TextView marquee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_test);

        marquee = (TextView)findViewById(R.id.textViewMarquee);
    }
}
