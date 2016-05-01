package info.einverne.exercise100;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SimpleContactDbHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_SIMPLE_CONTACT = "CREATE TABLE "
            + SimpleContact.ContactEntry.TABLE_NAME + " (" +
            SimpleContact.ContactEntry._ID + " INTEGER PRIMARY KEY, " +
            SimpleContact.ContactEntry.COLUMN_NAME_ENTRY_ID + " TEXT, " +
            SimpleContact.ContactEntry.COLUMN_NAME_CONTACT_NAME + " TEXT " + " )";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + SimpleContact.ContactEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SimpleContact.db";

    public SimpleContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SIMPLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
