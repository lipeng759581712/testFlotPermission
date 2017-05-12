package com.max.testflotpermission;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:
 *
 * @author Shawn_Dut
 * @since 2017-02-21
 */
public class ServiceHook implements InvocationHandler {
    private static final String TAG = "ServiceHook";

    private IBinder windowManagerService;
    private Class<?> mInterface;

    public ServiceHook(IBinder windowManagerService) {
        this.windowManagerService = windowManagerService;
        try {
            this.mInterface = Class.forName("android.view.IWindowManager");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG,"111111 "+method.getName());

        if ("queryLocalInterface".equals(method.getName())) {

            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{mInterface},
                    new HookHandler(windowManagerService));
        }
        return method.invoke(windowManagerService, args);
    }




}
