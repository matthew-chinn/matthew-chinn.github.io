package com.example.matt.gymlog;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by Matt on 8/24/15.
 */
public class TextViewHelper {

    public static TextView.OnEditorActionListener getEditorListener(final Activity a) {
        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() != KeyEvent.ACTION_DOWN)
                    return false;

                /*if (actionId == EditorInfo.IME_ACTION_DONE
                        && event.getAction() == KeyEvent.ACTION_DOWN) */
                else if (event == null || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    hideKeyboard(a);//match this behavior to your 'Send' (or Confirm) button
                }
                return true;
            }
        };
    return exampleListener;
}

    public static void hideKeyboard(Activity a) {
        // Check if no view has focus:
        View view = a.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
