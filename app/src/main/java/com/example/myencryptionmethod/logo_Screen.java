package com.example.myencryptionmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

public class logo_Screen extends AppCompatActivity {
    private Button login, signUp;
    //defining variables and they types

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo_screen);

        login = findViewById(R.id.loginBtn_logo);
        signUp = findViewById(R.id.SignupBtn_logo);
        //assignment of variables to XML IDs


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logo_Screen.this, login_Screen.class);
                startActivity(intent);
            }
        });
        //login onCLickListener allows the app to change screen from current screen
        // to next screen only after the login button has been tapped


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logo_Screen.this,signup_Screen.class);
                startActivity(intent);
            }
        });
        //signup onCLickListener allows the app to change screen from current screen
        // to next screen only after the signup button has been tapped


    }
}