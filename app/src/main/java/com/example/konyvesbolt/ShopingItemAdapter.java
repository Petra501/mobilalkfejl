package com.example.konyvesbolt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ShopingItemAdapter extends RecyclerView.Adapter<ShopingItemAdapter.ViewHolder> implements Filterable {
    private static final String CLASS = ShopingItemAdapter.class.getName();
    private ArrayList<ShoppingItem> shoppingItemsData;
    private ArrayList<ShoppingItem> allShoppingItemsData;
    private Context mContext;
    private int lastPosition = -1;

    ShopingItemAdapter(Context context, ArrayList<ShoppingItem> data){
        this.shoppingItemsData = data;
        this.allShoppingItemsData = data;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopingItemAdapter.ViewHolder holder, int position) {
        ShoppingItem item = shoppingItemsData.get(position);
        
        holder.bindTo(item);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.imageView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return shoppingItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ShoppingItem> listFilter = new ArrayList<>();
            FilterResults  results = new FilterResults();

            if (constraint == null || constraint.length() == 0){
                results.count = allShoppingItemsData.size();
                results.values = allShoppingItemsData;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ShoppingItem item : allShoppingItemsData){
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        listFilter.add(item);
                    }
                }
                results.count = listFilter.size();
                results.values = listFilter;
            }
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            shoppingItemsData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView subtitleTextView;
        private TextView priceTextView;
        private ImageView imageView;
        private RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            priceTextView = itemView.findViewById(R.id.prizeTextView);
            imageView = itemView.findViewById(R.id.itemImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.addToCartButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(CLASS, "Kosárba gomb megnyomva");
                    ((ShopListActivity)mContext).updateAlertIcon();
                }
            });
        }

        public void bindTo(ShoppingItem item) {
            titleTextView.setText(item.getName());
            subtitleTextView.setText(item.getSubtitle());
            priceTextView.setText(item.getPrize());
            ratingBar.setRating(item.getRatedInfo());

            imageView.getLayoutParams().height = 600;

            Glide.with(mContext)
                    .load(item.getImageResource())
                    .into(imageView);
        }
    }
}

