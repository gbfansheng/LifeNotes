package com.threef.lifenotes;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class EditActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle buddle = getIntent().getExtras();
        final int id = buddle.getInt("id");
        int layoutId = 0;
        String title = new String();
        switch (id){
            case R.id.button1:
                layoutId = R.layout.activity_edit_work;
                title = getString(R.string.totay_work);
                break;
            case R.id.button2:
                layoutId = R.layout.activity_edit_exercise;
                title = getString(R.string.totay_exercise);
                break;
            case R.id.button3:
                layoutId = R.layout.activity_edit_entertainment;
                title = getString(R.string.totay_entertainment);
                break;
        }

        setContentView(layoutId);
        setTitle(title);

        Button confirmBtn = (Button) findViewById(R.id.edit_confirm_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    confirm_edit(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        SingleShotLocationProvider.requestSingleUpdate(this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.i("Location", "my location is " + location.toString());
                    }
                });


        Button picker1 = (Button) findViewById(R.id.beginButton);
        Button picker2 = (Button) findViewById(R.id.endButton);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpd_init((Button) view);
            }
        };
        picker1.setOnClickListener(listener);
        picker2.setOnClickListener(listener);
    }

    private TimePickerDialog tpd=null;
    private Date beginDate = null;
    private Date endDate = null;

    void tpd_init(final Button btn){
        TimePickerDialog.OnTimeSetListener otsl=new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tpd.dismiss();

                Date date = new Date();
                date.setMinutes(minute);
                date.setHours(hourOfDay);
                SimpleDateFormat fmt=new SimpleDateFormat("HH:mm");
                String beginStr = fmt.format(date);
                btn.setText(beginStr);

                if (btn.getId() == R.id.beginButton){
                    beginDate = date;
                }else{
                    endDate = date;
                }
            }
        };
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
        int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        tpd=new TimePickerDialog(this,otsl,hourOfDay,minute,true);
        tpd.show();
    }


    void confirm_edit(int id) throws Exception{
        String baseURL = "http://182.92.222.196/sh/recordActivity.htm?userId=";


        SharedPreferences userInfo = getSharedPreferences("userinfo", 0);
        String userId = userInfo.getString("userid", "defaultname");

        baseURL += userId;

//        EditText calorieText = (EditText) findViewById(R.id.edit_text_calorie);
//
//        if (calorieText != null){
//            baseURL += "&calorie=";
//            baseURL += calorieText.getText();
//        }
//        EditText timeText = (EditText) findViewById(R.id.edit_text_time);
//        baseURL += "&duiring=";
//        baseURL += timeText.getText();
//        if (timeText.getText() == null){
//            showAlert(getString(R.string.alert_title),getString(R.string.alert_time));
//            return;
//        }

        if (beginDate == null)
        {
            showAlert(getString(R.string.alert_title),getString(R.string.alert_time_begin));
            return;
        }
        if (endDate == null)
        {
            showAlert(getString(R.string.alert_title),getString(R.string.alert_time_end));
            return;
        }
        if (beginDate.getHours() > endDate.getHours())
        {
            showAlert(getString(R.string.alert_title),getString(R.string.alert_time_error));
            return;
        }
        SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMddHHmmss");
        String beginStr = fmt.format(beginDate);
        String endStr = fmt.format(endDate);
        baseURL += "&startTime=";
        baseURL += URLEncoder.encode(beginStr, "utf-8");
        baseURL += "&endTime=";
        baseURL += URLEncoder.encode(endStr, "utf-8");

//        EditText heartBeatText = (EditText) findViewById(R.id.edit_text_heartbeat);
//        baseURL += "&hartRate=";
//        baseURL += heartBeatText.getText();
//        if (heartBeatText.getText() == null){
//            showAlert(getString(R.string.alert_title),getString(R.string.alert_heartbeat));
//            return;
//        }

        ToggleButtonGroupTableLayout entertainment_location = (ToggleButtonGroupTableLayout) findViewById(R.id.entertainment_location_group);
        if (entertainment_location != null){
            baseURL += "&enterAddr=";
            int checkedId = entertainment_location.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_entertainment_location));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += URLEncoder.encode(checkedBtn.getText().toString(), "utf-8");
        }

        ToggleButtonGroupTableLayout entertainment_type = (ToggleButtonGroupTableLayout) findViewById(R.id.entertainment_type_group);
        if (entertainment_type != null){
            baseURL += "&entertaiment=";
            int checkedId = entertainment_type.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_entertainment_type));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += URLEncoder.encode(checkedBtn.getText().toString(), "utf-8");
        }



        ToggleButtonGroupTableLayout exercise_location = (ToggleButtonGroupTableLayout) findViewById(R.id.exercise_location_group);
        if (exercise_location != null){
            baseURL += "&moveAddr=";
            int checkedId = exercise_location.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_exercise_location));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += URLEncoder.encode(checkedBtn.getText().toString(), "utf-8");
        }

        ToggleButtonGroupTableLayout exercise_type = (ToggleButtonGroupTableLayout) findViewById(R.id.exercise_type_group);
        if (exercise_type != null){
            baseURL += "&movement=";
            int checkedId = exercise_type.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_exercise_type));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += URLEncoder.encode(checkedBtn.getContentDescription().toString(), "utf-8");
        }

