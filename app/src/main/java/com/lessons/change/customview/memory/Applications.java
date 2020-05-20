package com.lessons.change.customview.memory;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lessons.change.customview.fbid.util.FlurryUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author
 * @date 2020/4/14.
 * GitHub：
 * email：
 * description：
 */
public class Applications extends Application {

    private long MEMORY_SIZE_CONTROL = 10;
    private long lastAvaMemory = -1;
    public static boolean isLight = true;
    private Timer mTimer1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:  //ava
                    // 比较可用内存 并且 监听亮屏
                    if (isLight) {
                        if (Long.valueOf(msg.obj.toString()) - lastAvaMemory > 10) {
                            Log.d("ADRequest", "内存抖动大于10，请求广告" + msg.obj);
                        } else {
                            Log.d("ADRequest", "内存抖动小于10，不请求广告");
                        }
                        lastAvaMemory = Long.valueOf(msg.obj.toString());

                    } else if (!isLight) {  //熄屏
                        Log.d("ADRequest", "熄屏ing");
                    }

                    Log.d("ava memory", String.valueOf(msg.obj));
                    break;
            }
        }
    };
    private TimerTask mTimerTask1;
    private ScreenStatusReceiver mScreenStatusReceiver;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void initFlurry() {
        FlurryUtils.getInstance().init(getApplicationContext(), "BGC4HW3F855NQ6MZZQD8");
    }

    private void testMemory() {

        mTimer1 = new Timer();
        mTimerTask1 = new TimerTask() {
            @Override
            public void run() {

                long mAva = SystemMemory.AvailMemoryGet(getApplicationContext());

                Message avaMsg = new Message();
                avaMsg.what = 0;
                avaMsg.obj = mAva;
                mHandler.sendMessage(avaMsg);

            }
        };

        mTimer1.schedule(mTimerTask1, 0, 2000);

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_COMPLETE://应用程序不可见-内存低-位于LRU底部-80-应用程序可能会被杀死
                Log.d("TRIM_MEMORY_COMPLETE", TRIM_MEMORY_COMPLETE + "");
                break;
            case TRIM_MEMORY_MODERATE://应用程序不可见-内存低-位于LRU中部-60-设备当前的运行环境内存较少
                Log.d("TRIM_MEMORY_MODERATE", TRIM_MEMORY_MODERATE + "");
                break;
            case TRIM_MEMORY_BACKGROUND://应用程序不可见-内存低-位于LRU顶部，但是位置在下降-40--设备当前的运行环境内存较少
                Log.d("TRIM_MEMORY_BACKGROUND", TRIM_MEMORY_BACKGROUND + "");
                break;

            case TRIM_MEMORY_UI_HIDDEN://应用程序不可见-20-程序处于后台应当释放一些内存
                Log.d("TRIM_MEMORY_UI_HIDDEN", TRIM_MEMORY_UI_HIDDEN + "");
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL://应用程序可见-15-内存紧张-位于LRU顶部-其他进程可能会销毁以获得更多可用内存
                Log.d("TRIM_RUNNING_CRITICAL", TRIM_MEMORY_RUNNING_CRITICAL + "");
                break;
            case TRIM_MEMORY_RUNNING_LOW: //应用程序可见-内存低-10-位于LRU顶部-设备的可用内存越来越少
                Log.d("TRIM_MEMORY_RUNNING_LOW", TRIM_MEMORY_RUNNING_LOW + "");
                break;
            case TRIM_MEMORY_RUNNING_MODERATE://应用程序可见-内存较少-5-位于LRU顶部系统即将进入低内存状态
                Log.d("TRIM_RUNNING_MODERATE", TRIM_MEMORY_RUNNING_MODERATE + "");
                break;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void registSreenStatusReceiver() {

        mScreenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter screenStatusIF = new IntentFilter();
        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStatusReceiver, screenStatusIF);
    }
    class ScreenStatusReceiver extends BroadcastReceiver {

        final String SCREEN_ON = "android.intent.action.SCREEN_ON";
        final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";


        @Override
        public void onReceive(Context context, Intent intent) {

            if (SCREEN_ON.equals(intent.getAction())) {
                Applications.isLight = true;

            } else {
                Applications.isLight = false;
            }
            Log.d("light", String.valueOf(Applications.isLight));

        }
    }


}




