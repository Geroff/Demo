package com.example.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demo.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        findViewById(R.id.btn_test_span).setOnClickListener(this);
        findViewById(R.id.btn_test_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_test_span:
                intent.setClass(this, SpanActivity.class);
                break;

            case R.id.btn_test_dialog:
                intent.setClass(this, DialogActivity.class);
                break;
        }

        startActivity(intent);
    }
}
