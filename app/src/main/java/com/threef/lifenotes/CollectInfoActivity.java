package com.threef.lifenotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CollectInfoActivity extends AppCompatActivity {
    EditText age;
    EditText workage;
    EditText job;
    EditText carrerTitle;
    RadioGroup gender;
    RadioGroup marry;
    CheckBox smoke;
    CheckBox drink;
    CheckBox diet;
    CheckBox fit;
    EditText heartRate;
    EditText bmi;
    EditText workTime;
    EditText pIncome;
    EditText fIncome;
    Spinner sleep;
    Spinner pressure;
    Spinner living;
    String sleepStatus;
    String pressureStatus;
    String livingStatus;
    String userId;
    String sex;
    String married;
    Handler handler; // 声明一个Handler对象
    String result = ""; //声明一个代表显示内容的字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_info);
        age = (EditText) findViewById(R.id.age);
        workage = (EditText) findViewById(R.id.workage);
        job = (EditText) findViewById(R.id.job);
        carrerTitle = (EditText) findViewById(R.id.carrertitle);
        gender = (RadioGroup) findViewById(R.id.gender);
        marry = (RadioGroup) findViewById(R.id.marry);
        smoke = (CheckBox) findViewById(R.id.smoke);
        drink = (CheckBox) findViewById(R.id.drink);
        diet = (CheckBox) findViewById(R.id.diet);
        fit = (CheckBox) findViewById(R.id.fit);
        heartRate = (EditText) findViewById(R.id.heartrate);
        bmi = (EditText) findViewById(R.id.bmi);
        workTime = (EditText) findViewById(R.id.worktime);
        pIncome = (EditText) findViewById(R.id.pincome);
        fIncome = (EditText) findViewById(R.id.fincome);
        sleep = (Spinner) findViewById(R.id.sleep);
        pressure = (Spinner) findViewById(R.id.pressure);
        living = (Spinner) findViewById(R.id.living);

        sleep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] temp = getResources().getStringArray(R.array.sleeparray);
                sleepStatus = temp[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pressure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] temp = getResources().getStringArray(R.array.pressurearray);
                pressureStatus = temp[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        living.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] temp = getResources().getStringArray(R.array.livingarray);
                livingStatus = temp[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //userId获得
        SharedPreferences userInfo = getSharedPreferences("userinfo", 0);
        userId = userInfo.getString("userid", null);
        if (userId == null) {
            Date date = new Date();
            SimpleDateFormat df=new SimpleDateFormat("ddhhmmss");
            userId = df.format(date);
            userInfo.edit().putString("userid",userId);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用网络
                sex = gender.getCheckedRadioButtonId() == R.id.male ? "1" : "0";
                married = marry.getCheckedRadioButtonId() == R.id.marry ? "1" : "0";
                String target = "";
                try {
                    target = "http://182.92.222.196:80/sh/register.htm?userId=" + userId + "&age=" + age.getText().toString()
                            + "&sex=" + sex + "&isMerry=" + married + "&workAge=" + workage.getText().toString()
                            + "&job=" + URLEncoder.encode(job.getText().toString(), "utf-8") + "&jobName=" + URLEncoder.encode(carrerTitle.getText().toString(), "utf-8") + "&isSmoke=" +
                            (smoke.isChecked() ? "1" : "0") + "&isDrink=" + (drink.isChecked() ? "1" : "0") + "&isDiet=" + (diet.isChecked() ? "1" : "0") + "&isFitness=" +
                            (fit.isChecked() ? "1" : "0") + "&avgHertRat=" + heartRate.getText().toString() + "&BMI=" + bmi.getText().toString()
                            + "&sleep=" + URLEncoder.encode(sleepStatus, "utf-8") + "&avgWork=" + workTime.getText().toString() + "&pressure=" + URLEncoder.encode(pressureStatus, "utf-8")
                            + "&psnIncome=" + pIncome.getText().toString() + "&famImcome=" + fIncome.getText().toString() +
                            "&living=" + URLEncoder.encode(livingStatus, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(CollectInfoActivity.this,result,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("url",target);
                Log.d("Collection","perUrl:"+target);
                intent.setClass(CollectInfoActivity.this, EPQActivity.class);
                startActivity(intent);
           }
        });
    }
}