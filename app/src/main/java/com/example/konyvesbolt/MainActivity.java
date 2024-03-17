package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final int KEY = 44;

    EditText userNameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.editTextUserName);
        passwordEditText = findViewById(R.id.editTextPassword);
    }


    public void login(View view) {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Log.i("MainActivity", "Felhasználónév: " + userName + ", jelszó: " + password);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("KEY", 44);
        //TODO
        startActivity(intent);
    }
}