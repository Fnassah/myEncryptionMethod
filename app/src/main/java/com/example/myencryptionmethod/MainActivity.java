package com.example.myencryptionmethod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myencryptionmethod.Fragments.ChatsFragment;
import com.example.myencryptionmethod.Fragments.UsersFragment;
import com.example.myencryptionmethod.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton logoutBtn;
    TextView profileUserName;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    //creating variables and definition of types


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutBtn = findViewById(R.id.logout_Btn);
        profileUserName = findViewById(R.id.userName);
        //assignment of variables to XML IDs


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        //defining variables with user login data getting current user data
        //defining variables with database data and getting data from "Users".child path


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, logo_Screen.class);
                startActivity(intent);
            }
        });
        //once logout button is tapped
        //firebaseAuth gets instance and signs instance out
        //application then moves from current activity to next activity in this case the logo_Screen


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                profileUserName.setText(user.getUsername());
            }
            //reference = data that is saved in the designated path
            // event listener method triggers if there are changes within the database
            //there are 2 events firstly on dataChange variable user is created
            //function to go into snapshot instance and call method getValue of User.class
            //assumes user value is not empty
            //sets text as current username to getUsername method


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // onCancelled is to handle any database errors


            }
        });

        TabLayout tabLayout = findViewById(R.id.tab_Layout);
        ViewPager viewPager = findViewById(R.id.view_Pager);
        //assignment of variables to XML IDs


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatsFragment(),"Recent Chats");
        viewPagerAdapter.addFragment(new UsersFragment(),"Registered Users");
        //implementation of viewPagerAdapter creates new instance of pagerAdapter and requires FragmentManager for Fragment implementation
        //add Fragment method calls both Chat and Users fragment method and assigns specific title to them

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //sets adapter to viewPager so viewpager can display content
        //tabLayout is setup with viewPager so it can be used within the viewPager


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        //class pagerAdapter extends fragmentPagerAdapter linking them both
        //creates private arrayList variables

        ViewPagerAdapter (FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
            //viewPagerAdapter requires fragmentManager variable
            //creates instance of fragments and titles in array list type


        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
            //getItem method requires int variable
            //returns fragment position by using get method


        }

        @Override
        public int getCount() {
            return fragments.size();
            //returns number of total fragments


        }

        public  void addFragment (Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
            //addFragment method adds requires fragment and String variable
            //fragment and title variable is defined with fragment argument and title argument

            
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}