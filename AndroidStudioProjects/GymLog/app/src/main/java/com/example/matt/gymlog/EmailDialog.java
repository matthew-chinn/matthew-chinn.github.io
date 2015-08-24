package com.example.matt.gymlog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Matt on 8/20/15.
 */
public class EmailDialog extends DialogFragment {

    private final SharedPreferences settings; //access saved email
    private final String date;
    private final Cursor cursor;
    private final View view;

    public EmailDialog(SharedPreferences settings, String date, Cursor cursor, View view)
    {
        this.settings = settings;
        this.date = date;
        this.cursor = cursor;
        this.view = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        LinearLayout emailLayout = (LinearLayout)inflater.inflate(R.layout.email_layout, null);
        builder.setView(emailLayout);

        final EditText emailInput = (EditText)emailLayout.findViewById(R.id.email);
        String previousEmailAddress = settings.getString("email", "blank");
        if (previousEmailAddress != null && !previousEmailAddress.equals("blank"))
        {
            emailInput.setText(previousEmailAddress);
        }
        emailInput.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = settings.edit();
                String[] emailAddress = {emailInput.getText().toString()};
                editor.putString("email", emailAddress[0]);
                editor.commit();

                String subject = "Workout for " + date;
                String text = CursorHelper.readCursor(cursor, date);

                /*String uriText = "mailto:" + emailAddress + "?subject=" + Uri.encode(subject)
                        + "&body=" + Uri.encode(text);
                Uri uri = Uri.parse(uriText);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(uri);*/
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
                emailIntent.putExtra(Intent.EXTRA_TEXT, text);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.setType("plain/text");
                startActivity(Intent.createChooser(emailIntent, "Send email"));
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

