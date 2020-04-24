package com.lessons.change.customview.fbid.util;

import android.content.Context;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryConfig;
import com.flurry.android.FlurryConfigListener;

/**
 * @author
 * @date 2020/4/15.
 * GitHub：
 * email：
 * description：
 */
public class FlurryUtils {

    private static FlurryUtils flurry = new FlurryUtils();
    private static FlurryAgent.Builder builder;
    private static FlurryConfig config;


    public static FlurryUtils getInstance() {
        if (builder == null) {
            synchronized (FlurryUtils.class) {
                if (builder == null) {
                    builder = new FlurryAgent.Builder();
                }
            }
        }
        return flurry;
    }

    public static void init(Context context, String s) {
        builder.withLogLevel(Log.DEBUG).withLogEnabled(true).build(context, s);
        loadFluury();
    }

    private static void loadFluury() {
        config = FlurryConfig.getInstance();
        config.registerListener(new FlurryConfigListener() {
            @Override
            public void onFetchSuccess() {
                Log.d("flurryyyyyyyyyyy", "ok");
            }

            @Override
            public void onFetchNoChange() {
                Log.d("flurryyyyyyyyyyy", "onFetchNoChange");
            }

            @Override
            public void onFetchError(boolean b) {
                Log.d("flurryyyyyyyyyyy", "error");
            }

            @Override
            public void onActivateComplete(boolean b) {
                Log.d("flurryyyyyyyyyyy", "onActivateComplete");
            }
        });
        config.fetchConfig();
    }


//    public static void main(String[] args) {
//        new FlurryAgent.Builder()
//                .withLogEnabled(true)
//                .build(, "BGC4HW3F855NQ6MZZQD8");
//    }

}
