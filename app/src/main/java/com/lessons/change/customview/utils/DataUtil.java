package com.lessons.change.customview.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 * @date 2020/4/15.
 * GitHub：
 * email：
 * description：
 */
public class DataUtil {

    private String getDate() {
        //创建SimpleDateFormat对象，指定样式    2019-05-13 22:39:30
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        //2019年的第133天。占位符是特定的
//        SimpleDateFormat sdf2 = new SimpleDateFormat("y年的第D天");
        //要格式化的Date对象
        Date date = new Date();
        //使用format()方法格式化Date对象为字符串，返回字符串
//        System.out.println(sdf1.format(date));

        return sdf1.format(date);
    }
}
