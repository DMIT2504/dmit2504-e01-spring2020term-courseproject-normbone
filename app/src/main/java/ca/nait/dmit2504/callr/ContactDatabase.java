package ca.nait.dmit2504.callr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class ContactDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";

    public ContactDatabase(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_NUMBER + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void createContact(String name, String number) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NUMBER, number);
        db.insert(TABLE_CONTACTS, null, values);
    }

    public Cursor getAllContacts(){
        SQLiteDatabase db = getReadableDatabase();
        String queryStatement = "SELECT " + BaseColumns._ID + ", "
                + COLUMN_NAME + ", " + COLUMN_NUMBER + " FROM " + TABLE_CONTACTS + " ORDER BY "
                + COLUMN_NAME + " DESC";
        return db.rawQuery(queryStatement, null);
    }

    public void deleteContact(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void updateContact(long id, String name, String number) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NUMBER, number);
        db.update(TABLE_CONTACTS, values, BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Contact findContact(long id) {
        Contact singleResult = null;
        SQLiteDatabase db = getReadableDatabase();
        String queryStatement = "SELECT " + BaseColumns._ID + ", " + COLUMN_NAME + ", " + COLUMN_NUMBER + " FROM "
                + TABLE_CONTACTS + " WHERE " + BaseColumns._ID + " = ?";
        Cursor cursor = db.rawQuery(queryStatement, new String[] {String.valueOf(id)});

        if (cursor.getCount() == 1)
        {
            cursor.moveToFirst();
            singleResult = new Contact();
            singleResult.setId(id);
            singleResult.setName(cursor.getString(1));
            singleResult.setNumber(cursor.getString(2));
        }
        cursor.close();
        return singleResult;
    }
}
