package com.threef.lifenotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.app.AlertDialog;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;


public class EditActivity extends AppCompatActivity {

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
                confirm_edit(id);
            }
        });
    }

    void confirm_edit(int id){
        String baseURL = "http://182.92.222.196/sh/recordActivity.htm?";

        EditText calorieText = (EditText) findViewById(R.id.edit_text_calorie);

        if (calorieText != null){
            baseURL += "&calorie=";
            baseURL += calorieText.getText();
        }

        EditText timeText = (EditText) findViewById(R.id.edit_text_time);
        baseURL += "&duiring=";
        baseURL += timeText.getText();
        if (timeText.getText() == null){
            showAlert(getString(R.string.alert_title),getString(R.string.alert_time));
            return;
        }

        EditText heartBeatText = (EditText) findViewById(R.id.edit_text_heartbeat);
        baseURL += "&hartRate=";
        baseURL += heartBeatText.getText();

        if (heartBeatText.getText() == null){
            showAlert(getString(R.string.alert_title),getString(R.string.alert_heartbeat));
            return;
        }

        RadioGroup entertainment_location = (RadioGroup) findViewById(R.id.entertainment_location_group);
        if (entertainment_location != null){
            baseURL += "&entertaiment_location=";
            int checkedId = entertainment_location.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_entertainment_location));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += checkedBtn.getText();
        }

        RadioGroup exercise_location = (RadioGroup) findViewById(R.id.exercise_location_group);
        if (exercise_location != null){
            baseURL += "&exercise_location=";
            int checkedId = exercise_location.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_exercise_location));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += checkedBtn.getText();
        }

        RadioGroup exercise_type = (RadioGroup) findViewById(R.id.exercise_type_group);
        if (exercise_type != null){
            baseURL += "&exercise_type=";
            int checkedId = exercise_type.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_exercise_type));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += checkedBtn.getText();
        }

        RadioGroup work_tension = (RadioGroup) findViewById(R.id.work_tension);
        if (work_tension != null){
            baseURL += "&work_tension=";
            int checkedId = work_tension.getCheckedRadioButtonId();
            if (checkedId < 0){
                showAlert(getString(R.string.alert_title),getString(R.string.alert_work_tension));
                return;
            }
            RadioButton checkedBtn = (RadioButton)findViewById(checkedId);
            baseURL += checkedBtn.getText();
        }

        baseURL += "&moods=";
        baseURL += getMoods();

        Log.i("URL", baseURL);
        requestURL(baseURL);
    }

    String[] getMoods(){
        List moods = new ArrayList();
        for(int i = 0;i < 15;i ++){
            String id_name = "checkBox" + i;
            int id = getResId(id_name, CheckBox.class);
            CheckBox checkBox = (CheckBox) findViewById(id);
            if (checkBox.isChecked()){
                moods.add(checkBox.getText());
            }
        }
        return  (String[])moods.toArray();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //    设置Title的图标
        builder.setTitle(title);
        //    设置Content来显示一个信息
        builder.setMessage(text);
        //    设置一个PositiveButton
        builder.setPositiveButton("确定",null);
    }


    void requestURL(String url){

    }
}
