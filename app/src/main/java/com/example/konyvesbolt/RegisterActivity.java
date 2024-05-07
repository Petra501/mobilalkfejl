package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String CLASS = RegisterActivity.class.getName();
    private static final String PREFERENCE = RegisterActivity.class.getPackage().toString();
    private SharedPreferences preferences;

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    EditText phoneEditText;
   // EditText typeEditText;
    Spinner phoneSpinner;

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
        phoneSpinner = findViewById(R.id.phoneSpinner);
        //typeEditText = findViewById(R.id.typeEditText);

        preferences = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userNameEditText.setText(userName);
        passwordEditText.setText(password);
        passwordAgainEditText.setText(password);

        phoneSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.phoneType, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSpinner.setAdapter(adapter);

        Log.i(CLASS, "onCreate");
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
        String phoneType = phoneSpinner.getSelectedItem().toString();

        //String type = typeEditText.getText().toString();

        if (!password.equals(passwordAgain)){
            Log.e(CLASS, "A jelszó és megerősítése nem egyezik!");
            return;
        }


       // Log.i(CLASS, "Regisztrált: " + userName + ", Email: " + userEmail + ", telefon: " + phone + ", típus: " + type);
        //TODO: A regisztrációs funkcionalitást meg kellene valósítani egyszer.
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedPhoneItem = parent.getItemAtPosition(position).toString();
        Log.i(CLASS, selectedPhoneItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //TODO: kiválaszott spiner item üres
    }
}