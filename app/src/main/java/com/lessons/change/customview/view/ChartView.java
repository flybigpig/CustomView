package com.lessons.change.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChartView extends View {

    private Context mContext;
    public Point point = new Point();
    private int xPoint = 80;
    private int yPoint = 0;

    public int XScale = 150;     //X的刻度长度
    public int YScale = 120;
    private int XLength;
    private int YLength;
    private float[] lines;
    private String[] rows;
    private List<float[]> data = new ArrayList<>();
    private List<float[]> datas = new ArrayList<>();


    public ChartView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }


    public ChartView(Context context, @Nullable AttributeSet attrs) {
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

        yPoint = point.y / 2;
        XLength = point.x - 100;        //X轴的长度
        YLength = point.y / 2 - 100;

        float[] s1 = new float[]{6, 56, 39, 100};
        float[] s2 = new float[]{26, 156, 139, 50};

        datas.add(s1);
        datas.add(s2);
        SetInfo(new String[]{"7-11", "7-12", "7-13", "7-14", "7-15", "7-16", "7-17"}, // X轴刻度
                new float[]{0, 50, 100, 150, 200, 250}, // Y轴刻度
                datas);
    }

    private void SetInfo(String[] rows, float[] lines, List<float[]> data) {
        this.lines = lines;
        this.rows = rows;
        this.data = data;

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

        canvas.drawLine(xPoint, 100, xPoint, point.y / 2, paint);
        canvas.drawLine(xPoint, point.y / 2, xPoint + point.x - 100, point.y / 2, paint);


        canvas.drawLine(xPoint, 100, xPoint - 10, 100 + 10, paint);  //箭头
        canvas.drawLine(xPoint, 100, xPoint + 10, 100 + 10, paint);

        canvas.drawLine(xPoint + point.x - 100, point.y / 2, xPoint + point.x - 100 - 10, point.y / 2 - 10, paint);  //箭头
        canvas.drawLine(xPoint + point.x - 100, point.y / 2, xPoint + point.x - 100 - 10, point.y / 2 + 10, paint);

        //lines
        for (int i = 0; i * YScale < YLength && i < lines.length; i++) {
            paint.setTextSize(25);
            paint.setStrokeWidth(1);
            canvas.drawText(String.valueOf(lines[i]), xPoint - 70, point.y / 2 - i * YScale, paint);
        }


        for (int i = 0; i * XScale < XLength && i < rows.length; i++) {
            paint.setTextSize(25);
            paint.setStrokeWidth(1);
            canvas.drawText(rows[i], xPoint + i * XScale, point.y / 2 + 30, paint);
        }

        paint.setStrokeWidth(3);
        for (int l = 0; l < datas.size(); l++) {
            for (int d = 0; d < datas.get(l).length; d++) {
                float[] data = datas.get(l);
                for (int i = 0; i < data.length - 1; i++) {
                    float lstage = (data[i] / 50);
                    float ldalteY = data[i] % 50;
                    float estage = (data[i + 1] / 50);
                    float edalteY = data[i + 1] % 50;
                    canvas.drawLine(xPoint + i * XScale, yPoint - (lstage * YScale + ldalteY), xPoint + (i + 1) * XScale, yPoint - (estage * YScale + edalteY), paint);
                }
            }
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