package com.lessons.change.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * @author
 * @date 2020/4/28.
 * GitHub：
 * email：
 * description：
 */
public class SurfacesView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private SurfaceHolder surfaceHolder;
    private int TIME_IN_FRAME = 50;
    private Canvas mCanvas;

    public SurfacesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        mCanvas = new Canvas();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        int x = 0, y = 0;
        while (true) {

            long startTime = System.currentTimeMillis();
            mCanvas = surfaceHolder.lockCanvas();
            try {

                Paint paints = new Paint();
                paints.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mCanvas.drawPaint(paints);
                paints.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                mCanvas.drawText("asdasdasda", x, y, paint);
            } catch (Exception e) {

            } finally {
                Log.d("canvas", "surface");
                surfaceHolder.unlockCanvasAndPost(mCanvas);
            }
            x += 2;
            y += 2;

            /** 取得更新游戏结束的时间 **/
            long endTime = System.currentTimeMillis();

            /** 计算出游戏一次更新的毫秒数 **/
            int diffTime = (int) (endTime - startTime);

            /** 确保每次更新时间为50帧 **/
            while (diffTime <= TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                /** 线程等待 **/
                Thread.yield();
            }
        }
    }
}
