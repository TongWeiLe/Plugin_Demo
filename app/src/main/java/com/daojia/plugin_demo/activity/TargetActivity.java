package com.daojia.plugin_demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.daojia.plugin_demo.R;

/**
 * Created by allen on 17/12/4.
 */

public class TargetActivity extends Activity {

    private static final String TAG = TargetActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate() called with " + "savedInstanceState = [" + savedInstanceState + "]");
//        TextView textView = new TextView(this);
//        textView.setText("TargetActivity");

        setContentView(R.layout.activity_target);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause() called with " + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume() called with " + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called with " + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() called with " + "");
    }

}
