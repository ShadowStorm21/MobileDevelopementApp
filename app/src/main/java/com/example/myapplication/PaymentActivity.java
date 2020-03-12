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

public class PaymentActivity extends AppCompatActivity {
    private Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        pay = findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });
    }


    private void insertRecordDatabase()
    {
        // after paying add the record to database in a new parent
    }
}
