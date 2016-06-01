package info.einverne.exercise100.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import info.einverne.exercise100.R;
import info.einverne.exercise100.ThemeUtils;

public class ChangeThemeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        // setTheme 必须放到 setContentView 之前，super.onCreate 之后
        setContentView(R.layout.activity_change_theme);

        findViewById(R.id.theme1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.theme1:
                ThemeUtils.changeToTheme(this, ThemeUtils.THEME_GREEN);
                break;
        }
    }
}
