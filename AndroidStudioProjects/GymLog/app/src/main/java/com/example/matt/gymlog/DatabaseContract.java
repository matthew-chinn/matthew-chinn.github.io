package com.example.matt.gymlog;

import android.provider.BaseColumns;

/**
 * Created by Matt on 8/17/15.
 */
public final class DatabaseContract {

    public DatabaseContract() {}

    public static abstract class DatabaseContent implements BaseColumns{
        public static final String TABLE_NAME = "WorkoutHistory";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_EXERCISE = "Exercise";
        public static final String COLUMN_NAME_REPETITIONS = "Repetitions";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String COMMA = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContent.TABLE_NAME + " (" +
            DatabaseContent._ID + " INTEGER PRIMARY KEY" + COMMA +
            DatabaseContent.COLUMN_NAME_DATE + TEXT_TYPE + COMMA +
            DatabaseContent.COLUMN_NAME_EXERCISE + TEXT_TYPE + COMMA +
            DatabaseContent.COLUMN_NAME_REPETITIONS + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            DatabaseContent.TABLE_NAME;
}