//        RadioGroup work_tension = (RadioGroup) findViewById(R.id.work_tension);
//        if (work_tension != null){
//            baseURL += "&work_tension=";
//            int checkedId = work_tension.getCheckedRadioButtonId();
//            if (checkedId < 0){
//                showAlert(getString(R.string.alert_title),getString(R.string.alert_work_tension));
//                return;
//            }
//            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
//            baseURL += URLEncoder.encode(checkedBtn.getText().toString(), "utf-8");
//        }

//        ToggleButtonGroupTableLayout mood = (ToggleButtonGroupTableLayout) findViewById(R.id.mood_group);
//        if (mood != null){
//            baseURL += "&emotion=";
//            int checkedId = mood.getCheckedRadioButtonId();
//            if (checkedId < 0){
//                showAlert(getString(R.string.alert_title),getString(R.string.alert_mood));
//                return;
//            }
//            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
//            baseURL += URLEncoder.encode(checkedBtn.getText().toString(), "utf-8");
//        }

        baseURL += "&emotion=";
        try{
            baseURL += URLEncoder.encode(getMoods(), "utf-8");
        }catch (Exception e){
            showAlert(getString(R.string.alert_title),getString(R.string.alert_mood));
            return;
        }

        Log.i("dddddddddd", baseURL);
        String type = "";
        switch (id){
            case R.id.button1:
                type="0";
                break;
            case R.id.button2:
                type="1";
                break;
            case R.id.button3:
                type="2";
                break;
        }
        baseURL += "&doing=";
        baseURL += type;

        final String finalBaseURL = baseURL;
        new Thread(){
            @Override
            public void run()
            {
                requestURL(finalBaseURL);
            }
        }.start();
    }

    String getMoods() throws Exception{
//        List moods = new ArrayList();
        String moods = new String();
        int[] ids = new int[]{R.id.checkBox0,R.id.checkBox1,R.id.checkBox2,R.id.checkBox3,R.id.checkBox4,R.id.checkBox5,R.id.checkBox6,R.id.checkBox7,R.id.checkBox8,R.id.checkBox9,R.id.checkBox10,R.id.checkBox11,R.id.checkBox12,R.id.checkBox13,R.id.checkBox14};
        int number = 0;
        for(int i = 0;i < 15;i ++){
            int id = ids[i];
            CheckBox checkBox = (CheckBox) findViewById(id);
            if (checkBox.isChecked()){
                moods += checkBox.getText();
                moods += ",";
                number += 1;
            }
        }

        if (moods.length() == 0 || number > 3){
            throw new Exception();
        }
//        return  (String[])moods.toArray();
        moods = moods.substring(0,moods.length()-1);
        return moods;
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    void showAlert(String title,String text){
        Log.i("ERROR", text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //    设置Title的图标
        builder.setTitle(title);
        //    设置Content来显示一个信息
        builder.setMessage(text);
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", null);

        builder.show();
    }

    void showToast(final String text) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    void requestURL(String urlStr) {
        /*建立HTTP Get对象*/
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("Request", urlStr);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            showToast(getString(R.string.network_error));
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);
            Log.i("Back Response", response);
            showToast(getString(R.string.operation_success));
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            showToast(getString(R.string.server_error));
        } finally {
            urlConnection.disconnect();
        }
    }

    private String readStream(InputStream iStream) throws IOException {
        //build a Stream Reader, it can read char by char
        InputStreamReader iStreamReader = new InputStreamReader(iStream);
        //build a buffered Reader, so that i can read whole line at once
        BufferedReader bReader = new BufferedReader(iStreamReader);
        String line = null;
        StringBuilder builder = new StringBuilder();
        while((line = bReader.readLine()) != null) {  //Read till end
            builder.append(line);
        }
        bReader.close();         //close all opened stuff
        iStreamReader.close();
        //iStream.close(); //EDIT: Let the creator of the stream close it!
        // some readers may auto close the inner stream
        return builder.toString();
    }
}
