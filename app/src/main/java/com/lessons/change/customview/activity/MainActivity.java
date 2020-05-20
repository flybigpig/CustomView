package com.lessons.change.customview.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lessons.change.customview.R;
import com.lessons.change.customview.view.ChartView;

/**
 * 自定义view 实战
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity.this";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ChartView chartView = findViewById(R.id.chartview);


    }

}