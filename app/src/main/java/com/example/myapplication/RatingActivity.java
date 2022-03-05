package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Classes.SmartPhones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button rate;
    private static String username,id;
    private int position;
    private float mRating;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Orders");
    private ArrayList<com.example.myapplication.Orders> ordersArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ratingBar = findViewById(R.id.ratingBar3);

        rate = findViewById(R.id.buttonRate);
        username = LoginActivity.getUsername();
        id = LoginActivity.getId();
//

         Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = intent.getIntExtra("position",0);
        ordersArrayList = (ArrayList<com.example.myapplication.Orders>) bundle.getSerializable("orders") ;
                                                                                                            // get all values from previous activity

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if(fromUser)
                {
                    mRating = rating;

                }
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.child(ordersArrayList.get(position).getOrder_id()).child("rating").setValue(mRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(RatingActivity.this, "Product Rated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(RatingActivity.this,MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });

            }
        });

    }


}
