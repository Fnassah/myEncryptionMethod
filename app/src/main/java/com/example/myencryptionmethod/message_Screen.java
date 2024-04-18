package com.example.myencryptionmethod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myencryptionmethod.Adapter.MessageAdapter;
import com.example.myencryptionmethod.Model.Chat;
import com.example.myencryptionmethod.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class message_Screen extends AppCompatActivity {
    TextView receiverUserName;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;
    ImageButton send_Btn;
    EditText edit_SendText;
    private byte[] encryptionKey = {2, 1, -2, 0, 9, 5, 7, 4, 4, 2, 5, 3, 2, 5, 8, 9};
    private Cipher cipher;
    private Cipher decipher;
    private SecretKeySpec secretKeySpec;
    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;

    //defining variables and they types
    //private variables so it cant be accessed
    //byte array variable created custom encryptionKey


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);

        send_Btn = findViewById(R.id.send_Btn);
        edit_SendText = findViewById(R.id.edit_SendText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //assignment of variables to XML IDs

        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //sets action bar as toolbar including back button
//gets support action bar and displays home something as true
//sets the location of the button to enable onClickListener
//on click method requires View when tapped
        toolbar.setNavigationOnClickListener(v -> finish());

        receiverUserName = findViewById(R.id.userName);
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //assignment of variables to XML IDs
        //new variable created, function to getIntent
        //new variable created, function to get intent and its extra data specifically userid
        //new variable created, function view registered users get instance of current user logged in

        send_Btn.setOnClickListener(v -> {
            String inputMessage = edit_SendText.getText().toString();
            String encryptedMessage = AESEncryptionMethod(inputMessage);
            if (!inputMessage.equals("")) {
                sendMessage(firebaseUser.getUid(), userid, encryptedMessage);
                edit_SendText.getText().clear();
            } else {
                Toast.makeText(message_Screen.this, "Requires input to send try now", Toast.LENGTH_SHORT).show();
            }
            edit_SendText.setHint("Type here");
        });
        //else if message not equal to empty message is true then display message "Requires input to send try now"
        //after prompt application automatically indicates where to type message displaying "type here" in message field


        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        try {
            cipher = Cipher.getInstance("AES");
            decipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        //variable created to get instance of database from directory "Users" to its subfolder variable userid
        //try function attempts to create variable cipher and decipher, function getting instance of cipher algorithm "AES"
        //catches error if not algorithm is found throws exception executes next line
        //catches error of no padding throws exception to execute next line without crashes


        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                receiverUserName.setText(user.getUsername());
                readMessages(firebaseUser.getUid(), userid);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //variable secretKeySpec is assigned, method function requiring encryptionKey and the algorithm that will be used for encryption
        //reference = data that is saved in the designated path
        //event listener method triggers if there are changes within the database
        //there are 2 events firstly on dataChange variable user is created
        //function to go into snapshot instance and call method getValue of User.class
        //sets text of getUsername method as receiver username
        //on cancelled method is only called if an error occurs however nothing is coded in


    }

    private void sendMessage(String sender, String receiver, String encrypted) {

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
//        hashMap.put("message",message);
        hashMap.put("encrypted", encrypted);
//        hashMap.put("decrypted",decrypted);


        reference1.child("Chats").push().setValue(hashMap);
    }
    //sendMessage method is created requiring current userid, receiver userid, encrypted message and decrypted message
    //new reference of database is created, function to get instance of database and its path
    //creates HashMap requiring Strings and objects
    //put function displays data with its corresponding data E.g String- "encrypted", object- encrypted
    //reference variable is called to child path "Chats", function to push data and set values of hashmap within the child directory


    private String AESEncryptionMethod(String string) {

        byte[] stringByte = string.getBytes();
        byte[] encryptedByte;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedByte = cipher.doFinal(stringByte);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        //private encryption method created requiring string
        //creates byte array variables, function to convert string into bytes
        //byte array variable created, function to get stringByte length
        //try attempts to initialize cipher variable by calling encryption mode to encrypt byte while using the private key and the specified algorithm
        //new variable created, function do final method resets cipher from any bytes that was previously encrypted
        //catches errors such as wrongKey, wrongBlockSize and badPadding and throws them all as exceptions

        String returnString = null;
        try {
            returnString = new String(encryptedByte, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return returnString;
    }
    //variable returnString set to null
    //try attempt to encryptByte again using ISO-8859-1 to enable to use more tropical characters
    //catches encoding errors throws it as exception
    //returns any string that is generated


    private String AESDecryptionMethod(String string) throws UnsupportedEncodingException {
        byte[] EncryptedByte = string.getBytes("ISO-8859-1");
        String decryptedString = string;
        byte[] decryption;
        //private decryptionMethod requires string variable
        //variable byte array is created, function to convert string into bytes and use ISO-8859-1 for reference if decrypted
        //byte type array variable created


        try {
            decipher.init(cipher.DECRYPT_MODE, secretKeySpec);
            decryption = decipher.doFinal(EncryptedByte);
            decryptedString = new String(decryption);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return decryptedString;
        //try attempts to initialize decipher variable by calling decryption mode to encrypt byte while using the private key and the specified algorithm
        //new variable created, function do final method resets cipher from any bytes that was previously encrypted
        //new variable string is set to decryption
        //catches errors such as wrongKey, wrongBlockSize and badPadding and throws them all as exceptions
    }

    private void readMessages(String myId, String userId) {

        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Chat chat = snapshot1.getValue(Chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(message_Screen.this, mChat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
