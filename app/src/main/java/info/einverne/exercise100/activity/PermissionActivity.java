package info.einverne.exercise100.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import info.einverne.exercise100.Push;
import info.einverne.exercise100.R;

public class PermissionActivity extends AppCompatActivity {

    private Button btnNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

    }

}
