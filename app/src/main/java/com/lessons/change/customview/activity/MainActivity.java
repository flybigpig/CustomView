package com.lessons.change.customview.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lessons.change.customview.R;
import com.lessons.change.customview.view.ChartLineView;
import com.lessons.change.customview.view.CustonCircle;

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


        ChartLineView chartView = findViewById(R.id.chartview);
//
//        String data[] = {"aa", "bb", "cc", "dd", "aa", "bb", "cc", "dd", "aa", "bb", "cc", "dd", "aa", "bb", "cc", "dd"};//假数据
//        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);//新建并配置ArrayAapeter
//        listView.setAdapter(adapter);
//
//
//        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {//（总时间，间隔时间）
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Toast.makeText(MainActivity.this, millisUntilFinished / 1000 + 1 + "秒后可退出！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFinish() {
//                Toast.makeText(MainActivity.this, "退出！", Toast.LENGTH_SHORT).show();
//            }
//        }.start();
//        countDownTimer.start();
    }


}