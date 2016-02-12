package com.threef.lifenotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        EditText heartBeatText = (EditText) findViewById(R.id.edit_text_heartbeat);
        baseURL += "&hartRate=";
        baseURL += heartBeatText.getText();

        Log.i("URL", baseURL);
    }

}
