package com.daojia.plugin_demo.intercept_activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by allen on 17/12/4.
 */

public class ActivityThreadHandlerCallback implements Handler.Callback {

    private static final String TAG = ActivityThreadHandlerCallback.class.getName();

    Handler handler;

    public ActivityThreadHandlerCallback(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {

        if (msg.what == 100){
            //拦截

            handleLaunchActivity(msg);

        }

//        handler.handleMessage(msg);
//      return false，表示只会拦截修改下Intent为TargetActivity,如果return true
//      则需要加上 handler.handleMessage(msg) 让handler处理其它消息
        return false;
    }

    private void handleLaunchActivity(Message msg) {

//        Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
//        final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
//
//        r.packageInfo = getPackageInfoNoCheck(
//                r.activityInfo.applicationInfo, r.compatInfo);
//        handleLaunchActivity(r, null, "LAUNCH_ACTIVITY");
//        Trace.traceEnd(Trace.TRACE_TAG_ACTIVITY_MANAGER);

        Object object = msg.obj;

        try {
            Field fieldIntent =  object.getClass().getDeclaredField("intent");
            fieldIntent.setAccessible(true);
            Intent intent = (Intent) fieldIntent.get(object);

            Intent target = intent.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);

            Log.e(TAG,intent + "       " +  target.toString());

            intent.setComponent(target.getComponent());



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
