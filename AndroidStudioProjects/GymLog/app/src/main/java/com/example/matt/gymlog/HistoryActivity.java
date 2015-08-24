package com.example.matt.gymlog;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class HistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //date of exercsies we want to display
        final String date = (String)getIntent().getExtras().getSerializable(HistoryButtonsActivity.DATE);
        TextView dateTextView = new TextView(this);
        dateTextView.setText(date);
        dateTextView.setTextSize(32);
        dateTextView.setTypeface(null, Typeface.BOLD);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(dateTextView);

        //next add data for the date using cursor
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {DatabaseContract.DatabaseContent.COLUMN_NAME_EXERCISE,
                            DatabaseContract.DatabaseContent.COLUMN_NAME_REPETITIONS};
        String selection = DatabaseContract.DatabaseContent.COLUMN_NAME_DATE + "=?";
        String[] selectionArg = {date};

        final Cursor c = db.query(
                DatabaseContract.DatabaseContent.TABLE_NAME,
                projection,
                selection,
                selectionArg,
                null,
                null,
                null
        );

        Log.v("HistoryActivity 64", CursorHelper.readCursor(c, "Data"));
        ArrayList<String> exercises = CursorHelper.makeExerciseArray(c);
        Log.v("HistoryActivity 66", exercises.toString());
        ScrollView scrollView = DisplayHelper.displayExercise(layout, exercises, this);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(DisplayHelper.MATCHW_WRAPH);

        DisplayHelper.WEIGHTW_WRAPH.setMargins(4,0,4,4);

        Button homeButton = new Button(this);
        homeButton.setText("Home");
        homeButton.setLayoutParams(DisplayHelper.WEIGHTW_WRAPH);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });
        homeButton.setBackgroundResource(R.drawable.button);

        final SharedPreferences settings = getPreferences(MODE_PRIVATE);

        Button emailButton = new Button(this);
        emailButton.setText("Email Workout");
        emailButton.setLayoutParams(DisplayHelper.WEIGHTW_WRAPH);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EmailDialog(settings, date, c, v);
                newFragment.show(getFragmentManager(), "email workout");
            }
        });
        emailButton.setBackgroundResource(R.drawable.button);

        linearLayout.addView(emailButton);
        linearLayout.addView(homeButton);

        LinearLayout outerLayout = new LinearLayout(this);
        outerLayout.setOrientation(LinearLayout.VERTICAL);
        outerLayout.addView(scrollView);
        outerLayout.addView(linearLayout);

        setContentView(outerLayout);

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
}
