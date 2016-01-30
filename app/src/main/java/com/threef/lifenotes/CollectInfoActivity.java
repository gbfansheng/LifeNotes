package com.threef.lifenotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用网络
                String sex = gender.getCheckedRadioButtonId() == R.id.male ? "1" : "0";
                String married = marry.getCheckedRadioButtonId() == R.id.marry ? "1" : "0";
                Log.d("CollectionInfoActivity:", age.getText().toString() + " " + workage.getText().toString() + " " +
                        job.getText().toString() + " " + carrerTitle.getText().toString() + " " + sex + " " + married + " " +
                        smoke.isChecked() + " " + drink.isChecked() + " " + diet.isChecked() + " " + fit.isChecked() + " " +
                        heartRate.getText().toString() + " " + bmi.getText().toString() + " " + workTime.getText().toString() + " " +
                        pIncome.getText().toString() + " " + fIncome.getText().toString()+ " " + sleepStatus+ " " +
                        pressureStatus+ " " + livingStatus);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
