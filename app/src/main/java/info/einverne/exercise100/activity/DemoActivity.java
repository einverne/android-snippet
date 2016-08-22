package info.einverne.exercise100.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.einverne.exercise100.R;

public class DemoActivity extends AppCompatActivity {

    public static final String TAG = "EV_DEMO_TAG";

    private ListView listView;

    final List<ActivityInfo> activityInfos = new ArrayList<>();
    List<String> activityNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        try {
            ActivityInfo[] activities = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES).activities;
            for (ActivityInfo activityInfo : activities) {
                if (!activityInfo.name.equals(this.getClass().getName())) {
                    activityInfos.add(activityInfo);

                    String name = activityInfo.name.substring(activityInfo.packageName.length() + 1);
                    activityNames.add(name);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, activityNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityInfo activityInfo = activityInfos.get(position);
                Class classname = null;
                try {
                    classname = Class.forName(activityInfo.name);
                    Intent intent = new Intent(DemoActivity.this, classname);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
