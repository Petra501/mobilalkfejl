package com.example.konyvesbolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ShopListActivity extends AppCompatActivity {

    private static final String CLASS = ShopListActivity.class.getName();
    private static final int KEY = 44;
    private FirebaseUser user;
    private FirebaseAuth auth;

    private FrameLayout redCircle;
    private TextView countTextView;
    private int cartItems = 0;
    private int gridNumber = 1;
    private RecyclerView mRecyclerView;
    private ArrayList<ShoppingItem> mItemsData;
    private ShopingItemAdapter mAdapter;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private SharedPreferences preferences;

    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(CLASS, "Hiteles felhasználó!");
        } else {
            Log.d(CLASS, "Nem hiteles felhasználó!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemsData = new ArrayList<>();
        mAdapter = new ShopingItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Konyvek");
        queryData();

    }

    private void queryData() {
        mItemsData.clear();
        mItems.limit(12).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ShoppingItem item = document.toObject(ShoppingItem.class);
                item.setId(document.getId());
                mItemsData.add(item);
            }

            if (mItemsData.size() == 0) {
                initializeData();
                queryData();
            }
            mAdapter.notifyDataSetChanged();
        });
    }


    public void deleteItem(ShoppingItem item) {
        DocumentReference ref = mItems.document(item._getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                    Toast.makeText(this, item._getId() +" könyv sikeresen törölve.", Toast.LENGTH_LONG).show();

                })
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, item._getId() +" könyv törlése sikertelen.", Toast.LENGTH_LONG).show();
                });

        queryData();
    }

    private void initializeData() {
        String[] itemsList = getResources().getStringArray(R.array.item_titles);
        String[] itemsSubtitle = getResources().getStringArray(R.array.item_subtitles);
        String[] itemsPrice = getResources().getStringArray(R.array.item_price);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.item_images);
        TypedArray itemsRate = getResources().obtainTypedArray(R.array.item_rates);

        for (int i = 0; i < itemsList.length; i++){
            mItems.add(new ShoppingItem(
                    itemsList[i],
                    itemsSubtitle[i],
                    itemsPrice[i],
                    itemsRate.getFloat(i, 0),
                    itemsImageResource.getResourceId(i, 0), 0));
        }

        itemsImageResource.recycle();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(CLASS, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        } else if (item.getItemId() == R.id.view) {
            if (viewRow) {
                changeSpanCount(item, R.drawable.ic_baseline_view_module_24, 1);
            } else {
                changeSpanCount(item, R.drawable.ic_baseline_view_stream_24, 2);
            }
            return true;
        } else if (item.getItemId() == R.id.cart) {
            //Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show();
            startCartActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void startCartActivity(){
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("KEY", KEY);
        startActivity(intent);
    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    //TODO: notification
    public void updateAlertIcon(ShoppingItem item){

        mItems.document(item._getId()).update("cartedCount", item.getCartedCount() + 1)
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, item._getId() +" könyv módosítása sikertelen.", Toast.LENGTH_LONG).show();
                });

        queryData();

    }

    public void updateCart(ShoppingItem item){

        mItems.document(item._getId()).update("cartedCount", 0)
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, item._getId() +" könyv módosítása sikertelen.", Toast.LENGTH_LONG).show();
                });

        queryData();

    }

}