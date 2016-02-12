package com.threef.lifenotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class EPQResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epqresult);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView resultImage = (ImageView)findViewById(R.id.result_image);
        AppCompatTextView resultTitle = (AppCompatTextView) findViewById(R.id.result_title);
        AppCompatTextView resultDetail = (AppCompatTextView) findViewById(R.id.result_detail);


    }

}
