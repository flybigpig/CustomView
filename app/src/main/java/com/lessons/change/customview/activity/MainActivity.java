package com.lessons.change.customview.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lessons.change.customview.R;
import com.lessons.change.customview.advanceview.RainWidget;
import com.lessons.change.customview.view.GravityView;
import com.lessons.change.customview.view.RegionView;
import com.lessons.change.customview.view.SurfacesView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义view 实战
 */
public class MainActivity extends AppCompatActivity {

    private long cacheSize;
    private PackageManager mPM;
    private String TAG = "MainActivity.this";
    private RegionView regionView;
    private TextView mMyView;

    private TextView ava;
    private TextView total;
    private RainWidget rainwedget;
    private GravityView mGravityView;
    private Timer mTimer1;
    private TimerTask mTimerTask1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {

        SurfacesView surfacesView = findViewById(R.id.surfaceView);


    }


}
