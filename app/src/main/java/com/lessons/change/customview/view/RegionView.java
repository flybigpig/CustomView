package com.lessons.change.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author
 * @date 2020/4/2.
 * GitHub：
 * email：
 * description：
 */
public class RegionView extends View {

    private int width;
    private int height;
    private Paint mPaint;

    public RegionView(Context context) {
        super(context);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        RectF rectF = new RectF(width / 4, height / 4, width / 4 * 3, height / 4 * 3);
        path.addOval(rectF, Path.Direction.CCW);//绘制一个椭圆Path.Direction.CCW 表示逆时钟绘制
        Region region = new Region();
        region.setPath(path, new Region(width / 4, height / 4, width / 4 * 3, height / 4 * 2));//取其交集
        drawRegion(canvas, region);//绘制

        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);
        //绘制
        canvas.drawRect(rect1, mPaint);
        canvas.drawRect(rect2, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        //使用Op对两个矩形进行相交
        Region region1 = new Region(rect1);
        Region region2 = new Region(rect2);
        region1.op(region2, Region.Op.INTERSECT);
        drawRegion(canvas, region1);//绘制
    }

    private void drawRegion(Canvas canvas, Region region) {
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (iterator.next(rect)) {
            canvas.drawRect(rect, mPaint);
        }
    }
}
