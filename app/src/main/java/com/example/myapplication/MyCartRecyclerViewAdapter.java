package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCartRecyclerViewAdapter extends RecyclerView.Adapter<MyCartRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Cart> items = new ArrayList<>();

    public MyCartRecyclerViewAdapter(ArrayList<Cart> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
   {
       public TextView mPrice,mTitle,productQuantity;
       public ImageView mImageView;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           mPrice = itemView.findViewById(R.id.textViewCartPrice);
           mTitle = itemView.findViewById(R.id.textViewCartTitle);
           mImageView = itemView.findViewById(R.id.imageViewCart);
           productQuantity = itemView.findViewById(R.id.tvQuantity);
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

        Cart item = items.get(position);
        if(item != null) {
            holder.mTitle.setText(item.getProduct_name());
            holder.mPrice.setText(String.valueOf(item.getPrice()));
            if (item.getUri() != null)
                holder.mImageView.setImageURI(item.getUri());
            Picasso.get().load(item.getUri()).into(holder.mImageView);

            Integer quantitiy = CartActivity.findProduct(item);
            if(quantitiy != null)
                holder.productQuantity.setText(quantitiy + "");
        }
        else
        {
            holder.mPrice.setText("No items Avaliable");
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
