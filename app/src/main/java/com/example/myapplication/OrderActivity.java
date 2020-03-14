package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {
    private TextView header,price,description;
    private ImageView headImg;
    private ArrayList<SmartPhones> smartPhone;
    private Button addToCart,buy;
    private int position;
    private String username,id,pid,pname;
    private double mPrice;
    private Uri mUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        header = findViewById(R.id.textViewTitle);
        price = findViewById(R.id.textViewPrice);
        headImg = findViewById(R.id.imageViewHeader);
        description = findViewById(R.id.textViewDescription);
        addToCart = findViewById(R.id.buttonAddToCart);
        buy = findViewById(R.id.buttonBuy);

        setTitle("Order Product");
        final Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
       position = intent.getIntExtra("position",0);
       username = intent.getStringExtra("username");
       id = intent.getStringExtra("id");
       smartPhone = (ArrayList<SmartPhones>) bundle.getSerializable("smartphones") ;
       pid = intent.getStringExtra("pid");
       mPrice = intent.getDoubleExtra("price",0);             // get all values from previous activity
       pname = intent.getStringExtra("pname");

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
                startActivity(intent1);
            }
        });


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
                intent1.putExtra("userid",id);
                intent1.putExtra("username",username);
                intent1.putExtra("pname",pname);
                intent1.putExtra("uri",String.valueOf(mUri));
                //Text(this, "uri"+String.valueOf(mUri), Toast.LENGTH_SHORT).show();
                intent1.putExtra("price",mPrice);
                intent1.putExtra("pid",pid);
                startActivity(intent1);

    }


}
