package info.einverne.exercise100;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    public final static String TAG = "EV_TAG";

    public final static String KEY_SIZE_OF_CIRCLE = "sizeOfCircle";
    public final static String KEY_COLOR_OF_CIRCLE = "colorOfCircle";
    public final static String KEY_OPERATION = "operation";

    SharedPreferences pos_sharedPreferences;
    SharedPreferences.Editor editor;

    private CustomView cv;

    private String operation;

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "on NewIntent");
        int taskId = getTaskId();
        Log.d(TAG, "taskId "+taskId);


        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int taskId = getTaskId();
        Log.d(TAG, "taskId "+taskId);

        pos_sharedPreferences = this.getSharedPreferences("position", MODE_PRIVATE);
        editor = pos_sharedPreferences.edit();

        cv = (CustomView)findViewById(R.id.customView);

        updateCircle();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        updateCircle();
    }

    protected void updateCircle() {


        // read from preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String color_of_circle = settings.getString(KEY_COLOR_OF_CIRCLE, "1");
        int size_of_circle = settings.getInt(KEY_SIZE_OF_CIRCLE, 30);
        operation = settings.getString(KEY_OPERATION,"1");

        // change color
        switch (color_of_circle){
            case "1":
                cv.setmCircleColor(Color.WHITE);
                break;
            case "2":
                cv.setmCircleColor(Color.RED);
                break;
            case "3":
                cv.setmCircleColor(Color.BLUE);
                break;
            case "4":
                cv.setmCircleColor(Color.YELLOW);
                break;
            default:
                cv.setmCircleColor(Color.WHITE);
        }
        cv.setmRadius(size_of_circle);

        cv.setOperation(operation);

//        float x = pos_sharedPreferences.getFloat("x", 250);
//        float y = pos_sharedPreferences.getFloat("y", 250);
//        Log.d(TAG, "x value " + x + " "+ y);
//
//        cv.setmCirclePosition(new PointF(x,y));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        /**
         * 当用户从Paused状态恢复activity时，系统会调用onResume()方法。
         */
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        /**
         * 意味着activity仍然处于部分可见的状态.但更多时候意味着用户正在离开这个activity，并马上会进入Stopped state. 通常应该在onPause()回调方法里面做以下事情:

         停止动画或者是其他正在运行的操作，那些都会导致CPU的浪费.
         提交在用户离开时期待保存的内容(例如邮件草稿).
         释放系统资源，例如broadcast receivers, sensors (比如GPS), 或者是其他任何会影响到电量的资源。
         */

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");


//        PointF position = cv.getmCirclePosition();
//        Log.d(TAG,"Get from View "+ position.x + " "+position.y);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_id:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
