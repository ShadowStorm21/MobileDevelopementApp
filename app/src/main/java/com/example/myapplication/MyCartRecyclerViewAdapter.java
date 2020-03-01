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
       public TextView mPrice,mTitle;
       public ImageView mImageView;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           mPrice = itemView.findViewById(R.id.textViewCartPrice);
           mTitle = itemView.findViewById(R.id.textViewCartTitle);
           mImageView = itemView.findViewById(R.id.imageViewCart);
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

        holder.mTitle.setText(item.getProduct_name());
        holder.mPrice.setText(item.getPrice()+"$");
        holder.mImageView.setImageURI(item.getUri());


        if (item.getProduct_name().equalsIgnoreCase("Samsung Galaxy S20")) {
            FirebaseStorage.getInstance().getReference("s20.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {


                    Picasso.get().load(uri).into(holder.mImageView);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //profilePic.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }
            });



        }
        else
        {
            FirebaseStorage.getInstance().getReference("iphone.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {


                    Picasso.get().load(uri).into(holder.mImageView);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //profilePic.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
