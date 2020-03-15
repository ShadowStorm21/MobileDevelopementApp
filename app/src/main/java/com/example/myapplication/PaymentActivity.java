package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
    private Button pay;
    private String user_id,username, product_id;
    private double price;
    private EditText cardHolderName,cardNumber,cardCvv;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Orders");
    private TextView txtPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        pay = findViewById(R.id.pay);
        setTitle("Payment");
        Intent intent = getIntent();
        txtPrice = findViewById(R.id.textView4);

        if(intent.hasExtra("username"))
        {
            user_id = intent.getStringExtra("id");
            username = intent.getStringExtra("username");
            product_id = intent.getStringExtra("pid");
            price = intent.getDoubleExtra("total",0);
            txtPrice.setText("Total Price : "+ price + " $");

        }
        else
        {
            Intent intent1 = new Intent(PaymentActivity.this,LoginActivity.class);
            startActivity(intent1);
            finish();
        }


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkEditTextFields())
                {
                    insertRecordDatabase();
                }


            }
        });
    }


    private void insertRecordDatabase()
    {
        // after paying add the record to database in a new parent
        String order_id = myRef.push().getKey();
        Orders order = new Orders(order_id,user_id,username,price);
        myRef.child(order_id).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

               sendNotification();
            }
        });
    }

    private boolean checkEditTextFields()
    {
        cardHolderName = findViewById(R.id.editTextCardHolder);
        cardNumber = findViewById(R.id.editTextCardNumber);
        cardCvv = findViewById(R.id.editTextCVV);

        if(cardHolderName.getText().toString().isEmpty() || cardNumber.getText().toString().isEmpty() || cardCvv.getText().toString().isEmpty())
        {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(cardNumber.getText().toString().length() > 16)
        {
            Toast.makeText(this, "Card Number Must not exceed 16 Digits ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(cardCvv.getText().toString().length() > 3)
        {
            Toast.makeText(this, "CVV must not exceed 3 Digits ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendNotification()
    {
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(PaymentActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID="LOLW";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"XD", NotificationManager.IMPORTANCE_LOW);
        Notification notification = new NotificationCompat.Builder(PaymentActivity.this,CHANNEL_ID)
                .setTicker(getString(R.string.app_name))
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.content))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(10, notification);
    }
}
