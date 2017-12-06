package com.daojia.plugin_demo.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import com.daojia.plugin_demo.activity.StubActivity;
import com.daojia.plugin_demo.intercept_activity.AMSHookHelper;

import java.lang.reflect.Method;

/**
 */
public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EvilInstrumentation";

    // ActivityThread中原始的对象, 保存起来
    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation base) {
        mBase = base;
    }

    public Intent daojiaIntent(){
        Uri uri = Uri.parse("https://bj.daojia.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        return intent;
    }


    public Intent stubIntent(Intent originIntent){
        Intent newIntent = new Intent();
        String stubPackage = "com.daojia.plugin_demo";
        ComponentName componentName = new ComponentName(stubPackage, StubActivity.class.getName());
        newIntent.setComponent(componentName);
        newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT,originIntent);
        return newIntent;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
//          用来测试 targetActivity 替换为 stubActivity
//        Intent newIntent = stubIntent(intent);


        //baidu url 替换为 daojai url
        Intent newIntent = stubIntent(intent);


        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        try {

            //这里通过反射找到原始Instrumentation类的execStartActivity方法
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);

            //执行原始Instrumentation的execStartActivity方法 再次之前 you can do whatever you want ...
            return (ActivityResult) execStartActivity.invoke(mBase, who,
                    contextThread, token, target, newIntent, requestCode, options);
        } catch (Exception e) {
            // 某该死的rom修改了  需要手动适配
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }
}

