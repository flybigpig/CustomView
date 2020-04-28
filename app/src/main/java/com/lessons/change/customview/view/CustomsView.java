package com.lessons.change.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CustomsView extends SurfaceView implements Callback {

    SurfaceHolder surfaceHolder;
    Thread thread;

    public CustomsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        thread = new Thread(new MyThread());
        thread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                Canvas canvas = surfaceHolder.lockCanvas();
                try {
                    Paint paint = new Paint();
                    paint.setColor(0xFFFF0000);
                    canvas.drawRect(0, 0, 100, 100, paint);
                } catch (Exception e) {

                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }

    }

}
