package com.daojia.plugin_demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.daojia.plugin_demo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by allen on 17/12/4.
 */

public class TargetActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with " + "savedInstanceState = [" + savedInstanceState + "]");
        TextView textView = new TextView(this);
        textView.setText("TargetActivity");

        setContentView(R.layout.activity_target);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called with " + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called with " + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called with " + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called with " + "");
    }

}
