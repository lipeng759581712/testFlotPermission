package com.max.testflotpermission;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Description:
 *
 * @author Shawn_Dut
 * @since 2017-02-21
 */
public class WindowManagerHook {

    private static final String TAG = WindowManagerHook.class.getSimpleName();

    public static void hookService(Context context) {
        IBinder windowManagerService = ServiceManager.getService(Context.WINDOW_SERVICE);


        if (windowManagerService != null) {
            IBinder hookWindowManagerService =
                    (IBinder) Proxy.newProxyInstance(windowManagerService.getClass().getClassLoader(),
                            windowManagerService.getClass().getInterfaces(),
                            new ServiceHook(windowManagerService));

            ServiceManager.setService(Context.WINDOW_SERVICE, hookWindowManagerService);
            init();

        } else {
            Log.e(TAG, "ClipboardService hook failed!");
        }
    }


    public static void init(){
        try {
            Class clazz = Class.forName("android.view.WindowManagerGlobal");

            Field sWindowManagerService = clazz.getDeclaredField("sWindowManagerService");
            Field sWindowSession = clazz.getDeclaredField("sWindowSession");

            sWindowManagerService.setAccessible(true);
            sWindowSession.setAccessible(true);

            sWindowManagerService.set(null, null);
            sWindowSession.set(null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
