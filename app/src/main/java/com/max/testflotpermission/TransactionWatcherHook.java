package com.max.testflotpermission;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by maxpengli on 2017/5/9.
 */

//用来监控 TransactionTooLargeException 错误
public class TransactionWatcherHook implements InvocationHandler {
    private static final String TAG = TransactionWatcherHook.class.getSimpleName();

    IBinder mBinder;
    IInterface mInterface;

    public TransactionWatcherHook(IBinder binderProxy, IInterface iInterface) {
        mBinder = binderProxy;
        mInterface = iInterface;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (objects.length >= 2 && objects[1] instanceof Parcel && mInterface != null) {
            //第二个参数对应为 Parcel 对象
            Log.e(TAG, mInterface.getClass().getName() + " transact's parameter size is " + ((Parcel)objects[1]).dataSize() + " B");
        }
        Object object = null;
        try {
            object = method.invoke(mBinder, objects);
        }catch (Exception e) {
            Log.e(TAG, "ERROR!!!! service is " + (mInterface != null ? mInterface.getClass().getName() : "null!!!"));
            e.printStackTrace();
        }
        return object;
    }
}
