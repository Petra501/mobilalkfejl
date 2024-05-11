package com.example.konyvesbolt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final String CLASS = ShopListActivity.class.getName();
    private static final int KEY = 44;
    private RecyclerView mRecyclerView;
    private TextView mtextView;
    private CartItemAdapter mAdapter;
    private int totalPrize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_dialog);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int key = getIntent().getIntExtra("KEY", 0);

        if (key != 44) {
            finish();
        }

        mRecyclerView = findViewById(R.id.cartItems);
        mtextView = findViewById(R.id.totalPrizeTextView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ShoppingItem> cartItems = getCartItems();

        mAdapter = new CartItemAdapter(this, cartItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<ShoppingItem> getCartItems() {
        ArrayList<ShoppingItem> cartItems = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("Konyvek");

        itemsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            int totalPrice = 0;
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ShoppingItem item = document.toObject(ShoppingItem.class);

                int cartedCount = item.getCartedCount();
                String prize = item.getPrize();

                String prizeWithoutCurrency = prize.replaceAll(" Ft", "");

                int actuallyPrize = Integer.parseInt(prizeWithoutCurrency) * cartedCount;

                totalPrice += actuallyPrize;

                if (cartedCount > 0) {
                    cartItems.add(item);
                }
            }
            totalPrize = totalPrice;
            mtextView.setText(totalPrize + " Ft");
            mAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Log.e(CLASS, "Error getting documents: ", e);
        });

        return cartItems;
    }
}