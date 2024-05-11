package com.example.konyvesbolt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private static final String CLASS = ShopingItemAdapter.class.getName();
    private int lastPosition = -1;


    private Context context;
    private ArrayList<ShoppingItem> items;

    public CartItemAdapter(Context context, ArrayList<ShoppingItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cartitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem shoppingItem = items.get(position);

        int count = shoppingItem.getCartedCount();
        String title = shoppingItem.getName();
        String prize = shoppingItem.getPrize();

        String prizeWithoutCurrency = prize.replaceAll(" Ft", "");

        int totalPrice = Integer.parseInt(prizeWithoutCurrency) * count;

        holder.itemTitleTextView.setText(""+title);
        holder.itemPrizeTextView.setText(""+totalPrice+" Ft");
        holder.itemPrizeEachTextView.setText(""+count);


        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_row);
            holder.itemTitleTextView.startAnimation(animation);
            holder.itemPrizeTextView.startAnimation(animation);
            holder.itemPrizeEachTextView.startAnimation(animation);
            holder.itemQuantityTextView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemTitleTextView;
        private TextView itemPrizeTextView;
        private TextView itemPrizeEachTextView;
        private TextView itemQuantityTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemTitleTextView = itemView.findViewById(R.id.itemTitleTextView);
            itemPrizeTextView = itemView.findViewById(R.id.itemPrizeTextView);
            itemPrizeEachTextView = itemView.findViewById(R.id.itemPrizeEachTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);

        }

    }

}
