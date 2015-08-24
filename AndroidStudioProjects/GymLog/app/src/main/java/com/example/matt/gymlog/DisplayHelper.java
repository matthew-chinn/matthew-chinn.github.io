package com.example.matt.gymlog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matt on 8/18/15.
 */
public abstract class DisplayHelper {
    private static final int PADDING = 16;
    public static final LinearLayout.LayoutParams MATCHW_WRAPH = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );
    public static final LinearLayout.LayoutParams WEIGHTW_WRAPH = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f
    );
    public static final LinearLayout.LayoutParams MATCHW_WEIGHTH = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
    );
    public static final LinearLayout.LayoutParams WRAPW_WRAPH = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
    );


    //put exercises within a scrollview
    public static ScrollView displayExercise(ViewGroup layout, ArrayList<String> exercises, Context context)
    {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        int height = display.heightPixels;

        ScrollView scrollView = new ScrollView(context);

        scrollView.setLayoutParams(MATCHW_WEIGHTH);
        int count = 1;
        for(int i = 0; i < exercises.size(); i += 2)
        {
            String exerciseName = exercises.get(i);
            String repNums = exercises.get(i + 1);
            TextView exerciseView = new TextView(context);
            TextView repView = new TextView(context);
            repView.setPadding(0, 0, 0, PADDING);

            exerciseView.setTextSize(24);
            repView.setTextSize(24);

            exerciseView.setText("Exercise " + count + ": " + exerciseName);
            repView.setText("Repetitions: " + repNums);

            layout.addView(exerciseView);
            layout.addView(repView);

            count++;
        }

        scrollView.addView(layout);
        return scrollView;
    }

    public static TextView makePlaceholder(Context context)
    {
        TextView placeHolder = new TextView(context);
        LinearLayout.LayoutParams space = new LinearLayout.LayoutParams(0,0,1.0f);
        placeHolder.setLayoutParams(space);
        return  placeHolder;
    }


}
