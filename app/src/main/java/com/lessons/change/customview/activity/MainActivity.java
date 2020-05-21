package com.lessons.change.customview.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.lessons.change.customview.R;
import com.lessons.change.customview.view.ChartLineView;

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

        String data[] = {"aa", "bb", "cc", "dd", "aa", "bb", "cc", "dd", "aa", "bb", "cc", "dd", "aa", "bb", "cc", "dd"};//假数据
        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);
    }


}