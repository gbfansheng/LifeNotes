package com.threef.lifenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class EPQResultActivity extends AppCompatActivity {
    float nTScore;
    float eTScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epqresult);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nTScore = getIntent().getFloatExtra("nTScore",0);
        eTScore = getIntent().getFloatExtra("eTScore",0);

        CoordinatesView coordinatesView = (CoordinatesView) findViewById(R.id.coordinates);
        coordinatesView.setResult(eTScore,nTScore);
        AppCompatTextView resultTitle = (AppCompatTextView) findViewById(R.id.result_title);
        if (nTScore <= 50 && eTScore <= 50) {
            resultTitle.setText("粘液质人格");
        } else if (nTScore <= 50 && eTScore >= 50) {
            resultTitle.setText("多血质人格");
        } else if (nTScore >= 50 && eTScore <= 50) {
            resultTitle.setText("抑郁质人格");
        } else if (nTScore >= 50 && eTScore >= 50) {
            resultTitle.setText("胆汁质人格");
        }
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
