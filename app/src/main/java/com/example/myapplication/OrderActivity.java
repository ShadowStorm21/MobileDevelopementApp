package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.SmartPhones;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity {
    private TextView header,price,description;
    private ImageView headImg;
    private ArrayList<SmartPhones> smartPhone;
    private Button addToCart,buy;
    private int position;
    private String username,id,pid,pname;
    private double mPrice;
    private Uri mUri;
    private RatingBar ratingBar;
    private double total,avg,rate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        header = findViewById(R.id.textViewTitle);
        price = findViewById(R.id.textViewPriceOrder);
        headImg = findViewById(R.id.imageViewHeader);
        description = findViewById(R.id.textViewDescription);
        addToCart = findViewById(R.id.buttonAddToCart);
        buy = findViewById(R.id.buttonBuy);
        ratingBar = findViewById(R.id.ratingBar2);
        ratingBar.setEnabled(false);

        setTitle("Order Product");
        final Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
       position = intent.getIntExtra("position",0);
       username = LoginActivity.getUsername();
       id = LoginActivity.getId();
       smartPhone = (ArrayList<SmartPhones>) bundle.getSerializable("smartphones") ;
       pid = intent.getStringExtra("pid");
       mPrice = intent.getDoubleExtra("price",0);             // get all values from previous activity
       pname = intent.getStringExtra("pname");
       total = 0.0;
        setViews();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
                SendData();

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(OrderActivity.this,PaymentActivity.class);
                intent1.putExtra("totalPrice",mPrice);

                startActivity(intent1);

            }
        });

        setRatingBar();
    }


    private void setViews()
    {

        header.setText(smartPhone.get(position).getmName());
        price.setText("Price : "+smartPhone.get(position).getmPrice()+" $");
        description.setText("Description : "+smartPhone.get(position).getmDescription());

        // this will be changed later
        SmartPhones phone = smartPhone.get(position);
        mPrice = phone.getmPrice();
        pname = phone.getmName();

        if (!phone.getmProductId().isEmpty()) {

            FirebaseStorage.getInstance().getReference(phone.getmProductId() + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Picasso.get().load(uri).into(headImg);
                    mUri = uri;


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //set default img
                }
            });

        }

    }

    private void SendData()
    {
                Intent intent1 = new Intent(OrderActivity.this,CartActivity.class);
                intent1.putExtra("pname",pname);
                intent1.putExtra("uri",String.valueOf(mUri));
                intent1.putExtra("price",mPrice);
                intent1.putExtra("pid",pid);
                startActivity(intent1);
    }

    private void setRatingBar()
    {
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference myRef = database.getReference("Orders");

         myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 int i = 0;


                 for(DataSnapshot snapshot: dataSnapshot.getChildren())
                 {
                     HashMap hashMap = (HashMap) snapshot.getValue();
                     ArrayList<String> ids = (ArrayList<String>) hashMap.get("productsId");

                        if(hashMap.get("rating") != null) {
                            try {

                                for(int k = 0 ; k < ids.size(); k++)
                                {
                                    if(ids.get(k).equals(pid))
                                    {
                                        rate = (double) hashMap.get("rating");
                                        total = total + rate;
                                        i++;
                                    }
                                }
                                avg = total / i;

                                ratingBar.setRating((float) avg);


                            }catch (Exception e)
                            {
                                rate = (Long) hashMap.get("rating");
                                total = total + rate;
                                i++;
                                avg = total / i;
                                ratingBar.setRating((float) avg);
                                e.printStackTrace();
                            }


                        }

                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {


             }
         });
    }




}
