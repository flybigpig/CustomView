package com.lessons.change.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.lessons.change.customview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartLineView extends View implements Runnable {

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
    private List<ScanResult> datas = new ArrayList<>();
    private int[] colors = new int[]{
            R.color.red, R.color.aquamarine, R.color.yellow, R.color.saddlebrown, R.color.violet_value, R.color.salmon,
            R.color.black, R.color.beige, R.color.green, R.color.greenyellow, R.color.violet, R.color.antiquewhite, R.color.thistle,
            R.color.teal, R.color.palegoldenrod, R.color.powderblue, R.color.darkorchid
    };

    List<Paint> mPaints = new ArrayList<>();

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
        YLength = point.y / 3;


        this.rows = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        this.lines = new float[]{-100, -80, -60, -40, -20, 0};

        XScale = (XLength - 200) / rows.length;
        YScale = (YLength - 100) / lines.length;

        initPaints();


    }

    private void initPaints() {
        int max = colors.length;
        Paint paint;
        for (int i = 0; i < max; i++) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setAlpha(30);
            paint.setAntiAlias(true);
            int color = new Random().nextInt(colors.length);
            paint.setColor(mContext.getResources().getColor(colors[color]));
            mPaints.add(paint);
        }
    }

    public void SetInfo(List<ScanResult> data) {
        this.datas.clear();
        this.datas = data;
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

        ScanResult scanResult = null;
        for (int l = 0; l < datas.size(); l++) {
            scanResult = datas.get(l);
            int y = (100 - Math.abs(scanResult.level)) / 20;
            float y11 = (100 - Math.abs(scanResult.level)) % 20;
            int xposition = getChannelByFrequency(scanResult.frequency) - 1;

            // 原定曲线
//            RectF rectf = new RectF(xPoint + xposition * XScale + 100 - XScale,
//                    point.y / 3 - (y * YScale + (y11 / 20 * YScale)),
//                    xPoint + xposition * XScale + 100 + XScale,
//                    point.y / 3 + (y * YScale + (y11 / 20 * YScale)));
//            int color = new Random().nextInt(colors.length);
//            Log.d("xposition color", color + " " + colors[color]);
//
//            int paintNum = new Random().nextInt(mPaints.size());
//            canvas.drawArc(rectf, 180, 180, false, mPaints.get(paintNum));
//            Log.d("xposition", "name " + scanResult.SSID + " channal   " + getChannelByFrequency(scanResult.frequency) + " level " + (scanResult.level + 100));
//
//            Log.d("xposition  xxxxxyyyyy", y + "  " + y11);

            // 改为梯形
            int paintNum = new Random().nextInt(mPaints.size());
            float x1 = xPoint + xposition * XScale + 100;
            float y1 = point.y / 3 - (y * YScale + (y11 / 20 * YScale));

            float x2 = xPoint + xposition * XScale + 100 + XScale;
            float y2 = point.y / 3;


            canvas.drawLine(x1, y1, x2, y1, mPaints.get(paintNum));
            canvas.drawLine(x1, y1, x1 - XScale, y2, mPaints.get(paintNum));
            canvas.drawLine(x2, y1, x2 + XScale, y2, mPaints.get(paintNum));

            Paint paint2 = new Paint();
            paint2.setStrokeWidth(2);
            paint2.setStyle(Paint.Style.FILL);
            canvas.drawText(scanResult.SSID, xPoint + xposition * XScale + 100 + XScale / 2,
                    point.y / 3 - (y * YScale + (y11 / 20 * YScale)), paint2);

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

    /**
     * getChannal
     *
     * @param frequency
     * @return
     */
    public static int getChannelByFrequency(int frequency) {
        int channel = -1;
        switch (frequency) {
            case 2412:
                channel = 1;
                break;
            case 2417:
                channel = 2;
                break;
            case 2422:
                channel = 3;
                break;
            case 2427:
                channel = 4;
                break;
            case 2432:
                channel = 5;
                break;
            case 2437:
                channel = 6;
                break;
            case 2442:
                channel = 7;
                break;
            case 2447:
                channel = 8;
                break;
            case 2452:
                channel = 9;
                break;
            case 2457:
                channel = 10;
                break;
            case 2462:
                channel = 11;
                break;
            case 2467:
                channel = 12;
                break;
            case 2472:
                channel = 13;
                break;
            case 2484:
                channel = 14;
                break;
            case 5745:
                channel = 149;
                break;
            case 5765:
                channel = 153;
                break;
            case 5785:
                channel = 157;
                break;
            case 5805:
                channel = 161;
                break;
            case 5825:
                channel = 165;
                break;
        }
        return channel;
    }

    @Override
    public void run() {
        boolean draw = false;
        while (true) {
            if (datas.size() > 0 && !draw) {
                invalidate();
                draw = true;
            }
        }
    }
}