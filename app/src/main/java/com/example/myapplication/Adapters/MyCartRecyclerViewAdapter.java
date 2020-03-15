package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CartActivity;
import com.example.myapplication.Classes.Cart;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCartRecyclerViewAdapter extends RecyclerView.Adapter<MyCartRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Cart> items = new ArrayList<>();


    public void setItems(ArrayList<Cart> items) {
        this.items = items;
    }

    public MyCartRecyclerViewAdapter(ArrayList<Cart> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
   {
       public TextView mPrice,mTitle,productQuantity,mId;
       public ImageView mImageView;

       public ViewHolder(@NonNull final View itemView) {
           super(itemView);
           mPrice = itemView.findViewById(R.id.textViewCartPrice);
           mTitle = itemView.findViewById(R.id.textViewCartTitle);
           mImageView = itemView.findViewById(R.id.imageViewCart);
           productQuantity = itemView.findViewById(R.id.tvQuantity);
           mId = itemView.findViewById(R.id.tvId);
       }
   }
    @NonNull
    @Override
    public MyCartRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCartRecyclerViewAdapter.ViewHolder holder, int position) {

        final Cart item = items.get(position);
        if(item == null) {
            holder.mPrice.setText("No items Avaliable");
            return;
        }
            holder.mTitle.setText(item.getProduct_name());
            holder.mPrice.setText(item.getPrice()+" $");
            holder.mId.setText(item.getProduct_id());
            Integer quantity = CartActivity.findProduct(item);
            if (item.getUri() != null) {
                holder.mImageView.setImageURI(item.getUri());
                Picasso.get().load(item.getUri()).into(holder.mImageView);
            }
            if(quantity != null)
                holder.productQuantity.setText(quantity + "");


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
