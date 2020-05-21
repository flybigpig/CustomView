package com.lessons.change.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.lessons.change.customview.R;
import com.lessons.change.customview.utils.BitmapUtils;
import com.lessons.change.customview.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author
 * @date 2020/5/13.
 * GitHub：
 * email：
 * description：
 */
public class MeterView extends View implements Runnable {

    private Context mContext;

    private Paint mPaint;
    private int mRightDownX;
    private int mRightDownY;
    private float CONNER = 30;
    private float mCircleRadius;
    private final float PADDING = UiUtils.dpToPixel(45);
    private float mStartX = UiUtils.dpToPixel(45);
    private float mStartY = UiUtils.dpToPixel(45);
    private float mCenterX;
    private float mCenterY;
    private List<Integer> texts = new ArrayList<>();
    private static Integer speed = 0;
    private int mWidth;
    private int mHeight;

    public MeterView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MeterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(UiUtils.dpToPixel(5));
        texts.add(0);
        texts.add(5);
        texts.add(10);
        texts.add(20);
        texts.add(50);
        texts.add(100);
        texts.add(200);

    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
//        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        if (width > 0 && height > 0) {
            mRightDownX = width;
            mRightDownY = height;
            // 根据被赋予的画布大小，计算半径
            // 判断高度，高度的分界线为r+sinConner*r(此时的半径为width/2)
            if (mRightDownY >= ((1 + Math.sin(CONNER * Math.PI / 180)) * (mRightDownX / 2))) {
                mCircleRadius = mRightDownX / 2 - PADDING;
            } else {
                // 获取与要画圆的正切的正方形的边长
                float minLength = Math.min(mRightDownX, mRightDownY) - PADDING;
                // 获取圆的半径
                // 由于不是半圆，要多画10度，需要算出这10度的y的高度(高度/(1+sin10))
                mCircleRadius = (float) ((minLength / (1 + Math.sin(CONNER * Math.PI / 180))));
            }

            mCenterX = mStartX + mCircleRadius;
            mCenterY = mStartY + mCircleRadius;
            postInvalidate();
        }
        new Thread(this).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDashArc(canvas);
        drawText(canvas);
    }


    private void drawDashArc(Canvas canvas) {
        mPaint = new Paint();
        float radiusWidth = 15;
        mPaint.setStrokeWidth(radiusWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setColor(mContext.getResources().getColor(R.color.colorAccent));
        int[] colors = {Color.parseColor("#6CCBFE"), Color.parseColor("#16A4F6"), Color.parseColor("#6CCBFE")};
        float[] positions = {0.25f, 0.5f, 1f};
        SweepGradient sweepGradient = new SweepGradient(mCenterX, mCenterY, colors, positions);
        mPaint.setShader(sweepGradient);
        // 画弧线
        RectF rectF = new RectF(mStartX + radiusWidth / 2, mStartY + radiusWidth / 2,
                mCircleRadius * 2 + mStartX - radiusWidth / 2, mCircleRadius * 2 + mStartY - radiusWidth / 2);
//        canvas.drawArc(rectF, 180 - CONNER, 180 + CONNER * 2, false, mPaint);

        int flag = 0;
        for (int i = 0; i < texts.size() - 1; i++) {
            if (texts.get(i) <= speed && texts.get(i + 1) > speed) {
                flag = i;
                break;
            } else {
                flag = texts.size() - 1;
            }
        }
        Log.d("flag", flag + "" + texts.get(flag));
        float angle = 0;
        if (flag == 6) {
            angle = 0;
        } else {
            angle = (float) ((speed - texts.get(flag)) * 0.1 / (texts.get(flag + 1) - texts.get(flag)) * (180 + 2 * CONNER) / 6) * 10;
        }

        Log.d("flag_angle", angle + " ");
        RectF rectFs = new RectF(mStartX + radiusWidth / 2 + 20, mStartY + radiusWidth / 2 + 20,
                mCircleRadius * 2 + mStartX - radiusWidth / 2 - 20, mCircleRadius * 2 + mStartY - radiusWidth / 2 - 20);
        if (flag == texts.size() - 1) {
            canvas.drawArc(rectFs, 180 - CONNER, 180 + CONNER * 2, false, mPaint);
        } else {
            canvas.drawArc(rectFs, 180 - CONNER, (180 + CONNER * 2) / 6 * flag + angle, false, mPaint);
        }

        Bitmap bitmapss = BitmapUtils.drawableToBitmap(mContext.getResources().getDrawable(R.drawable.icon_progre));
        int width = bitmapss.getWidth();
        int height = bitmapss.getHeight();
        float scaleWidth = (float) (mWidth) / width;
        float scaleHeight = (float) (mHeight) / height;
        float scale = Math.min(scaleWidth, scaleHeight);
        Bitmap bitmaps = BitmapUtils.big(bitmapss, scale);
        canvas.drawBitmap(bitmaps, 0, 0, mPaint);
    }

    private void drawText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#16A4F6"));
        paint.setTextSize(mCircleRadius / 2);

        if (speed >= 0 && speed < 10) {
            canvas.drawText(String.valueOf(speed), mCenterX - mCircleRadius / 6, mCenterY, paint);
        } else if (speed < 100 && speed >= 10) {
            canvas.drawText(String.valueOf(speed), mCenterX - mCircleRadius / 4, mCenterY, paint);
        } else if (speed > 100) {
            canvas.drawText(String.valueOf(speed), mCenterX - mCircleRadius / 3, mCenterY, paint);
        }
    }


    private void initScale(Canvas canvas) {
        mPaint = new Paint();

        float connreStart = 2 * CONNER - 180;
        canvas.rotate(connreStart, mCenterX, mCenterY);
        for (int i = 0; i < 7; i++) {
            mPaint.setTextSize(30);
            mPaint.setColor(mContext.getResources().getColor(R.color.black));
//            canvas.drawLine(mCenterX, mStartY, mCenterX, mStartY + UiUtils.dpToPixel(10), mPaint);
            canvas.drawText(texts.get(i).toString(), mCenterX - texts.get(i).toString().length() * 15, mStartY - 10, mPaint);
            canvas.rotate((180 + CONNER * 2) / 6, mCenterX, mCenterY);
        }
    }

    @Override
    public void run() {
        while (true) {
            this.setSpeed(new Random().nextInt(200));
            postInvalidate();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
