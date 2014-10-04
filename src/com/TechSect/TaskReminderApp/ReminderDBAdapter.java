package com.TechSect.TaskReminderApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hitenpratap on 5/10/14.
 */
public class ReminderDBAdapter {

    private static final String DATABASE_NAME = "techSectTaskReminder";
    private static final String DATABASE_Table = "reminders";

    private static final int DATABASE_VERSION = 1;

    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_DATE_TIME = "reminder_date_time";
    public static final String KEY_ROWID = "_id";

    private DataBaseHelper databaseHelper;

    private SQLiteDatabase database;

    private static final String DATABASE_CREATE = "create table "+DATABASE_Table+" ("+KEY_ROWID+"" +
            "integer primary key autoincrement, "+KEY_TITLE+" text not null,"
            +KEY_BODY+" text not null, "+KEY_DATE_TIME+" text not null);";

    private final Context context;

    public ReminderDBAdapter(Context context){
        this.context = context;
    }

    public ReminderDBAdapter open() throws SQLException{
        databaseHelper = new DataBaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        database.close();
    }

    public long createReminder(String title,String body,String reminderDateTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,title);
        contentValues.put(KEY_BODY,body);
        contentValues.put(KEY_DATE_TIME,reminderDateTime);
        return database.insert(DATABASE_Table,null,contentValues);
    }

    public boolean deleteReminder(long rowId){
        return database.delete(DATABASE_Table,KEY_ROWID+"="+rowId,null) > 0;
    }

    public Cursor fetchAllReminders(){
        return database.query(DATABASE_Table,new String[]{KEY_ROWID,KEY_TITLE,KEY_BODY,KEY_DATE_TIME},null
                ,null,null,null,null);
    }

    public Cursor fetchReminder(long rowId){
        Cursor cursor = database.query(true,DATABASE_Table,new String[]{KEY_ROWID,KEY_TITLE,KEY_BODY,KEY_DATE_TIME},
                KEY_ROWID+" = "+rowId,null,null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        return cursor;
    }


    public static class DataBaseHelper extends SQLiteOpenHelper{

        DataBaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase database){
            database.execSQL(DATABASE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase database,int oldVersion, int newVersion){
            //////////// used to upgrade the database
        }

    }


}
