package com.lessons.change.customview.fbid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author
 * @date 2020/4/15.
 * GitHub：
 * email：
 * description：
 */
public class InstallReferrerReceiver extends BroadcastReceiver {

    private final int OK = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        if (OK == 0) {

        }

    }
}
