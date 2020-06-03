package com.lessons.change.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author
 * @date 2020/6/3.
 * GitHub：
 * email：
 * description：
 */
public class CustonCircle extends View {

    private final Context context;
    private Paint paint;
    private int lx;
    private int ty;
    private int rx;
    private int by;

    public CustonCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        lx = 0;
        ty = 0;
        rx = 115;
        by = 500;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectf = new RectF(lx, ty, rx, by);
        canvas.drawArc(rectf, 180, 180, false, paint);
    }
}
