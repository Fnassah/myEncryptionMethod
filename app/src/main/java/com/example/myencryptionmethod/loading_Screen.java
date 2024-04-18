package com.example.myencryptionmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class loading_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(loading_Screen.this, logo_Screen.class));
                finish();
            }
        }, 3000);
        // Handler method allows the app to load loading_Screen activity first
        // the postDelayed method delays the loading_Screen for a total of 3secs until...
        // ... it changes to the logo_Screen activity


    }
}