package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String CLASS = MainActivity.class.getName();
    private static final String PREFERENCE = MainActivity.class.getPackage().toString();
    private SharedPreferences preferences;
    private static final int KEY = 44;

    EditText userNameEditText;
    EditText passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.editTextUserName);
        passwordEditText = findViewById(R.id.editTextPassword);

        preferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        Log.i(CLASS, "onCreate");
    }


    public void login(View view) {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Log.i(CLASS, "Felhasználónév: " + userName + ", jelszó: " + password);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("KEY", KEY);
        //TODO
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(CLASS, "onStart");
        //TODO: Az onStart és egyéb life ciklusos logolásait ki kell szedni
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(CLASS, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(CLASS, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", userNameEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.apply();

        Log.i(CLASS, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CLASS, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(CLASS, "onRestart");
    }
}