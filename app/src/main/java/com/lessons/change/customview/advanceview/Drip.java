package com.lessons.change.customview.advanceview;

import android.graphics.Point;

/**
 * @author
 * @date 2020/4/21.
 * GitHub：
 * email：
 * description：
 */
//雨滴类 以一根线条作为雨滴效果
public class Drip {
    //雨滴出生点
    public Point bronPoint;
    //长度点 **一条线由两个点构成**
    public Point lengthPoint;
    //雨滴速度
    public int speed;
    //水滴长度
    public int height;
    //雨滴宽度
    public int width;
    //雨滴透明度
    public int alpha;
    //屏幕宽度
    private int mScreenWidth;
    //屏幕高度
    private int mScreenHeight;

    public Drip(int screenWidth, int screenHeight, int speed, int height, int width, int alpha, int tilt) {

    }

    public Drip(int mScreenWidth, int mScreenHeight, int speed, int height, int width, int alpha) {
        this.mScreenWidth = mScreenWidth;
        this.mScreenHeight = mScreenHeight;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.alpha = alpha;
        //雨滴一旦被创建，就调用initPoint()方法，
        //在手机屏幕随机生成雨滴的两个点
        initPoint(mScreenWidth, mScreenHeight);
    }

    //该方法用于设置雨滴两个点的坐标
    private void initPoint(int screenWidth, int screenHeight) {
        //出生点的设置。第一次打开应用，出生点一定是随机生成的
        //所以x坐标随机范围是(0,屏幕宽度)
        //所以y坐标随机范围是(0,屏幕高度)
        bronPoint = new Point((int) (Math.random() * screenWidth), (int) (Math.random() * screenHeight));
        //第二个点的坐标就好确定了
        //x坐标就是第一个点的坐标
        //y坐标可以控制雨滴的长度，也就是线条的长度
        //y坐标就是第一个点的y坐标+雨滴长度
        lengthPoint = new Point(bronPoint.x + 5, bronPoint.y + height);
    }

    //rain()方法，是雨滴下落的效果
    //雨滴下落是由线条两个点的坐标变化而变化的
    //首先，两个点的x轴不会发生变化，而y轴的增减量是相同的
    public void rain(int screenHeight) {
        //通过Point.offset()方法，使得y点增加一个speed值
        //这只是每一帧动画的效果
        bronPoint.offset(0, speed);
        lengthPoint.offset(0, speed);
        //当雨滴的y坐标大于屏幕高度，那么就重新生成雨滴的两个点的坐标
        //第一个参数是控制生成雨滴x坐标的范围
        //第二个参数是控制生成雨滴y坐标的范围
        //因为重新生成的雨滴必须是从屏幕最上面落下来，所以第二个参数默认是0
        if (bronPoint.y > mScreenHeight) {
            initPoint(mScreenWidth, 0);
        }
    }
}
