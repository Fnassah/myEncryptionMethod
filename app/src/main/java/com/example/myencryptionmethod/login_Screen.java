package com.example.myencryptionmethod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_Screen extends AppCompatActivity {

    Button loginAccountBtn;
    EditText email,password;
    ImageButton back;
    FirebaseAuth auth;
    //defining variables and they types


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        back = findViewById(R.id.back_Btn);
        email = findViewById(R.id.EditEmail);
        password = findViewById(R.id.EditPass);
        loginAccountBtn = findViewById(R.id.loginAccount_Btn);
        //assignment of variables to XML IDs


        auth = FirebaseAuth.getInstance();
        //defining variable with type and method


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_Screen.this, logo_Screen.class);
                startActivity(intent);
            }
        });
        //back onCLickListener allows the app to change screen from current screen
        // to next screen only after the back button has been tapped


        loginAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_Email = email.getText().toString();
                String login_Password = password.getText().toString();
                //once login button is tapped
                //definition and assignment of old variables to new variables
                //creates String variable of predefined variables with methods to create new variables
                //collects text and converts to string from predefined variables and creates new variables


                if (TextUtils.isEmpty(login_Email) || TextUtils.isEmpty(login_Password)){
                    Toast.makeText(login_Screen.this,"insert in fields indicated",Toast.LENGTH_SHORT).show();
                    // if editEmail,editPassword Field empty then display "insert in fields indicated"


                }else {
                    auth.signInWithEmailAndPassword(login_Email, login_Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(login_Screen.this, MainActivity.class);
                                        Toast.makeText(login_Screen.this,"login successful",Toast.LENGTH_SHORT).show();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        Toast.makeText(login_Screen.this,"invalid email or password",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    //else auth variable calls signInWithEmailAndPassword method requiring email and password arguments
                    //if method is successful then application then the application moves from current activity to next activity
                    //flags are logs that shows activity history
                    //startActivity method enables the code on the next activity to be executed
                    //finish method stops activity that goes on in previous screen
                    //else the application displays "invalid email or password" to the user


                }
            }
        });
    }
}