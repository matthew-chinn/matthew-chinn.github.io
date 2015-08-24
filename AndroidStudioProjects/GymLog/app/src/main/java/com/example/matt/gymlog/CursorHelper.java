package com.example.matt.gymlog;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Matt on 8/18/15.
 */
public class CursorHelper {

    public static String readCursor(Cursor c, String type)
    {
        c.moveToFirst();
        int numCol = c.getColumnCount();
        int numRow = c.getCount();
        String toReturn = type + " \n";

        for(int i = 0; i < numRow; i++)
        {
            for(int j = 0; j < numCol; j++)
            {
                toReturn += c.getString(j) + " ";
            }
            toReturn += "\n";
            c.moveToNext();
        }
        c.moveToFirst();
        return toReturn;
    }

    public static ArrayList<String> makeExerciseArray(Cursor c)
    {
        c.moveToFirst();
        int numCol = c.getColumnCount();
        int numRow = c.getCount();
        ArrayList<String> exercises = new ArrayList<>();

        for(int i = 0; i < numRow; i++)
        {
            for(int j = 0; j < numCol; j++)
            {
                String data = c.getString(j);
                if(data != null && !data.trim().equals(""))
                    exercises.add(c.getString(j));
            }
            c.moveToNext();
        }
        c.moveToFirst();
        return exercises;
    }
}
