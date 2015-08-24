package com.example.matt.gymlog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Matt on 8/17/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String NAME = "Database.db";

    public DatabaseHelper(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.v("DatabaseHelper", "Create Entries: " + DatabaseContract.SQL_CREATE_ENTRIES);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void resetDatabase()
    {
        onUpgrade(getWritableDatabase(), 1, 1);
    }

    public static void resetDatabaseDialog(Activity a){
        DialogFragment newFragment = new DeleteDatabaseDialog(a);
        newFragment.show(a.getFragmentManager(), "deleteDatabase");
    }
}

