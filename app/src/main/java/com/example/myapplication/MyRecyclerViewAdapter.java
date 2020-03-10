package com.example.myapplication;

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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<SmartPhones> smartPhones;
    private View.OnClickListener mOnItemClickListener;


    public MyRecyclerViewAdapter(ArrayList<SmartPhones> smartPhones) {
        this.smartPhones = smartPhones;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        SmartPhones smartPhone = smartPhones.get(position);

        holder.title.setText(smartPhone.getmName());
        holder.subtitle.setText(smartPhone.getmPrice()+"$");
        holder.imageView.setImageURI(smartPhone.getmPic());
// this will be changed also

        if (!smartPhone.getmProductId().isEmpty()) {
            FirebaseStorage.getInstance().getReference(smartPhone.getmProductId() + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Picasso.get().load(uri).into(holder.imageView);

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


                    Picasso.get().load(uri).into(holder.imageView);

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
        return smartPhones.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView title,subtitle;
        public ImageView imageView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewProductName);
            subtitle = itemView.findViewById(R.id.textViewProductPrice);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
            imageView = itemView.findViewById(R.id.imageViewProductImg);

        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

}
