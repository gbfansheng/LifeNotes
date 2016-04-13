package com.threef.lifenotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EverydayActivity extends AppCompatActivity {
    Handler handler;
    Runnable everyDayTask;
    String netWorkResult;
    AppCompatTextView summary;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        summary = (AppCompatTextView) findViewById(R.id.summary);

        SharedPreferences userInfo = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userId = userInfo.getString("userid", null);
//创建一个Handler对象
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (netWorkResult != null) {
//                    Toast.makeText(EverydayActivity.this, netWorkResult, Toast.LENGTH_SHORT).show();
                    parseResult();
                }
                super.handleMessage(msg);
            }
        };

        everyDayTask = new Runnable() {
            @Override
            public void run() {
                URL url;
                String finalUrl = "http://182.92.222.196/sh/qryTodaySum.htm?userId="+userId;
                Log.d("Query", finalUrl);
                netWorkResult = "";
                try {
                    url = new URL(finalUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url
                            .openConnection();  //创建一个HTTP连接
                    InputStreamReader in = new InputStreamReader(
                            urlConn.getInputStream()); // 获得读取的内容
                    BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                    String inputLine = null;
                    //通过循环逐行读取输入流中的内容
                    while ((inputLine = buffer.readLine()) != null) {
                        netWorkResult += inputLine + "\n";
                    }
                    in.close(); //关闭字符输入流对象
                    urlConn.disconnect();   //断开连接
                } catch (MalformedURLException e) {
                    Log.d("Query", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("Query", e.toString());
                    e.printStackTrace();
                }
                Log.d("Query",netWorkResult);
                Message m = handler.obtainMessage();
                handler.sendMessage(m); // 发送消息
            }
        };
    }

    private void parseResult() {
        try {
//            Log.d("Query",netWorkResult);
//            netResultList = new JSONArray(netWorkResult);
//            for(int i=0;i<records.length();i++) {
//                JSONObject tempJson = records.optJSONObject(i);
////                List<String> strings = new ArrayList<>();
////                strings.add(tempJson.getString("movement"));
////                strings.add(tempJson.getString("entertainment"));
////                strings.add(tempJson.getString("doing"));
////                strings.add(tempJson.getString("duiring"));
////                strings.add(tempJson.getString("emotion"));
////                strings.add(tempJson.getString("working"));
////                strings.add(tempJson.getString("hart_Rate"));
////                strings.add(tempJson.getString("insert_time"));
////                strings.add(tempJson.getString("coordinate"));
////                strings.add(tempJson.getString("moveAddr"));
////                strings.add(tempJson.getString("enterAddr"));
//                netResultList.add(tempJson);
//            }
//            recordListView.setAdapter(new RecordListAdapter(QueryActivity.this));
//            recordListView.invalidate();
            summary.setText(netWorkResult);
        } catch (Exception ex) {
            // 异常处理代码
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        new Thread(everyDayTask).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            Intent intent = new Intent();
            intent.putExtra("from", "EverydayActivity");
            intent.setClass(EverydayActivity.this, QueryActivity.class);
            startActivity(intent);
        }

        return false;

    }
}
