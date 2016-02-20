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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class QueryActivity extends AppCompatActivity {
    String userId;
    String netWorkResult;
    Handler handler;
    List<List<String>> netResultList;
    ExpandableListView recordListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        recordListView = (ExpandableListView)findViewById(R.id.record_list);

        //userId获得
        SharedPreferences userInfo = getSharedPreferences("userinfo", 0);
        userId = userInfo.getString("userid", null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                String finalUrl = "http://182.92.222.196/sh/qryActivity.htm?userId="+userId;
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
        }).start();
        //创建一个Handler对象
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (netWorkResult != null) {
                    Toast.makeText(QueryActivity.this, netWorkResult, Toast.LENGTH_SHORT).show();
                    parseResult();
                    recordListView.setAdapter(new RecordExpandableListAdapter(QueryActivity.this));
                }
                super.handleMessage(msg);
            }
        };

    }

    private void parseResult() {
        try {
            Log.d("Query",netWorkResult);
            netResultList = new ArrayList<>();
            JSONArray records = new JSONArray(netWorkResult);
            for(int i=0;i<records.length();i++) {
                JSONObject tempJson = records.optJSONObject(i);
                List<String> strings = new ArrayList<>();
                strings.add(tempJson.getString("movement"));
                strings.add(tempJson.getString("entertainment"));
                strings.add(tempJson.getString("doing"));
                strings.add(tempJson.getString("duiring"));
                strings.add(tempJson.getString("emotion"));
                strings.add(tempJson.getString("working"));
                strings.add(tempJson.getString("hartRate"));
                strings.add(tempJson.getString("insertDate"));
                strings.add(tempJson.getString("coordinate"));
                strings.add(tempJson.getString("moveAddr"));
                strings.add(tempJson.getString("enterAddr"));
                netResultList.add(strings);
            }
        } catch (Exception ex) {
            // 异常处理代码
            ex.printStackTrace();
        }
    }

    public class RecordExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater inflater;
        public RecordExpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public int getGroupCount() {
            return netResultList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return netResultList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            if (convertView == null) {
                convertView =  inflater.inflate(R.layout.epqresult_group,null);
                holder = new GroupHolder();
                holder.title = (AppCompatTextView)convertView.findViewById(R.id.group);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder)convertView.getTag();
            }
            holder.title.setText("");
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            if (convertView == null) {
                convertView =  inflater.inflate(R.layout.epqresult_child,null);
                holder = new ChildHolder();
                holder.text = (AppCompatTextView)convertView.findViewById(R.id.child);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder)convertView.getTag();
            }
            List<String> strings = netResultList.get(groupPosition);
            String text = "";
            for (int i = 0; i <strings.size() ; i ++) {
                text = text + strings.get(i) + "\n";
            }
            holder.text.setText(text);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    /**存放控件*/
    public final class GroupHolder{
        public AppCompatTextView title;
    }

    public final class ChildHolder{
        public AppCompatTextView text;
    }

}
