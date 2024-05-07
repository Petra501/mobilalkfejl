package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {

    private static final String CLASS = ShopListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    //private ArrayList<ShopingItem> itemList;
    private ShopingItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(CLASS, "Hiteles felhasználó!");
            Toast.makeText(ShopListActivity.this, "Sikeres belépés", Toast.LENGTH_LONG).show();
        } else {
            Log.d(CLASS, "Nem hiteles felhasználó!");
            finish();
        }
    }
}