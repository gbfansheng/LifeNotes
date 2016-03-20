package com.threef.lifenotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectInfoActivity extends AppCompatActivity {
    EditText name;
    EditText age;
    EditText workage;
    EditText job;
    EditText carrerTitle;
    RadioGroup gender;
    RadioGroup marry;
    RadioGroup smoke;
    RadioGroup drink;
    RadioGroup diet;
    RadioGroup fit;
    EditText smokeyear;
    EditText drinkyear;
    EditText dietyear;
    EditText fityear;
    EditText heartRate;
    EditText bmi;
    EditText workTime;
    EditText pIncome;
    EditText fIncome;
    Spinner sleep;
    Spinner pressure;
//    Spinner living;
    String sleepStatus;
    String pressureStatus;
//    String livingStatus;
    String userId;
    String sex;
    String married;
    Handler handler; // 声明一个Handler对象
    String result = ""; //声明一个代表显示内容的字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_info);

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        workage = (EditText) findViewById(R.id.workage);
        job = (EditText) findViewById(R.id.job);
        carrerTitle = (EditText) findViewById(R.id.carrertitle);
        gender = (RadioGroup) findViewById(R.id.gender);
        marry = (RadioGroup) findViewById(R.id.marry);
        smoke = (RadioGroup) findViewById(R.id.smoke);
        drink = (RadioGroup) findViewById(R.id.drink);
        diet = (RadioGroup) findViewById(R.id.diet);
        fit = (RadioGroup) findViewById(R.id.fit);
        smokeyear = (EditText) findViewById(R.id.smokeyear);
        drinkyear = (EditText) findViewById(R.id.drinkyear);
        dietyear = (EditText) findViewById(R.id.dietyear);
        fityear = (EditText) findViewById(R.id.fityear);
        heartRate = (EditText) findViewById(R.id.heartrate);
//        bmi = (EditText) findViewById(R.id.bmi);
        workTime = (EditText) findViewById(R.id.worktime);
        pIncome = (EditText) findViewById(R.id.pincome);
        fIncome = (EditText) findViewById(R.id.fincome);
        sleep = (Spinner) findViewById(R.id.sleep);
        pressure = (Spinner) findViewById(R.id.pressure);
//        living = (Spinner) findViewById(R.id.living);


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

//        living.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String[] temp = getResources().getStringArray(R.array.livingarray);
//                livingStatus = temp[position];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        String name="xixi";
//        editor.putString("name", name);
//        editor.commit();
//
//        String name2=preferences.getString("name", "defaultname");
//        Log.i("============", name2);

        //userId获得
        SharedPreferences userInfo = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userId = userInfo.getString("userid", null);
        if (userId == null) {
            Date date = new Date();
            SimpleDateFormat df=new SimpleDateFormat("ddhhmmss");
            userId = df.format(date);
            SharedPreferences.Editor editor=userInfo.edit();
            editor.putString("userid", userId);
            editor.commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用网络
                sex = gender.getCheckedRadioButtonId() == R.id.male ? "1" : "0";
                married = marry.getCheckedRadioButtonId() == R.id.marry ? "1" : "0";
                String target = "";
//                try {
//                    target = "http://182.92.222.196:80/sh/register.htm?userId=" + userId + "&age=" + age.getText().toString()
//                            + "&sex=" + sex + "&isMerry=" + married + "&workAge=" + workage.getText().toString()
//                            + "&job=" + URLEncoder.encode(job.getText().toString(), "utf-8") + "&jobName=" + URLEncoder.encode(carrerTitle.getText().toString(), "utf-8") + "&isSmoke=" +
//                            (smoke.isChecked() ? "1" : "0") + "&isDrink=" + (drink.isChecked() ? "1" : "0") + "&isDiet=" + (diet.isChecked() ? "1" : "0") + "&isFitness=" +
//                            (fit.isChecked() ? "1" : "0") + "&avgHertRat=" + heartRate.getText().toString() + "&BMI=" + bmi.getText().toString()
//                            + "&sleep=" + URLEncoder.encode(sleepStatus, "utf-8") + "&avgWork=" + workTime.getText().toString() + "&pressure=" + URLEncoder.encode(pressureStatus, "utf-8")
//                            + "&psnIncome=" + pIncome.getText().toString() + "&famImcome=" + fIncome.getText().toString() +
//                            "&living=" + URLEncoder.encode(livingStatus, "utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                Toast.makeText(CollectInfoActivity.this,result,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("url",target);
                intent.putExtra("from","CollectionInfoActivity");
                Log.d("Collection","perUrl:"+target);
                intent.setClass(CollectInfoActivity.this, EPQActivity.class);
                startActivity(intent);
           }
        });
    }
}