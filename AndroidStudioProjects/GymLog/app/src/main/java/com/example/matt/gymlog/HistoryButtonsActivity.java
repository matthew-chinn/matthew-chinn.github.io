package com.example.matt.gymlog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class HistoryButtonsActivity extends ActionBarActivity {
    private final boolean DISTINCT  = true;
    private final int PADDING = 16;
    public final static String DATE = "com.example.matt.gymlog.DATE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {DatabaseContract.DatabaseContent.COLUMN_NAME_DATE};
        Cursor cursor = db.query(
                DISTINCT,
                DatabaseContract.DatabaseContent.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        Log.v("HistoryButtonsActivity", CursorHelper.readCursor(cursor, "Date"));

        LinearLayout layout = new LinearLayout(this);
        layout.setPadding(PADDING, PADDING, PADDING, PADDING);
        layout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<Button> dateButtons = makeButtons(cursor);
        if(dateButtons.size() == 0)
        {
            TextView textView = new TextView(this);
            textView.setText("No history to display");
            textView.setTextSize(32);
            layout.addView(textView);
        }
        else
        {
            for (int i = 0; i < dateButtons.size(); i++) {
                layout.addView(dateButtons.get(i));
            }
        }

        Button homeButton = new Button(this);
        homeButton.setText("Home");
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        homeButton.setLayoutParams(buttonParams);
        homeButton.setBackgroundResource(R.drawable.button);

        TextView placeHolder = new TextView(this);
        LinearLayout.LayoutParams space = new LinearLayout.LayoutParams(0,0,1.0f);
        placeHolder.setLayoutParams(space);

        layout.addView(placeHolder);
        layout.addView(homeButton);
        setContentView(layout);

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

    public ArrayList<Button> makeButtons(Cursor c)
    {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(0,0,0,8);
        ArrayList<Button> toReturn = new ArrayList<>();
        int numRow = c.getCount();
        for(int i = 0; i < numRow; i++)
        {
            Button button = new Button(this);
            button.setText(c.getString(0)); //the date
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateClicked(v);
                }
            });
            button.setBackgroundResource(R.drawable.button);
            button.setLayoutParams(buttonParams);
            c.moveToNext();
            toReturn.add(button);
        }
        return toReturn;
    }

    //display that date's exercise history
    public void dateClicked(View v)
    {
        Button b = (Button)v;
        String date = b.getText().toString();
        Intent displayDateHistory = new Intent(this, HistoryActivity.class);
        displayDateHistory.putExtra(DATE, date);
        startActivity(displayDateHistory);
    }
}
