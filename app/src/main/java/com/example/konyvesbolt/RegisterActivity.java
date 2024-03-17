package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int key = getIntent().getIntExtra("KEY", 0);

        if(key != 44){
            finish();
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {
    }
}