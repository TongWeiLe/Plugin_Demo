package com.daojia.plugin_demo.dynamic_proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by allen on 17/6/22.
 */

public class InstrumentationHandler implements InvocationHandler {

    private static final String TAG = "InstrumentationHandler";

    Object mBase;

    public InstrumentationHandler(Object mBase) {
        this.mBase = mBase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Log.e(TAG,"哈哈哈哈 我来了");

        return method.invoke(mBase,args);

    }


}
