package com.example.seajobnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Intent i=new Intent(SplashScreenActivity.this, LoginActivity.class);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}