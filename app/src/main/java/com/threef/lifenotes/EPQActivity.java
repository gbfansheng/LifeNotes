package com.threef.lifenotes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class EPQActivity extends AppCompatActivity {
    List<String> questionList;//EPQ问题的列表
    List<Boolean> answerList;//EPQ回答列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListViewCompat listViewCompat = (ListViewCompat) findViewById(R.id.listCompat);
        listViewCompat.setAdapter(new EPQListAdapter(this));
        //装载数据？
        questionList = new ArrayList<>();//EPQ问题的列表
        questionList.add("问题1");
        answerList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public class EPQListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        public EPQListAdapter(Context context) {
            super();
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return questionList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.epq_list_item,null);
                holder = new ViewHolder();
                holder.questionTextView = (AppCompatTextView) convertView.findViewById(R.id.question);
                holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.option_group);
                holder.radioGroup.setTag(position);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.questionTextView.setText(questionList.get(position));
            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    answerList.set((int)group.getTag(),(checkedId == R.id.yes));
                }
            });
            return convertView;
        }

        /**存放控件*/
        public final class ViewHolder{
            public AppCompatTextView questionTextView;
            public RadioGroup radioGroup;
        }
    }


}
