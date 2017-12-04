package com.daojia.plugin_demo.intercept_activity;

import android.os.Handler;

import java.lang.reflect.Field;

/**
 * Created by allen on 17/12/4.
 */

public class AMSHookHelper {

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookActivityThreadHandler(){

        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field field = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            field.setAccessible(true);
            //获取ActivityThread对象
            Object activityThreadObject = field.get(activityThreadClass);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler handler = (Handler) mHField.get(activityThreadObject);

            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(handler,new ActivityThreadHandlerCallback(handler));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
