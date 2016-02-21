package com.threef.lifenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("创建一个记录");

        setContentView(R.layout.activity_create);
        int[] ids = new int[]{R.id.button1,R.id.button2,R.id.button3};
        for (final int id:ids) {
            Button createWorkBtn = (Button) findViewById(id);
            createWorkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goEdit(id);
                }
            });
        }
    }

    void goEdit(int id){
        Intent intent=new Intent(this,EditActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
