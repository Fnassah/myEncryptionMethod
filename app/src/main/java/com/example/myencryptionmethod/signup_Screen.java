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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup_Screen extends AppCompatActivity {
    EditText username, email, password;
    Button createAccountBtn;
    ImageButton back;
    FirebaseAuth auth;
    DatabaseReference reference;
    //defining variables and they types


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        back = findViewById(R.id.back_Btn);
        username = findViewById(R.id.signupEditUser);
        email = findViewById(R.id.signupEditEmail);
        password = findViewById(R.id.signupEditPass);
        createAccountBtn = findViewById(R.id.createAccount_Btn);
        //assignment of variables to XML IDs


        auth = FirebaseAuth.getInstance();
        //defining variable with type and method


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup_Screen.this, logo_Screen.class);
                startActivity(intent);
            }
        });
        //back onCLickListener allows the app to change screen from current screen
        // to next screen only after the back button has been tapped


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signup_Username = username.getText().toString();
                String signup_Email = email.getText().toString();
                String signup_Password = password.getText().toString();
                    //definition and assignment of old variables to new variables
                    //creates String variable of predefined variables with methods to create new variables
                    //collects text and converts to string from predefined variable and creates new variable


                if (TextUtils.isEmpty(signup_Username) || TextUtils.isEmpty(signup_Email) || TextUtils.isEmpty(signup_Password)) {
                    Toast.makeText(signup_Screen.this, "insert in fields indicated", Toast.LENGTH_SHORT).show();
                } else if (signup_Password.length() <= 4) {
                    Toast.makeText(signup_Screen.this, "5 characters or more required for password", Toast.LENGTH_SHORT).show();
                } else if (!signup_Email.contains("@")) {
                    Toast.makeText(signup_Screen.this, "insert valid email", Toast.LENGTH_SHORT).show();
                } else {
                    setCreateAccount(signup_Username, signup_Email, signup_Password);
                    Toast.makeText(signup_Screen.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                }
                //if editTextUsername,email and password field empty then it should display "insert in fields indicated"
                //else if length of password is less than 4 display "5 characters or more required for password"
                //else if email does not contain "@" then display "insert valid email"
                //else call createAccount method using previously created variables to be passed through
                //as argument through the method if successful display "Sign up successful"


            }
        });
    }
    private void setCreateAccount(String username, String email, String password) {
        auth.createUserWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    // create account method requires username, email, password
                    // createUserWithEmailAndPassword method requires email and password


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            //if task is successful then new firebase variable is created
                            //allowing to retrieve current user logged in
                            //assumes that firebaseUser is not null
                            //creates new variable that gets UID from firebaseUser


                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            //variable is assigned to get instance of generated data
                            //get reference allows to retrieve populated data from path dir called "Users"
                            //create and access of a child path being named the current userid


                            HashMap <String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            //creates HashMap requiring Strings that will be saved in the child path
                            //put function displays data and its corresponding data E.g "username", user1


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(signup_Screen.this, login_Screen.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    //calls reference variable and sets values of hashMap
                                        //if values set is successful then the application moves from current activity to next activity
                                    //flags are logs that shows activity history
                                    //startActivity method enables the code on the next activity to be executed
                                    //finish method stops activity that goes on in previous screen


                                }
                            });
                        } else {
                            Toast.makeText(signup_Screen.this, "invalid signup details", Toast.LENGTH_SHORT).show();
                            // else in any other scenario the message "invalid signup details" is displayed to user


                        }
                    }
                });
    }
}