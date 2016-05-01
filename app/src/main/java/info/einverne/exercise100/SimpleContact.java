package info.einverne.exercise100;

import android.provider.BaseColumns;

public final class SimpleContact {
    public SimpleContact() {
    }

    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_CONTACT_NAME = "name";
    }
}
