package com.example.matt.gymlog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Matt on 8/19/15.
 */
public class DeleteDatabaseDialog extends DialogFragment {

    private Context context;

    public DeleteDatabaseDialog(Context c)
    {
        context = c;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete workout history?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHelper dbHelper = new DatabaseHelper(context);
                    dbHelper.resetDatabase();
                    Log.v("MainActivity", "Database reset");
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //automatically cancels?
                }
            });
        return builder.create();
    }
}
