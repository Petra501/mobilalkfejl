package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    EditText phoneEditText;
    EditText typeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int key = getIntent().getIntExtra("KEY", 0);

        if(key != 44){
            finish();
        }

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        typeEditText = findViewById(R.id.typeEditText);
    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String userEmail = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String type = typeEditText.getText().toString();

        if (!password.equals(passwordAgain)){
            Log.e("RegisterActivity", "A jelszó és megerősítése nem egyezik!");
        }

        Log.i("RegisterActivity", "Regisztrált: " + userName + ", Email: " + userEmail + ", telefon: " + phone + ", típus: " + type);
        //TODO: A regisztrációs funkcionalitást meg kellene valósítani egyszer.
    }
}