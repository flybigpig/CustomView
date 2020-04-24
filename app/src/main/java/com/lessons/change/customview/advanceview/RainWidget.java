package com.lessons.change.customview.advanceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lessons.change.customview.R;
import com.lessons.change.customview.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/4/21.
 * GitHub：
 * email：
 * description：
 */
public class RainWidget extends View implements Runnable {
    //画笔
    private Paint mPaint;
    //雨点集合
    private List<Drip> drips = null;
    //屏幕宽度
    private int mWidth;
    //屏幕高度
    private int mHeight;
    private List<Drip> mDrips;

    public RainWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (drips != null) {
            drips.clear();
        }
        drips = initDrips(10);
        //开启线程
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //通过for循环，遍历所有雨滴，并且画出来
        for (Drip drip : drips) {
            mPaint.setAlpha(drip.alpha);
            mPaint.setStrokeWidth(drip.width);
//            canvas.drawLine(drip.bronPoint.x, drip.bronPoint.y, drip.lengthPoint.x, drip.lengthPoint.y, mPaint);
            canvas.drawBitmap(BitmapUtils.drawableToBitmap(getContext().getResources().getDrawable(R.mipmap.ic_launcher)), drip.bronPoint.x, drip.bronPoint.y, mPaint);
        }
    }


    //方法参数为雨滴的数目
    private List<Drip> initDrips(int dripNumber) {
        mDrips = new ArrayList<>();
        for (int i = 0; i < dripNumber; i++) {
            mDrips.add(DripFactory.createDrip(mWidth, mHeight));
        }
        return mDrips;
    }


    @Override
    public void run() {

        while (true) {
            //遍历所有的雨滴，并执行一帧下雨的动作
            for (Drip drip : mDrips) {
                drip.rain(getHeight());
            }
            try {
                //线程sleep 15ms，然后调用postInvalidate()来进行重绘
                Thread.sleep(15);
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
