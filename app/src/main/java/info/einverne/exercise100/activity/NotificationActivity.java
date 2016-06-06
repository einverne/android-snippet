package info.einverne.exercise100.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import info.einverne.exercise100.Push;
import info.einverne.exercise100.R;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.btn_noti_normal).setOnClickListener(this);
        findViewById(R.id.btn_noti_remote_view).setOnClickListener(this);
        findViewById(R.id.btn_noti_big_picture).setOnClickListener(this);
        findViewById(R.id.btn_noti_big_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_noti_normal:
                Intent intent = new Intent();
                Push.sendNotification(this, intent);
                break;
            case R.id.btn_noti_remote_view:
                Intent intent1 = new Intent();
                Push.sendCustomNotification(this, intent1);
                break;
            case R.id.btn_noti_big_picture:
                Push.sendBigPictureNotification(this);
                break;
            case R.id.btn_noti_big_text:
                Push.sendBigTextNotification(this);
                break;
            default:
                break;
        }
    }
}
