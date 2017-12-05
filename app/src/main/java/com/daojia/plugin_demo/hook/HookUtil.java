package com.daojia.plugin_demo.hook;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by allen on 17/6/22.
 */

public class HookUtil {
    private static final String TAG = "HookUtil";
    public void hookIActivityManager() {
        try {
            Class<?> c = Class.forName("android.app.ActivityManagerNative");

            Field field = c.getDeclaredField("gDefault");

            field.setAccessible(true);

            Log.e(TAG,field.getName() + "      " + field.toString());

            //返回ActivityManagerNative对象
            Object amn = field.get(null);

            Class<?> k = Class.forName("android.util.Singleton");

            Field field1 = k.getDeclaredField("mInstance");
            Log.e(TAG,field1.getName() + "      " + field1.toString());

            field1.setAccessible(true);

            //拿到ActivityManagerProxy对象 代理ActivityManagerNative对象的子类ActivityManagerService
            //因为在gDefault对象的 实现方法 onCreate()方法中 asInterface(b)返回的是  return new ActivityManagerProxy(obj)  ActivityManagerProxy实现了IActivityManager 具体可以看源码

            Object iActivityManager = field1.get(amn);

            IamInvocationHandler iamInvocationHandler = new IamInvocationHandler(iActivityManager);

            //第一个参数 我们使用IamInvocationHandler这个类的ClassLoader来加载我们的代理类对象
            Object object = Proxy.newProxyInstance(iamInvocationHandler.getClass().getClassLoader(),iActivityManager.getClass().getInterfaces(),iamInvocationHandler);

            //使用Singleton 变量mInstance做替换 防止IllegalArgumentException 猜想
            field1.set(amn,object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class IamInvocationHandler implements InvocationHandler {

        Object iamObject;

        public IamInvocationHandler(Object iamObject) {
            this.iamObject = iamObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            Log.e(TAG, method.getName());
            if ("startActivity".equals(method.getName())) {
                Log.e(TAG, "要开始启动了 啦啦啦啦啦啦  ");
            }
            return method.invoke(iamObject, args);
        }
    }
}
