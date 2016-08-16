package info.einverne.exercise100.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import info.einverne.exercise100.DemoIntentService;
import info.einverne.exercise100.R;

public class ServiceDemoActivity extends AppCompatActivity {
    public static final String DOWNLOAD_RESULT = "info.einverne.exercise100.DOWNLOAD_RESULT";
    private Button btn_start_service;
    private Button btn_stop_service;

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == DOWNLOAD_RESULT) {
                String resultFromDownload = intent.getStringExtra(DemoIntentService.EXTRA_DOWNLOAD_RESULT);
                handleResult(resultFromDownload);
            }
        }
    };

    private void handleResult(String resultFromDownload) {
        Toast.makeText(this, "result " + resultFromDownload, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);

        btn_start_service = (Button) findViewById(R.id.btn_start_service);
        btn_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDownloadTask();
            }
        });

        registerReceiver();
    }

    private int i = 0;
    private void addDownloadTask() {
        DemoIntentService.startDownload(this, "download path" + i);
        i++;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DOWNLOAD_RESULT);
        registerReceiver(downloadReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadReceiver);
    }
}
