package info.einverne.exercise100;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    Button writeBtn;
    Button readBtn;
    Button cacheBtn;
    Button deleteBtn;
    Button writeDb;
    Button readDb;
    TextView textView;
    int insert_count = 1;
    SimpleContactDbHelper helper;

    public static final String FILENAME = "Filename";
    public static final String TAG = "EV_DATA_TAG";
    FileOutputStream outputStream;
    OutputStreamWriter outputStreamWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        editText = (EditText) findViewById(R.id.editText);
        writeBtn = (Button) findViewById(R.id.button_write);
        readBtn = (Button) findViewById(R.id.button_read);
        cacheBtn = (Button) findViewById(R.id.button_cache);
        deleteBtn = (Button) findViewById(R.id.button_delete);
        writeDb = (Button) findViewById(R.id.write_db);
        readDb = (Button) findViewById(R.id.read_db);

        textView = (TextView) findViewById(R.id.textView);
        writeBtn.setOnClickListener(this);
        readBtn.setOnClickListener(this);
        cacheBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        writeDb.setOnClickListener(this);
        readDb.setOnClickListener(this);

        helper = new SimpleContactDbHelper(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_write:

                Editable text = editText.getText();
                String str = text.toString();
                Log.d(TAG, str);
                try {
                    outputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(str);
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /**
                 * 另一种使用  Java API
                 * File file = new File(getFilesDir(), filename);
                 * FileOutputStream outputStream = new FileOutputStream(file);
                 */

                break;
            case R.id.button_read:
                try {
                    FileInputStream inputStream = openFileInput(FILENAME);
                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }
                        inputStream.close();
                        textView.setText(stringBuilder.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.button_delete:
                deleteFile(FILENAME);

                /**
                 * Java API
                 * file.delete();
                 */
                break;
            case R.id.button_cache:
                try {
                    File file = File.createTempFile("tempfile", null, getCacheDir());
                    FileOutputStream outStream = new FileOutputStream(file);
                    outStream.write(editText.getText().toString().getBytes());
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.write_db:
                if (insertIntoDb(insert_count, "name" + insert_count) != -1) {
                    Toast.makeText(this, "insert successfully " + insert_count, Toast.LENGTH_SHORT).show();
                }
                insert_count++;
                break;
            case R.id.read_db:
                readDb();
            default:
                break;
        }
    }

    private long insertIntoDb(int id, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SimpleContact.ContactEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(SimpleContact.ContactEntry.COLUMN_NAME_CONTACT_NAME, name);

        return db.insert(SimpleContact.ContactEntry.TABLE_NAME,
                "",
                values);
    }

    private void readDb() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                SimpleContact.ContactEntry.COLUMN_NAME_ENTRY_ID,
                SimpleContact.ContactEntry.COLUMN_NAME_CONTACT_NAME,
        };
        String selection = SimpleContact.ContactEntry.COLUMN_NAME_ENTRY_ID + "=?";
        String[] selectArgs = {"1"};

        Cursor cursor = db.query(
                SimpleContact.ContactEntry.TABLE_NAME,
                projection,             // 返回属性
                selection,              // where 语句
                selectArgs,             // where 参数
                null,                   // group the rows
                null,                   // filter by row groups
                null                    // sort
        );
        String ret = "";
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(SimpleContact.ContactEntry.COLUMN_NAME_ENTRY_ID));
            String name = cursor.getString(cursor.getColumnIndex(
                    SimpleContact.ContactEntry.COLUMN_NAME_CONTACT_NAME
            ));
            ret += id;
            ret += " " + name;
            ret += "\n";
        }
        cursor.close();
        textView.setText(ret);
    }
}
