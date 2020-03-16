package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imageView3);
        imageView.animate().rotationBy(1080).setStartDelay(200).setDuration(1000).start();
        imageView.animate().scaleXBy(1f).setStartDelay(500).setDuration(700).start();
        imageView.animate().scaleYBy(1f).setStartDelay(500).setStartDelay(700).start();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();





            }
        }, 1400);
    }
}
