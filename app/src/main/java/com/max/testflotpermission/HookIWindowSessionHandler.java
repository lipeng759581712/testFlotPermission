package com.max.testflotpermission;

import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by maxpengli on 2017/5/9.
 */

public class HookIWindowSessionHandler implements InvocationHandler {
    private static final String TAG = HookIWindowSessionHandler.class.getSimpleName();

    private Object sWindowSession;

    public HookIWindowSessionHandler(Object sWindowSession){
        this.sWindowSession = sWindowSession;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG,"333333 "+method.getName());

        if("addToDisplay".equals(method.getName()))
        {
            if((WindowManager.LayoutParams)args[2] != null){
                Log.e(TAG,"333333 addToDisplay "+((WindowManager.LayoutParams)args[2]).type);

                if( ((WindowManager.LayoutParams)args[2]).type == WindowManager.LayoutParams.TYPE_SYSTEM_ALERT){
                    ((WindowManager.LayoutParams)args[2]).type = WindowManager.LayoutParams.TYPE_TOAST;
                }

            }

        }
        else if("relayout".equals(method.getName())){
            if((WindowManager.LayoutParams)args[2] != null){
                Log.e(TAG,"333333 relayout "+((WindowManager.LayoutParams)args[2]).type);

                if( ((WindowManager.LayoutParams)args[2]).type == WindowManager.LayoutParams.TYPE_SYSTEM_ALERT){
                    ((WindowManager.LayoutParams)args[2]).type = WindowManager.LayoutParams.TYPE_TOAST;
                }

            }

        }
        else if("add".equals(method.getName())){
            if((WindowManager.LayoutParams)args[2] != null){
                Log.e(TAG,"333333 add "+((WindowManager.LayoutParams)args[2]).type);

                if( ((WindowManager.LayoutParams)args[2]).type == WindowManager.LayoutParams.TYPE_SYSTEM_ALERT){
                    ((WindowManager.LayoutParams)args[2]).type = WindowManager.LayoutParams.TYPE_TOAST;
                }

            }

        }
        else if("addWithoutInputChannel".equals(method.getName())){
            if((WindowManager.LayoutParams)args[2] != null){
                Log.e(TAG,"333333 addWithoutInputChannel "+((WindowManager.LayoutParams)args[2]).type);

                if( ((WindowManager.LayoutParams)args[2]).type == WindowManager.LayoutParams.TYPE_SYSTEM_ALERT){
                    ((WindowManager.LayoutParams)args[2]).type = WindowManager.LayoutParams.TYPE_TOAST;
                }

            }

        }
        else if("addToDisplayWithoutInputChannel".equals(method.getName())){
            if((WindowManager.LayoutParams)args[2] != null){
                Log.e(TAG,"333333 addToDisplayWithoutInputChannel "+((WindowManager.LayoutParams)args[2]).type);

                if( ((WindowManager.LayoutParams)args[2]).type == WindowManager.LayoutParams.TYPE_SYSTEM_ALERT){
                    ((WindowManager.LayoutParams)args[2]).type = WindowManager.LayoutParams.TYPE_TOAST;
                }

            }

        }



        return method.invoke(sWindowSession, args);
    }
}
