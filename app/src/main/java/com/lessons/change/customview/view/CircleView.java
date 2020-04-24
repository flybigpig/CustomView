package com.lessons.change.customview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * @author
 * @date 2020/4/1.
 * GitHub：
 * email：
 * description：
 */
public class CircleView extends View {


    private long width;
    private long height;
    private Paint mPaint;
    private float DEGREE_PER_CIRCLE = 360;
    private int mAnimateValue;
    private ValueAnimator mAnimator;
    private OnCountDownFinishListener mListener;
    private float mMAnimateValue;
    private float[] mWholeCircleRadius = new float[1];  //记录所有小圆半径
    private int[] mWholeCircleColors = new int[1]; //记录所有小圆颜色
    private float mMaxCircleRadius;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setTextSize(50);

    }

    private void initValue() {
        float minCircleRadius = width / 24;
        for (int i = 0; i < 12; i++) {
            switch (i) {
                case 7:
                    mWholeCircleRadius[i] = minCircleRadius * 1.25f;
                    mWholeCircleColors[i] = (int) (255 * 0.7f);
                    break;
                case 8:
                    mWholeCircleRadius[i] = minCircleRadius * 1.5f;
                    mWholeCircleColors[i] = (int) (255 * 0.8f);
                    break;
                case 9:
                case 11:
                    mWholeCircleRadius[i] = minCircleRadius * 1.75f;
                    mWholeCircleColors[i] = (int) (255 * 0.9f);
                    break;
                case 10:
                    mWholeCircleRadius[i] = minCircleRadius * 2f;
                    mWholeCircleColors[i] = 255;
                    break;
                default:
                    mWholeCircleRadius[i] = minCircleRadius;
                    mWholeCircleColors[i] = (int) (255 * 0.5f);
                    break;
            }
        }
        mMaxCircleRadius = minCircleRadius * 2;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = (long) getMeasuredWidth();
        height = (long) getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("width TAG ", width + " " + height);
        canvas.drawCircle(width / 2, height / 2, 200, mPaint);
        canvas.rotate(mMAnimateValue, width / 2, height / 2);
        canvas.drawCircle(700, 700, 10, mPaint);
        canvas.rotate(DEGREE_PER_CIRCLE, width / 4, width / 4);
    }

    private ValueAnimator getValA(long countdownTime) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);

        valueAnimator.setDuration(countdownTime);

        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.setRepeatCount(-1);

        return valueAnimator;
    }

    public void startCountDown() {

        setClickable(false);

        ValueAnimator valueAnimator = getValA(5 * 1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mMAnimateValue = Float.valueOf(String.valueOf(animation.getAnimatedValue()));
                Log.d("TAG", mMAnimateValue + "");

                invalidate();
            }
        });

        valueAnimator.start();

        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);

                //倒计时结束回调
                if (mListener != null) {

                    mListener.countDownFinished();

                }

                setClickable(true);
            }

        });
    }

    public void setAddCountDownListener(OnCountDownFinishListener mListener) {

        this.mListener = mListener;
    }

    public interface OnCountDownFinishListener {

        void countDownFinished();
    }


}
