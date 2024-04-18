package com.example.myencryptionmethod.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myencryptionmethod.Adapter.UserAdapter;
import com.example.myencryptionmethod.Model.Chat;
import com.example.myencryptionmethod.Model.User;
import com.example.myencryptionmethod.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;
    private List<String> usersList;
    //creating variables and definition of types


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //assignment of variables to XML IDs
        //recyclerview variable is set size
        //recyclerview variable is set layout manager


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        //variable "fuser" defined as current user of Firebase instance authentication
        //arraylist object created
        //defining variable reference with Firebase data and getting data from "Chats" path


        reference.addValueEventListener(new ValueEventListener() {
            //references called and addvalueeventlistenr is called to record data changes within reference variable
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(fuser.getUid())){
                        usersList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fuser.getUid())) {
                        usersList.add(chat.getSender());
                    }
                }
                readChats();
            }
            //sets value of "userslist" = null
            //for statement passing snapshot value in Chat.class
            //if sender is equal to current user id (current user logged in) then
            //update userslist and add value getReciever()
            //if reciever is equal to current user id (current user logged in) then
            //update userslist and add value of getSender()
            //lastly the readChats() is called which checks if fuser has any chats with any uids

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void readChats() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        //arraylist object created
        //defining variable reference with Firebase data and getting data from "Users" path

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    // Check if the user's ID exists in usersList
                    if (usersList.contains(user.getId())) {
                        boolean isNewUser = true; // Flag to track if the user is new

                        // Check if the user is already in mUsers
                        for (User existingUser : mUsers) {
                            if (user.getId().equals(existingUser.getId())) {
                                isNewUser = false; // User already exists
                                break;
                            }
                        }

                        // Add the user if it's new
                        if (isNewUser) {
                            mUsers.add(user);
                        }
                    }
                }

                // Update the adapter with the new user list
                userAdapter = new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}