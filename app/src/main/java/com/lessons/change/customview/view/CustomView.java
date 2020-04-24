package com.lessons.change.customview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.lessons.change.customview.R;
import com.lessons.change.customview.utils.BitmapUtils;

/**
 * @author
 * @date 2020/3/19.
 * GitHub：
 * email：
 * description：
 */
public class CustomView extends View {

    private Context context;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private OnCountDownFinishListener mListener;
    private int mCountdownTime;
    private int step = 0;
    private ValueAnimator mValueAnimator;
    private int position;

    public CustomView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // window
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

//        mWidth = displayMetrics.widthPixels;
//        mHeight = displayMetrics.heightPixels;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mPaint = new Paint();

        mPaint.setColor(Color.BLACK);

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(15);

        Log.d("valueAnimators", step + " " + " " + mHeight);


        drawMyCar(canvas);

        drawCar1(canvas);

        drawCar2(canvas);

        drawCar3(canvas);
    }

    public void drawMyCar(Canvas canvas) {
        Log.d("drawMyCar", mWidth + "");
        int drawableHeight = getResources().getDrawable(R.mipmap.ic_launcher).getIntrinsicHeight();
        int drawableWidth = getResources().getDrawable(R.mipmap.ic_launcher).getIntrinsicWidth();
        if (position == -1) {
            canvas.drawBitmap(BitmapUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.ic_launcher)), 100, mHeight - drawableHeight, mPaint);
        } else if (position == 0) {
            canvas.drawBitmap(BitmapUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.ic_launcher)), (mWidth / 2) - (drawableWidth / 2), mHeight - drawableHeight, mPaint);
        } else if (position == 1) {
            canvas.drawBitmap(BitmapUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.ic_launcher)), 600, mHeight - drawableHeight, mPaint);
        }
    }

    public void drawCar1(Canvas canvas) {
        if (step < mHeight) {
            canvas.drawBitmap(BitmapUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.ic_launcher)), 230, 100 + step, mPaint);
        } else {
            step = 0;
        }
    }

    public void drawCar2(Canvas canvas) {
        if (step < mHeight) {
            canvas.drawBitmap(BitmapUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.ic_launcher)), 800, step, mPaint);
        } else {
            step = 0;
        }

    }


    public void drawCar3(Canvas canvas) {

    }

    public void setCountdownTime(int mCountdownTime) {

        this.mCountdownTime = mCountdownTime;

    }

    private ValueAnimator getValA(long countdownTime) {

        mValueAnimator = ValueAnimator.ofFloat(0, 100);

        mValueAnimator.setDuration(1200);

        mValueAnimator.setInterpolator(new LinearInterpolator());

        mValueAnimator.setRepeatCount(0);

        mValueAnimator.setupEndValues();

        return mValueAnimator;
    }

    public void startCountDown() {

        setClickable(false);

        ValueAnimator valueAnimator = getValA(mCountdownTime * 1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float ii = Float.valueOf(String.valueOf(animation.getAnimatedValue()));

                step = step + (int) ii;

//                Log.d("valueAnimator", ii + "");

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                Log.d("TOUCH", x + "");
                if (x <= mWidth / 2) {
                    position = -1;
                } else {
                    position = 1;
                }
                break;
        }
        return true;
    }

    public void setAddCountDownListener(OnCountDownFinishListener mListener) {

        this.mListener = mListener;
    }

    public interface OnCountDownFinishListener {

        void countDownFinished();
    }
}
