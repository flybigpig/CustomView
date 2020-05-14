package com.lessons.change.customview.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lessons.change.customview.R;
import com.lessons.change.customview.view.SurfacesView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // view has inited
    }

    private void initView() {
        SurfacesView surfacesView = findViewById(R.id.surfaceView);


    }

    private void testNet() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // ping www.baidu.com
////                Log.d("ping-aaavg", Ping("www.baidu.com"));
//
//                //download
////                String downloadUrl = "https://cmshqzst.com.prod.hosts.ooklaserver.net:8080/download?nocache=36c7afd5-6e6e-40cb-a313-ad648ad537c7&size=25000000&guid=2a233d19-ce58-45aa-ba0b-225cb191cd5e";
////                downloadTest(downloadUrl);
//
//                // upload
////                String uploadUrl = "https://cmshqzst.com.prod.hosts.ooklaserver.net:8080/upload?nocache=082daf3b-3e4c-4c88-9b41-02c70f132ac0&guid=2a233d19-ce58-45aa-ba0b-225cb191cd5e";
////                uploadTest(uploadUrl);
//            }
//        }).start();

//        String uploadUrl = "https://cmshqzst.com.prod.hosts.ooklaserver.net:8080/upload?nocache=082daf3b-3e4c-4c88-9b41-02c70f132ac0&guid=2a233d19-ce58-45aa-ba0b-225cb191cd5e";
//        final HttpUploadTest httpUploadTest = new HttpUploadTest(uploadUrl);
//        httpUploadTest.start();

//        String downloadUrl = "https://cmshqzst.com.prod.hosts.ooklaserver.net:8080/download?nocache=36c7afd5-6e6e-40cb-a313-ad648ad537c7&size=25000000&guid=2a233d19-ce58-45aa-ba0b-225cb191cd5e";
//        final HttpDownloadTest httpDownloadTest = new HttpDownloadTest(downloadUrl);
//        httpDownloadTest.start();

    }

    /**
     * @param uploadUrl Content-type: application/octet-stream，Content-Length:
     */
    private void uploadTest(String uploadUrl) {
        int uploadedKByte = 0;
        Double finalUploadRate;
        byte[] buffer = new byte[150 * 1024];
        long now;
        int timeout = 8;
        long startTime = System.currentTimeMillis();
        double uploadElapsedTime;
        while (true) {
            try {
                HttpsURLConnection conn = null;
                URL url = new URL(uploadUrl);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.connect();

                String body = "{account:" + UUID.randomUUID() + ",startTime:" + UUID.randomUUID() + "}";
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

                conn.getResponseCode();

                uploadedKByte += buffer.length / 1024.0;
                Log.d(" upload-size", String.valueOf(uploadedKByte));
                long endTime = System.currentTimeMillis();
                uploadElapsedTime = (endTime - startTime) / 1000.00;
                if (uploadElapsedTime >= timeout) {

                }
                conn.disconnect();

                now = System.currentTimeMillis();
                uploadElapsedTime = (now - startTime) / 1000.0;
                finalUploadRate = (Double) (((uploadedKByte / 1000.00) * 8.00) / uploadElapsedTime);
                Log.d("speed_test3 upload", String.valueOf(finalUploadRate));
                Log.d("upload-rate", String.valueOf(finalUploadRate));

            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }


    }

    public double downloadTest(String downloadUrl) {
        int responseCode = 0;
        HttpsURLConnection httpsConn = null;
        long startTime = 0;
        long endTime = 0;
        double downloadElapsedTime = 0;
        double instantDownloadRate;
        double finalDownloadRate;
        int downloadedByte = 0;
        try {

            URL url = new URL(downloadUrl);
            httpsConn = (HttpsURLConnection) url.openConnection();
            httpsConn.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            httpsConn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            startTime = System.currentTimeMillis();
            String body = "{nocache:" + UUID.randomUUID() + ",guid:" + UUID.randomUUID() + "}";
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8"));
            writer.write(body);
            writer.close();
            httpsConn.connect();
            responseCode = httpsConn.getResponseCode();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] buffer = new byte[10240];

                InputStream inputStream = httpsConn.getInputStream();
                int len = 0;

                while ((len = inputStream.read(buffer)) != -1) {
                    downloadedByte += len;
                    endTime = System.currentTimeMillis();
                    downloadElapsedTime = (endTime - startTime) / 1000.0;
                    instantDownloadRate = setInstantDownloadRate(downloadedByte, downloadElapsedTime);
                    Log.d("DownloadRate-now/Mbps:", String.valueOf(instantDownloadRate));
                    if (downloadElapsedTime >= 10) {
                        break;
                    }
                }

                inputStream.close();
                httpsConn.disconnect();
            } else {
                System.out.println("Link not found...");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        endTime = System.currentTimeMillis();
        downloadElapsedTime = (endTime - startTime) / 1000.0;
        finalDownloadRate = ((downloadedByte * 8.00) / (1000 * 1000.00)) / downloadElapsedTime;
        Log.d("DownloadRate-avg/Mbps:", String.valueOf(finalDownloadRate));
        Log.d("speed_test2 download", String.valueOf(finalDownloadRate));
        return finalDownloadRate;
    }

    public double setInstantDownloadRate(int downloadedByte, double elapsedTime) {
        if (downloadedByte >= 0) {
            return round((Double) (((downloadedByte * 8) / (1000 * 1000)) / elapsedTime), 2);
        } else {
            return 0.0;
        }
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd;
        try {
            bd = new BigDecimal(value);
        } catch (Exception ex) {
            return 0.0;
        }
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String Ping(String str) {
        String resault = "";
        Process p;
        Double avgRtt = null;
        try {
            //ping -c 10 -w 30  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 10 -w 30 " + str);
            int status = p.waitFor();
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
                Log.d("pings", line.toString());
                if (line.contains("icmp_seq")) {
                    Double instantRtt = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));
                    Log.d("instantRtt-pings", String.valueOf(instantRtt));
                }
                if (line.startsWith("rtt ")) {
                    avgRtt = Double.parseDouble(line.split("/")[4]);

                    Log.d("speed_test1", String.valueOf(avgRtt));
                    Log.d("avg-pings ping", String.valueOf(avgRtt));

                    break;
                }
                if (line.contains("Unreachable") || line.contains("Unknown") || line.contains("%100 packet loss")) {
                    return "";
                }
            }
            Log.d("Return ============", buffer.toString());
            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(avgRtt);
    }

}