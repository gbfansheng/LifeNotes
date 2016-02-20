package com.threef.lifenotes;

import android.content.Intent;
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
        resultImage.setImageResource(R.mipmap.image1);
        AppCompatTextView resultTitle = (AppCompatTextView) findViewById(R.id.result_title);
        resultTitle.setText("？？？人格");
        AppCompatTextView resultDetail = (AppCompatTextView) findViewById(R.id.result_detail);
        resultDetail.setText(getIntent().getStringExtra("epqresult"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EPQResultActivity.this, QueryActivity.class);
                startActivity(intent);
            }
        });
    }

}
