package com.threef.lifenotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends AppCompatActivity {
    String userId;
    String netWorkResult;
    Handler handler;
    JSONArray netResultList;
    ListView recordListView;
    Runnable queryTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_query);

        recordListView = (ListView)findViewById(R.id.record_list);

        FloatingActionButton createBtn = (FloatingActionButton)findViewById(R.id.create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCreate();
            }
        });

        //userId获得
        SharedPreferences userInfo = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userId = userInfo.getString("userid", null);

        //创建一个Handler对象
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (netWorkResult != null) {
//                    Toast.makeText(QueryActivity.this, netWorkResult, Toast.LENGTH_SHORT).show();
                    parseResult();
                }
                super.handleMessage(msg);
            }
        };
        queryTask = new Runnable() {
            @Override
            public void run() {
                URL url;
                String finalUrl = "http://182.92.222.196/sh/qryActivity.htm?userId="+userId;
                Log.d("Query",finalUrl);
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
    @Override
    protected void onResume(){
        super.onResume();
        if (userId == null) {
            Intent intent = new Intent();
            intent.setClass(QueryActivity.this, CollectInfoActivity.class);
            startActivity(intent);
        } else {
            new Thread(queryTask).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.query_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.collect_info) {
            Intent intent = new Intent();
            intent.setClass(QueryActivity.this, CollectInfoActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.epq_test) {
            Intent intent = new Intent();
            intent.putExtra("from","QuearyActivity");
            intent.setClass(QueryActivity.this, EPQActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void goCreate(){
        Intent intent=new Intent(this,CreateActivity.class);
        startActivity(intent);
    }


    private void parseResult() {
        try {
            Log.d("Query",netWorkResult);
            netResultList = new JSONArray(netWorkResult);
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
            recordListView.setAdapter(new RecordListAdapter(QueryActivity.this));
//            recordListView.invalidate();

        } catch (Exception ex) {
            // 异常处理代码
            ex.printStackTrace();
        }
    }

    public class RecordListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        public RecordListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return netResultList.length();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChildHolder holder;
            if (convertView == null) {
                convertView =  inflater.inflate(R.layout.epqresult_child,null);
                holder = new ChildHolder();
                holder.title = (AppCompatTextView)convertView.findViewById(R.id.time);
                holder.content = (AppCompatTextView)convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder)convertView.getTag();
            }
            try {

                JSONObject obj = (JSONObject)netResultList.get(position);
                String time = obj.getString("insertDate");
                String content = "";
                if (obj.getString("doing").equals("0")) {
                    time = time + " 工作";
                    content = content + "工作时长：" + obj.getString("duiring") + "小时" + "\n";
                    content = content + "心情：" + obj.getString("emotion") + "\n";
                    content = content + "心率：" + obj.getString("hartRate") + "次/分" ;

                } else if (obj.getString("doing").equals("1")) {
                    time = time + " 运动";
                    content = content + "运动时长：" + obj.getString("duiring") + "小时" + "\n";
                    content = content + "运动地点：" + obj.getString("moveAddr") + "\n";
                    content = content + "运动类型：" + obj.getString("movement") + "\n";
                    content = content + "心情：" + obj.getString("emotion") + "\n";
                    content = content + "心率：" + obj.getString("hartRate") + "次/分" ;

                } else if (obj.getString("doing").equals("2")) {
                    time = time + " 娱乐";
                    content = content + "娱乐时长：" + obj.getString("duiring") + "小时" + "\n";
//                    content = content + "娱乐地点：" + obj.getString("entertainment") + "\n";
                    content = content + "心情：" + obj.getString("emotion") + "\n";
                    content = content + "心率：" + obj.getString("hartRate") + "次/分";
                }
                holder.title.setText(time);
                holder.content.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String text = "";

//            for (int i = 0; i <strings.size() ; i ++) {
//                text = text + strings.get(i) + "\n";
//            }
//            holder.text.setText(text);
            return convertView;
        }
    }

//    public class RecordExpandableListAdapter extends BaseExpandableListAdapter {
//        private Context context;
//        private LayoutInflater inflater;
//        public RecordExpandableListAdapter(Context context) {
//            this.context = context;
//            inflater = LayoutInflater.from(context);
//        }
//        @Override
//        public int getGroupCount() {
//            return netResultList.size();
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return 1;
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return netResultList.get(groupPosition);
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            GroupHolder holder;
//            if (convertView == null) {
//                convertView =  inflater.inflate(R.layout.epqresult_group,null);
//                holder = new GroupHolder();
//                holder.title = (AppCompatTextView)convertView.findViewById(R.id.group);
//                convertView.setTag(holder);
//            } else {
//                holder = (GroupHolder)convertView.getTag();
//            }
//            holder.title.setText("");
//            return convertView;
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            ChildHolder holder;
//            if (convertView == null) {
//                convertView =  inflater.inflate(R.layout.epqresult_child,null);
//                holder = new ChildHolder();
//                holder.text = (AppCompatTextView)convertView.findViewById(R.id.child);
//                convertView.setTag(holder);
//            } else {
//                holder = (ChildHolder)convertView.getTag();
//            }
//            List<String> strings = netResultList.get(groupPosition);
//            String text = "";
//            for (int i = 0; i <strings.size() ; i ++) {
//                text = text + strings.get(i) + "\n";
//            }
//            holder.text.setText(text);
//            return convertView;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return false;
//        }
//    }
//
//    /**存放控件*/
//    public final class GroupHolder{
//        public AppCompatTextView title;
//    }
//
    public final class ChildHolder{
        public AppCompatTextView title;
        public AppCompatTextView content;
    }

}
