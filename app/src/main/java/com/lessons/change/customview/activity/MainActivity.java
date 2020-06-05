package com.lessons.change.customview.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.lessons.change.customview.R;
import com.lessons.change.customview.view.ChartLineView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义view 实战
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity.this";

    private String downloadUrl = "https://xact02.baidupcs.com/file/ddd6122b1oab4c33a48b0348e1e1b096?bkt=en-00f3aa810d089f2064e12e8db87129f6f7be86593ea310e72c242dce3f9b9dd2fee15a5c7e20ea55b12d7d21a5d73080a167b0ed6452cb806c4d994fd23cdd41&fid=3803172254-250528-92232504055665&time=1590833107&sign=FDTAXUGERLQlBHSKfW-DCb740ccc5511e5e8fedcff06b081203-yozPR2%2FBxbUtkaYzPG2TWufLbjM%3D&to=126&size=286860&sta_dx=286860&sta_cs=811&sta_ft=zip&sta_ct=1&sta_mt=1&fm2=MH%2CXian%2CAnywhere%2C%2Cbeijing%2Cct&ctime=1590594011&mtime=1590594020&resv0=-1&resv1=0&resv2=rlim&resv3=5&resv4=286860&vuk=2687346094&iv=0&htype=&randtype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=en-1af71acf35e775fcec42ae11aa1b30ca43ca5fee55d30872d6cfcc7edf9b0d89494f5bf4d08ac86f60b35a364efb3168a205b37a82bc7fe8305a5e1275657320&sl=68616270&expires=8h&rt=sh&r=654932839&vbdid=1065301567&fin=RealBlood+v3.1.1.zip&fn=RealBlood+v3.1.1.zip&rtype=1&dp-logid=3496400459955362134&dp-callid=0.1&hps=1&tsl=200&csl=200&fsl=-1&csign=SCIMH3n20padSlxmF0ayYGpOkzM%3D&so=0&ut=6&uter=4&serv=0&uc=1437159684&ti=34c17094e7d5a0c9b58924f26cf8c6ce7ea3c6c6b202cf15&hflag=30&adg=c_9b157bff8a7784b7fc1b79782cabcfb5&reqlabel=250528_f_d4e2f5386761f99383763f2408630029_-1_cbf5449594a4cb5372f2b70ffdd51fde&by=themis";
    private String path = "/ZhangTao/";
    private ChartLineView mChartView;
    private int REQUEST_LOCATION = 1;
    private Timer mTimer;
    private TimerTask mMTimerTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    requestLocationPermission();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {


        mChartView = findViewById(R.id.chartview);
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

        mTimer = new Timer();
        mMTimerTask = new TimerTask() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
                Log.d("taskkkkk", "taks start" + System.currentTimeMillis());

            }
        };

        mTimer.schedule(mMTimerTask, 0, 2000);
//        mTimer.schedule(mMTimerTask, 0);
    }

    public void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            //判断是否具有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(getApplicationContext(), "自Android 6.0开始需要打开位置权限才可以搜索到WIFI设备", Toast.LENGTH_SHORT);

            }

            //请求权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 1:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        List<ScanResult> listb = wifiManager.getScanResults();
                        Log.d("xposition", listb.size() + "");
                        mChartView.SetInfo(listb);
                        mChartView.invalidate();

                    } else {
                        Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_SHORT);
                    }
                }
                break;
            default:
                break;
        }
    }


}