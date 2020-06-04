package com.lessons.change.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
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
        paint.setStrokeWidth(5);
        int[] colors = {Color.WHITE, Color.BLUE, Color.YELLOW};
        float[] positions = {0.25f, 0.5f, 1f};
        SweepGradient sweepGradient = new SweepGradient(0, 510, colors, positions);
//        paint.setShader(sweepGradient);
        lx = 0;
        ty = 0;
        rx = 120;
        by = 510;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectf = new RectF(0, 0, 210, 500);
        canvas.drawArc(rectf, 180, 180, false, paint);
        canvas.drawRect(rectf, paint);

        RectF rectfs = new RectF(210, 0, 420, 400);
        canvas.drawRect(rectfs, paint);
        canvas.drawArc(rectfs, 180, 180, false, paint);
    }
}
