package com.example.matt.gymlog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class WorkoutActivity extends ActionBarActivity {
    public final static String HISTORY = "com.example.matt.gymlog.HISTORY";
    private final static boolean DISTINCT = true;
    public int exerciseCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        addExerciseField(null);
        setupUI(findViewById(R.id.root_linear_layout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_database) {
            DatabaseHelper.resetDatabaseDialog(this);
            return true;
        }
        else if(id == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.help){
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //not currently used
    public void addRepetitionField(View view)
    {
        ViewGroup layout = (ViewGroup)view.getParent();
        int numReps = layout.getChildCount();
        EditText repetitionField = (EditText)getLayoutInflater().inflate(R.layout.repetition_layout, null);
        layout.addView(repetitionField, numReps-2); //2 for the button and placeholder view
    }

    public void addExerciseField(View view)
    {
        ViewGroup layout = (ViewGroup)findViewById(R.id.exercise_layout_within_scrollview);
        getLayoutInflater().inflate(R.layout.rounded_linear_layout, layout);
        LinearLayout linearLayout = (LinearLayout)layout.getChildAt(exerciseCount);
        exerciseCount++;
        //LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.rounded_linear_layout, null);
        final LinearLayout exerciseLayout = (LinearLayout)linearLayout.getChildAt(0);

        Spinner spinner = (Spinner) exerciseLayout.getChildAt(2);
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        addSpinnerOptions(spinner, db, (EditText) exerciseLayout.getChildAt(1));

        TextView addListener = (TextView)linearLayout.findViewById(R.id.exercise_edit_text);
        addListener.setOnEditorActionListener(TextViewHelper.getEditorListener(this));

        LinearLayout rep = (LinearLayout)linearLayout.findViewById(R.id.repetition_layout);
        for(int i = 0; i < rep.getChildCount(); i++)
        {
            addListener = (TextView)rep.getChildAt(i);
            addListener.setOnEditorActionListener(TextViewHelper.getEditorListener(this));
        }

        //layout.addView(linearLayout);

    }

    private void addSpinnerOptions(Spinner spinner, SQLiteDatabase db, final EditText editText)
    {
        final Cursor c = db.rawQuery("SELECT " + DatabaseContract.DatabaseContent._ID + ","
                + DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE + " from " +
                DatabaseContract.DatabaseContent.TABLE_NAME + " GROUP BY " +
                DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE, null);

        MatrixCursor extras = new MatrixCursor(new String[]{ //add default first choice for spinner
                DatabaseContract.DatabaseContent._ID,
                DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE});
        extras.addRow(new String[]{"-1", "-Previous-"});
        Cursor[] cursors = {extras, c};
        Cursor extendedCursor = new MergeCursor(cursors);

        //Log.v("WorkoutActivity 115", CursorHelper.readCursor(c, "exercsises: "));

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                extendedCursor,
                new String[]{DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE},
                new int[] {android.R.id.text1});

        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cursorAdapter);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = "";
                if (position > 0) {
                    c.moveToPosition(position - 1);
                    selected = c.getString(c.getColumnIndex(DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE));
                }
                //EditText editText = (EditText) exerciseLayout.getChildAt(1);
                editText.setText(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });
    }

    public void setupUI(final View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    TextViewHelper.hideKeyboard((Activity) view.getContext());
                    return false;
                }

            });
        }

        else //is an EditText
        {
            ((EditText)view).setOnEditorActionListener(TextViewHelper.getEditorListener(this));
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void finishWorkout(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        ArrayList<String> exercises = new ArrayList<String>(); //even indices = exercise name, odd indices = rep numbers
        LinearLayout dataGroup = (LinearLayout)findViewById(R.id.exercise_layout_within_scrollview);
        int numChildren = dataGroup.getChildCount(); //num of pairs of exercises/reps
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.MONTH) + "/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR);

        for(int i = 0; i < numChildren; i++) //loop through each set of exercise/reps
        {
            LinearLayout outerLayout = (LinearLayout)dataGroup.getChildAt(i);
            LinearLayout exerciseLayout = (LinearLayout)outerLayout.getChildAt(0);
            EditText editText = (EditText)exerciseLayout.getChildAt(1); //the editview child containing the exercise name
            String text = editText.getText().toString();
            if(text.trim().equals("")) {
                text = "Unspecified";
            }
            exercises.add(text);

            LinearLayout repLayout = (LinearLayout)outerLayout.getChildAt(1); //layout for reps
            String numReps = getReps(repLayout);
            if(numReps.trim().equals("")) {
                numReps = "0";
            }

            exercises.add(numReps);
            //Log.v("WorkoutActivity", "Exercises: " + exercises);

            //Database entry
            values.put(DatabaseContract.DatabaseContent.COLUMN_NAME_DATE, date);
            values.put(DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE, text);
            values.put(DatabaseContract.DatabaseContent.COLUMN_NAME_REPETITIONS, numReps);

            db.insert(DatabaseContract.DatabaseContent.TABLE_NAME, "NULL", values);
        }

        //db.close();

        String[] projection = {
                DatabaseContract.DatabaseContent.COLUMN_NAME_DATE,
                DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE,
                DatabaseContract.DatabaseContent.COLUMN_NAME_REPETITIONS
        };

        String selection = DatabaseContract.DatabaseContent.COLUMN_NAME_DATE + "=?";
        String[] selArgs = {date};

        Cursor cursor = db.query(
                DatabaseContract.DatabaseContent.TABLE_NAME,
                projection,
                selection,
                selArgs,
                null,
                null,
                null
                );

        cursor.moveToFirst();
        Log.v("WorkoutActivity", CursorHelper.readCursor(cursor, "Data"));

        db.close();

        Intent finishWorkout = new Intent(this, FinishWorkoutActivity.class);
        finishWorkout.putExtra(HISTORY, exercises);
        startActivity(finishWorkout);
    }

    public String getReps(LinearLayout layout)
    {
        String toReturn = "";
        for(int i = 1; i < 6; i++)
        {
            EditText editText = (EditText)layout.getChildAt(i);
            String text = editText.getText().toString();
            if(!text.trim().equals(""))
            {
                toReturn += text + " ";
            }
        }
        return toReturn;
    }

    public void history(View view)
    {
        //choose a data to view that day's workout
        Intent history = new Intent(this, HistoryButtonsActivity.class);
        startActivity(history);
    }

}
