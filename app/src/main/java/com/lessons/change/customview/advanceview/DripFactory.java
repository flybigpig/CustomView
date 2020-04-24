package com.lessons.change.customview.advanceview;

/**
 * @author
 * @date 2020/4/21.
 * GitHub：
 * email：
 * description：
 */
//雨滴工厂，批量生产雨滴
public class DripFactory {

    public static Drip createDrip(int mScreenWidth, int mScreenHeight) {
        //以下四个属性的值，都是通过随机来生成的
        //笔者因为偷懒，并没有将随机的默认值和最大值抽离出来
        //如果抽离出来，就可以在xml文件中动态控制各个属性
        int speed = (int) (Math.random() * 5 + 2);  //默认最小5 最大15
        int width = (int) (Math.random() * 8 + 3);
        int alpha = (int) (Math.random() * 200 + 20);
        int height = (int) (Math.random() * 30 + 20);
        Drip drip = new Drip(mScreenWidth, mScreenHeight, speed, height, width, alpha);
        return drip;
    }
}
