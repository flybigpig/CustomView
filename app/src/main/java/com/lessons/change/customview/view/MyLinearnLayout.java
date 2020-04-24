package com.lessons.change.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author
 * @date 2020/4/24.
 * GitHub：
 * email：
 * description：
 */
public class MyLinearnLayout extends ViewGroup {


    public MyLinearnLayout(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;//记录已用的高度
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);//获取子View
            view.layout(0, top, view.getMeasuredWidth(), top + view.getMeasuredHeight());//对子View进行布局，也就是将子View进行摆放
            top += view.getMeasuredHeight();//累加已用高度
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //【1】获取尺寸信息
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //【2】测量所有子布局
        int contentWidth = 0;//所有子View的最大宽度
        int contentHeight = 0;//所有子View的高度总和
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            contentWidth = Math.max(contentWidth, view.getMeasuredWidth());
            contentHeight += view.getMeasuredHeight();
        }
        //【3】处理AT_MOST和UNSPECIFIED(如果是这两种模式那么设置当前ViewGroup为content的尺寸)
        if (wMode == MeasureSpec.UNSPECIFIED || wMode == MeasureSpec.AT_MOST) {
            width = contentWidth;
        }
        if (hMode == MeasureSpec.UNSPECIFIED || hMode == MeasureSpec.AT_MOST) {
            height = contentHeight;
        }
        //【4】设置我们需要的尺寸（这里的尺寸通常但并不总是会被作为View的最终尺寸，最终View的大小是在上一级View在对子View进行layout中被决定的）
        setMeasuredDimension(width, height);
    }
}
