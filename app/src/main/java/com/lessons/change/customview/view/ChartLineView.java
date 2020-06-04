package com.lessons.change.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChartLineView extends View {

    private Context mContext;
    public Point point = new Point();
    private int xPoint = 80;
    private int yPoint = 0;

    public int XScale = 50;     //X的刻度长度
    public int YScale = 100;
    private int XLength;
    private int YLength;
    private float[] lines;
    private String[] rows;
    private List<Integer> datas = new ArrayList<>();


    public ChartLineView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }


    public ChartLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        point.x = width;
        point.y = height;

        yPoint = point.y / 3;
        XLength = point.x - 100;        //X轴的长度
        YLength = point.y;

        float[] s2 = new float[]{};

        datas.add(50);
        datas.add(60);
        datas.add(20);
        datas.add(35);
        this.rows = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
        this.lines = new float[]{-100, -80, -60, -40, -20, 0};
    }

    public void SetInfo(List<Integer> data) {
        this.datas = data;
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        setMeasuredDimension(width, height / 3 + 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//重写onDraw方法

//        canvas.drawColor(Color.BLACK);//设置背景颜色
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.BLACK);//颜色
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);//去锯齿
        paint1.setColor(Color.BLACK);
        paint.setTextSize(12);  //设置轴文字大小

        canvas.drawLine(xPoint, 50, xPoint, point.y / 3, paint);
        canvas.drawLine(xPoint, point.y / 3, xPoint + point.x - 100, point.y / 3, paint);


        canvas.drawLine(xPoint, 50, xPoint - 10, 60, paint);  //箭头
        canvas.drawLine(xPoint, 50, xPoint + 10, 60, paint);

        canvas.drawLine(xPoint + point.x - 100, point.y / 3, xPoint + point.x - 100 - 10, point.y / 3 - 10, paint);  //箭头
        canvas.drawLine(xPoint + point.x - 100, point.y / 3, xPoint + point.x - 100 - 10, point.y / 3 + 10, paint);

        //lines
        for (int i = 0; i * YScale < YLength && i < lines.length; i++) {
            paint.setTextSize(25);
            paint.setStrokeWidth(1);
            canvas.drawText(String.valueOf(lines[i]), xPoint - 70, point.y / 3 - i * YScale, paint);
            Log.d("yyyyyyyy", "text： " + String.valueOf(lines[i]) + " Y: " + (point.y / 3 - i * YScale) + "  i" + i);
        }

        // rows
        for (int i = 0; i * XScale < XLength && i < rows.length; i++) {
            paint.setTextSize(25);
            paint.setStrokeWidth(1);
//            canvas.drawText(rows[i], xPoint + i * XScale, point.y / 2 + 30, paint);
            drawText(canvas, rows[i], xPoint + i * XScale + 100, point.y / 3 + 50, paint, -45);
        }

        paint.setStrokeWidth(3);


        for (int l = 0; l < datas.size(); l++) {

            float y = (100 - datas.get(l)) / 20;
            float y11 = (100 - datas.get(l)) % 20;

            Log.d("yyyyyyyy", y + "  " + y11 + "  " + (point.y / 3 - (y * YScale + y11)));

            RectF rectf = new RectF(xPoint + l * 2 * XScale + 100 - XScale,
                    point.y / 3 - (y * YScale + (y11 / 20 * YScale)),
                    xPoint + l * 2 * XScale + 100 + XScale,
                    point.y / 3 + (y * YScale + (y11 / 20 * YScale)));
            canvas.drawArc(rectf, 180, 180, false, paint);

        }

    }

    //设置文字显示方向
    void drawText(Canvas canvas, String text, float x, float y, Paint paint, float angle) {
        if (angle != 0) {
            canvas.rotate(angle, x, y);

        }
        canvas.drawText(text, x, y, paint);
        if (angle != 0) {
            canvas.rotate(-angle, x, y);
        }
    }
}