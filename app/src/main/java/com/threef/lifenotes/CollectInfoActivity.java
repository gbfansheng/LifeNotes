package com.threef.lifenotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
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
    AppCompatTextView sum;
    ScrollView mainScrollView;
    LinearLayout inputLayout;
    LinearLayout sumLayout;
    AppCompatButton submitBtn;
    AppCompatButton cancelBtn;
    EditText height;
    EditText weight;
    EditText qu;
    EditText jiedao;
    EditText lu;
    Float bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_info);

        mainScrollView = (ScrollView) findViewById(R.id.mainscrollview);
        sumLayout = (LinearLayout) findViewById(R.id.sumlayout);
        sumLayout.setVisibility(View.INVISIBLE);
        sum = (AppCompatTextView) findViewById(R.id.sum);
        submitBtn = (AppCompatButton) findViewById(R.id.submit);
        cancelBtn = (AppCompatButton) findViewById(R.id.cancel);
        inputLayout = (LinearLayout) findViewById(R.id.inputlayout);

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
//        heartRate = (EditText) findViewById(R.id.heartrate);
//        bmi = (EditText) findViewById(R.id.bmi);
        workTime = (EditText) findViewById(R.id.worktime);
        pIncome = (EditText) findViewById(R.id.pincome);
        fIncome = (EditText) findViewById(R.id.fincome);
        sleep = (Spinner) findViewById(R.id.sleep);
        pressure = (Spinner) findViewById(R.id.pressure);
//        living = (Spinner) findViewById(R.id.living);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        qu = (EditText) findViewById(R.id.qu);
        jiedao = (EditText) findViewById(R.id.jiedao);
        lu = (EditText) findViewById(R.id.lu);



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
            SimpleDateFormat df = new SimpleDateFormat("ddhhmmss");
            userId = df.format(date);
            SharedPreferences.Editor editor=userInfo.edit();
            editor.putString("userid", userId);
            editor.commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainScrollView.scrollTo(0, 0);
                sumLayout.setVisibility(View.VISIBLE);
                inputLayout.setVisibility(View.INVISIBLE);
                Float fheight = (height.getText().toString().equals(null) ? 0:Float.parseFloat(height.getText().toString()))/100;
                Float fweight = weight.getText().toString().equals(null) ? 0:Float.parseFloat(weight.getText().toString());
                bmi = fweight / (fheight * fheight);
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String bmiString=fnum.format(bmi);

                DecimalFormat decimalFormat = new DecimalFormat(".00");
                String sumtext = "昵称："+name.getText()+"\n"+"年龄："+age.getText()+"\n"+"工作年限："+workage.getText()+"\n"
                        +"岗位："+job.getText()+"\n"+"职称："+carrerTitle.getText()+"\n"+"性别："+(gender.getCheckedRadioButtonId() == R.id.male ? "男" : "女") + "\n"
                        +(marry.getCheckedRadioButtonId() == R.id.ismarry? "已婚":"未婚")+"\n"
                        +(smoke.getCheckedRadioButtonId() == R.id.smokeyes? "吸烟 "+smokeyear.getText()+" 年":"不吸烟")+ "\n"
                        +(drink.getCheckedRadioButtonId() == R.id.drinkyes? "喝酒 "+drinkyear.getText()+" 年":"不喝酒")+ "\n"
                        +(diet.getCheckedRadioButtonId() == R.id.dietyes? "节食 "+dietyear.getText()+" 年":"不节食")+"\n"
                        +(fit.getCheckedRadioButtonId() == R.id.fityes? "健身 "+fityear.getText() + " 年":(fit.getCheckedRadioButtonId() == R.id.fittemp ? "偶尔健身":"不健身"))+"\n"
                        //+"平均心率："+heartRate.getText()+"\n"
                        +"平均工作时间："+workTime.getText()+"\n"
                        +"个人平均月收入："+pIncome.getText()+"\n"
                        +"家庭月平均收入："+fIncome.getText()+"\n"
                        +"身高："+ height.getText()+"cm\n"
                        +"体重："+ weight.getText()+"kg\n"
                        +"BMI："+ bmiString +"\n"
                        +"居住地区："+qu.getText()+"区 " +jiedao.getText()+"街道 "+lu.getText()+"路 \n"
                        +"睡眠状况："+sleepStatus +"\n"
                        +"生活压力："+pressureStatus;
                sum.setText(sumtext);

           }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String bmiString=fnum.format(bmi);
                //调用网络
                sex = gender.getCheckedRadioButtonId() == R.id.male ? "1" : "0";
                married = marry.getCheckedRadioButtonId() == R.id.marry ? "1" : "0";
                DecimalFormat decimalFormat = new DecimalFormat(".00");
                String live = "居住地区："+qu.getText()+"区 " +jiedao.getText()+"街道 "+lu.getText()+"路";
                String target = "";
                try {
                    target = "http://182.92.222.196:80/sh/register.htm?userId=" + userId + "&age=" + age.getText().toString()
                            + "&sex=" + sex + "&isMerry=" + married + "&workAge=" + workage.getText().toString()
                            + "&job=" + URLEncoder.encode(job.getText().toString(), "utf-8") + "&jobName=" + URLEncoder.encode(carrerTitle.getText().toString(), "utf-8")
                            + "&isSmoke="  + (smoke.getCheckedRadioButtonId() == R.id.smokeyes ? "1" : "0") + "&smokeAge=" + (smoke.getCheckedRadioButtonId() == R.id.smokeyes ? smokeyear.getText():"0")
                            + "&isDrink="+ (drink.getCheckedRadioButtonId() == R.id.drinkyes ? "1" : "0") + "&drinkAge=" + (drink.getCheckedRadioButtonId() == R.id.drinkyes ? drinkyear.getText():"0")
                            + "&isDiet="+ (diet.getCheckedRadioButtonId() == R.id.dietyes ? "1" : "0") + "&dietAge=" + (diet.getCheckedRadioButtonId() == R.id.dietyes ? dietyear.getText():"0")
                            + "&isFitness="+ (fit.getCheckedRadioButtonId() == R.id.fityes ? "1" : (fit.getCheckedRadioButtonId() == R.id.fittemp ? "2":"0")) + "&fitness=" + (fit.getCheckedRadioButtonId() == R.id.fityes ? fityear.getText(): "0")
                            /*+ "&avgHertRat=" + heartRate.getText().toString()*/ + "&BMI=" + bmiString + "&heigh=" + height.getText().toString() + "&weight=" + weight.getText().toString()
                            + "&sleep=" + URLEncoder.encode(sleepStatus, "utf-8") + "&avgWork=" + workTime.getText().toString() + "&pressure=" + URLEncoder.encode(pressureStatus, "utf-8")
                            + "&psnIncome=" + pIncome.getText().toString() + "&famImcome=" + fIncome.getText().toString() +
                            "&living=" + URLEncoder.encode(live, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(CollectInfoActivity.this,result,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("url",target);
                intent.putExtra("from","CollectionInfoActivity");
                Log.d("Collection","perUrl:"+target);
                intent.setClass(CollectInfoActivity.this, EPQActivity.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumLayout.setVisibility(View.INVISIBLE);
                inputLayout.setVisibility(View.VISIBLE);
                mainScrollView.scrollTo(0,0);
            }
        });
    }
}