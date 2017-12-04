package com.daojia.plugin_demo.hook;

import android.app.Activity;
import android.app.Instrumentation;

import com.daojia.plugin_demo.dynamic_proxy.InstrumentationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by allen on 17/6/20.
 */

public class HookActivityHelper {


    public static void replaceInstrumentation(Activity activity){

            Class<?> k = Activity.class;
        try {
            //通过Activity.class 拿到 mInstrumentation字段
            Field field = k.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            //根据activity内mInstrumentation字段 获取Instrumentation对象
            Instrumentation instrumentation = (Instrumentation)field.get(activity);


            InstrumentationHandler instrumentationHandler = new InstrumentationHandler(instrumentation);

            //代理instrumentation
            Instrumentation instrumentation1 = (Instrumentation)Proxy.newProxyInstance(instrumentationHandler.getClass().getClassLoader(),instrumentation.getClass().getInterfaces(),instrumentationHandler);


            //创建代理对象
            Instrumentation instrumentationProxy = new EvilInstrumentation(instrumentation);

            //进行替换
            field.set(activity,instrumentation1);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }

    }



}
