package com.max.testflotpermission;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    private WindowManager mWindowManager;
    //创建浮动窗口设置布局参数的对象
    private WindowManager.LayoutParams wmParams;
    private LinearLayout mFloatLayout;

    private boolean isshow = false;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"start hook");
        WindowManagerHook.hookService(this);
        Log.e(TAG,"end hook");
        mWindowManager = (WindowManager)getApplication().getSystemService(Context.WINDOW_SERVICE);
        initWindow();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
                show();

                break;
            case R.id.hide:
                hide();
                break;
            default:
                break;
        }
    }


    private void initWindow() {

        wmParams = new WindowManager.LayoutParams();

        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.TOP|Gravity.LEFT;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = LayoutInflater.from(this);
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.activity_float, null);
        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
            private static final int TOUCH_SLOP = 8;
            private float mTouchStartX;
            private float mTouchStartY;
            private float dx;
            private float dy;
            private float x;
            private float y;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                        //获取相对屏幕的坐标，即以屏幕左上角为原点
                        mTouchStartX = event.getRawX();
                        mTouchStartY = event.getRawY();
                        x = wmParams.x;
                        y = wmParams.y;

                        break;
                    case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作

                        dx = event.getRawX() - mTouchStartX;
                        dy = event.getRawY() - mTouchStartY;
                        if (Math.abs(dx) >= TOUCH_SLOP || Math.abs(dy) >= TOUCH_SLOP) {
                            //更新浮动窗口位置参数
                            wmParams.x = (int) (x + dx);
                            wmParams.y = (int) (y + dy);
                            mWindowManager.updateViewLayout(view, wmParams);  //刷新显示
                        }

                        break;
                    case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                        break;
                }
                return true;
            }
        });
        isInit = true;
    }

    public void show(){
        Log.i("xp","isInit=="+isInit+"   "+"isshow=="+isshow);
        if(!isInit){
            return;
        }
        if (isshow){
            return;
        }
        mWindowManager.addView(mFloatLayout, wmParams);
        isshow = true;

    }

    public void hide(){
        if(!isInit){
            return;
        }
        if (!isshow){
            return;
        }
        mWindowManager.removeView(mFloatLayout);
        isshow = false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        hide();
    }
}
