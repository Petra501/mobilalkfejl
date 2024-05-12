package com.example.konyvesbolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String CLASS = MainActivity.class.getName();
    private static final String PREFERENCE = MainActivity.class.getPackage().toString();
    private SharedPreferences preferences;
    private FirebaseAuth auth;
    private static final int KEY = 44;

    EditText userEmailEditText;
    EditText passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmailEditText = findViewById(R.id.editTextUserName);
        passwordEditText = findViewById(R.id.editTextPassword);

        preferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();

        Log.i(CLASS, "onCreate");
    }


    public void login(View view) {
        String userEmail = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //Log.i(CLASS, "Email: " + userEmail + ", jelszó: " + password);

        auth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(CLASS, "A felhasználó sikeresen belépve");
                    startShoplist();
                } else {
                    Log.d(CLASS, "A felhasználó belépése sikertelen");
                    Toast.makeText(MainActivity.this, "A felhasználó belépése sikertelen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startShoplist(){
        Intent intent = new Intent(this, ShopListActivity.class);
        //intent.putExtra("KEY", KEY);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("KEY", KEY);
        startActivity(intent);
    }

    public void guestLogin(View view) {
        auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(CLASS, "A vendég felhasználó sikeresen belépve");
                    startShoplist();
                } else {
                    Log.d(CLASS, "A vendég felhasználó belépése sikertelen");
                    Toast.makeText(MainActivity.this, "A vendég felhasználó belépése sikertelen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(CLASS, "onStart");
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
        editor.putString("userEmail", userEmailEditText.getText().toString());
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