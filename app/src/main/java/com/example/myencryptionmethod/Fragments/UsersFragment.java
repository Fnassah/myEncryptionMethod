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

public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List <User> mUsers;
    //defining private variables and they types


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_users,container, false);
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        readUsers();
        return view;
    }
    //onCreateView refers to when the fragment is generated it requires LayoutInflater, container and savedInstanceState
    //view variable sets the layout or inflates layout of fragment from fragment_users as a container
    //assignment of variables to XML IDs
    //recyclerView size is set fixed size as true within the inflated fragment
    //recyclerView utilises layoutManager it gets recyclerView items and finalises userInput boundaries for them items
    //variable mUsers ArrayList is created to list registered users from database
    //readUsers() method is called and view variable is returned in order to display retrieved data


    private void  readUsers(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        //readUsers method is created function to retrieve populated data "Users" from database
        //variable firebaseUser is assigned to get instance of firebaseAuth and get username of CurrentUser
        //variable reference is assigned to get instance of generated data from firebaseDatabase
        //get reference allows to retrieve populated data from path dir called "Users"


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                //reference refers to saved data in database
                //event listener method triggers if there are changes within the database
                //function onDataChange clears data from mUsers array


                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if (!user.getId().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                    }
                }
                userAdapter = new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }
            //for includes both dataSnapshot and snapshot variables
            //variable user defined as getting value of dataSnapshot from User.class
            //assumes user variable is not equal to null (user value is "true")
            //assumes firebaseUser variable is not equal to null (firebaseUser value is "true")
            //if variable user.getID is not equal to firebaseUser.getUid
            //then mUsers.add(user) adds user that is newly registered to array
            //userAdapter variable created function to both getContext existing users and mUsers variable for new users
            //recyclerView linked to setAdapter to display userAdapter variable (users and new users)

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}