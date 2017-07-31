package com.example.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demo.R;
import com.example.demo.dialog.SaveDialogFragment;


public class DialogActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        init();
    }


    private void init() {
        findViewById(R.id.btn_show_fragment_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_fragment_dialog:
                SaveDialogFragment saveDialogFragment = new SaveDialogFragment();
                saveDialogFragment.show(getSupportFragmentManager(), "saveDialog");
                break;
        }

    }
}
