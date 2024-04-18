package com.example.myencryptionmethod.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myencryptionmethod.Model.Chat;
import com.example.myencryptionmethod.Model.User;
import com.example.myencryptionmethod.R;
import com.example.myencryptionmethod.message_Screen;
import com.google.android.material.transition.Hold;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    //static final int variable created represents value 0 or 1

    private Context mContext;
    private List<Chat> mChat;
    //class extends recyclerView class variables and attributes
    //defining private variables and they types

    FirebaseUser fUser;


    public MessageAdapter(Context mContext, List<Chat> mChat) {

        this.mChat = mChat;
        this.mContext = mContext;

    }
    //variables from this class specifically assigned as new variables within this class


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }
    //CreateViewHolder method requires parent and viewType (recyclerView)
    //returns UserAdapter from other class and type viewHolder method with view argument


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {


        Chat chat = mChat.get(position);
        String encrypted = chat.getEncrypted();
        if (encrypted.length() < 0) {
            throw new RuntimeException("no messages");
        } else {
        }
        try {
            String message = chat.getDecrypted();
            holder.show_message.setText(message);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


    }
    //BindViewHolder method requires ViewHolder variable and int value
    //responsible for getting messages from database and showing message on appropriate chat screen
    //determined by the receiver ID and sender ID


    @Override
    public int getItemCount() {
        return mChat.size();
    }
    //item count method returns mChat variable size meaning the int value of the total amount of items that defines mUsers

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.sent_Message);

        }
        //viewHolder class extends recyclerView class
        //creating public variable and its type
        //viewHolder method calls primary class variable 'itemView' use of super
        //assignment of username variable to XML IDs
    }

    @Override
    public int getItemViewType(int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
