package com.max.testflotpermission;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by maxpengli on 2017/5/9.
 */

public class HookHandler implements InvocationHandler {
    private static final String TAG = HookHandler.class.getSimpleName();

    private Object IWindowManagerProxy;
    private Class<?> mStub;

    public HookHandler(IBinder windowManagerService) {

        try {
            this.mStub = Class.forName("android.view.IWindowManager$Stub");
            Method asInterface = this.mStub.getDeclaredMethod("asInterface", IBinder.class);
            this.IWindowManagerProxy = asInterface.invoke(null, windowManagerService);


//                Class clazz = mBase.getClass();
//                Field mRemote = clazz.getDeclaredField("mRemote");
//                mRemote.setAccessible(true);
//                //新建一个 BinderProxy 的代理对象
//                Object binderProxy = Proxy.newProxyInstance(mBase.getClass().getClassLoader(),
//                        new Class[] {IBinder.class}, new TransactionWatcherHook((IBinder) mRemote.get(mBase), (IInterface) mBase));
//                mRemote.set(mBase, binderProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG,"222222 "+method.getName());



        if("openSession".equals(method.getName())){

            Object sWindowSession = method.invoke(IWindowManagerProxy, args);

            Class<?>  IWindowSessionInterface = Class.forName("android.view.IWindowSession");

            return Proxy.newProxyInstance(sWindowSession.getClass().getClassLoader(), new Class[]{IWindowSessionInterface},
                    new HookIWindowSessionHandler(sWindowSession));
        }


        return method.invoke(IWindowManagerProxy, args);
    }
}
