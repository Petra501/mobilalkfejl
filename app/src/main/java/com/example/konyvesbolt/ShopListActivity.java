package com.example.konyvesbolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ShopListActivity extends AppCompatActivity {

    private static final String CLASS = ShopListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth auth;

    private FrameLayout redCircle;
    private TextView countTextView;
    private int cartItems = 0;
    private int gridNumber = 1;
    private RecyclerView mRecyclerView;
    private ArrayList<ShoppingItem> mItemsData;
    private ShopingItemAdapter mAdapter;

    private SharedPreferences preferences;

    private boolean viewRow = true;

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

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemsData = new ArrayList<>();
        mAdapter = new ShopingItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
    }

    private void initializeData() {
        String[] itemsList = getResources().getStringArray(R.array.item_titles);
        String[] itemsSubtitle = getResources().getStringArray(R.array.item_subtitles);
        String[] itemsPrice = getResources().getStringArray(R.array.item_price);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.item_images);
        TypedArray itemsRate = getResources().obtainTypedArray(R.array.item_rates);

        mItemsData.clear();

        for (int i = 0; i < itemsList.length; i++){
            mItemsData.add(new ShoppingItem(itemsList[i], itemsSubtitle[i], itemsPrice[i], itemsRate.getFloat(i, 0), itemsImageResource.getResourceId(i, 0)));
        }

        itemsImageResource.recycle();

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            // Itt tedd meg a Settings menüpont működését
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.cart);
        FrameLayout frameLayout = (FrameLayout) alertMenuItem.getActionView();

        redCircle = (FrameLayout) frameLayout.findViewById(R.id.viewRedCircleFrameLayout);
        countTextView = (TextView) frameLayout.findViewById(R.id.viewAlertCountTextview);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon(){
        cartItems = (cartItems + 1);
        if (0 < cartItems){
            countTextView.setText(String.valueOf(cartItems));
        } else {
            countTextView.setText("");
        }

        redCircle.setVisibility((cartItems > 0) ? VISIBLE : GONE);

    }

}