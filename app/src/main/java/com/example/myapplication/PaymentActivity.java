package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {
    private Button pay,clear;
    private EditText cardNo,cardCVV;
    private Spinner cardYear, cardMonth;
    private RadioGroup paymentOptions;
    private TextView price,errMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        pay = findViewById(R.id.btnPay);
        clear = findViewById(R.id.btnClear);
        price = findViewById(R.id.tvTotalPrice);
        errMsg = findViewById(R.id.errMsg);
        cardNo = findViewById(R.id.etCardNo);
        cardMonth = findViewById(R.id.spCardMonth);
        cardYear = findViewById(R.id.spCardYear);
        cardCVV = findViewById(R.id.etCVV);
        paymentOptions = findViewById(R.id.rgPaymentOptions);

        final Double cartTotalPrice = getIntent().getDoubleExtra("total",0);
        price.setText(  cartTotalPrice.toString() + " $");
        price.setTextColor(Color.RED);
        errMsg.setTextColor(Color.RED);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !isDataValid())
                    return;
                errMsg.setText("");

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

                insertRecordDatabase();

            }
        });

        paymentOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (paymentOptions.getCheckedRadioButtonId()){
                    case R.id.rbCash: // disable the visa edit texts
                        cardCVV.setEnabled(false);
                        cardMonth.setEnabled(false);
                        cardYear.setEnabled(false);
                        cardNo.setEnabled(false);
                        clear.setEnabled(false);
                        break;
                    case R.id.rbVisa: // enable the visa edit texts
                        cardCVV.setEnabled(true);
                        cardMonth.setEnabled(true);
                        cardYear.setEnabled(true);
                        cardNo.setEnabled(true);
                        clear.setEnabled(true);
                        break;
                }
            }
        });
    }

    private boolean isDataValid() {
        if( paymentOptions.getCheckedRadioButtonId() == R.id.rbCash ) {
            return true;
        }
        // check card data

        String cardNumber = cardNo.getText().toString();
        String CVV = cardCVV.getText().toString();
        Integer month = Integer.valueOf(cardMonth.getSelectedItem().toString()) - 1;
        Integer year = Integer.valueOf(cardYear.getSelectedItem().toString());

        Calendar calendar = Calendar.getInstance();
        Integer curMonth = calendar.get(calendar.MONTH);
        Integer curYear = calendar.get(calendar.YEAR);

        if(cardNumber.length() != 16) {
            errMsg.setText("Card Number must be 16 Number");
            return false;
        }
        if(CVV.length() != 3){
            errMsg.setText("CVV must be 3 Numbers");
            return false;
        }
        if(year < curYear || month < curMonth){
            errMsg.setText("Your Card is expired!");
            return false;
        }

        return true;
    }


    private void insertRecordDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        String uid,username;
    }
}