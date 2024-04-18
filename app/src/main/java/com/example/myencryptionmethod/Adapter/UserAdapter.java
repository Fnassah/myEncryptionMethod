package com.example.myencryptionmethod.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myencryptionmethod.Model.User;
import com.example.myencryptionmethod.R;
import com.example.myencryptionmethod.message_Screen;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private List<User> mUsers;
    //class extends recyclerView class variables and attributes
    //defining private variables and they types

    public UserAdapter(Context mContext,List<User>mUsers){

        this.mUsers = mUsers;
        this.mContext = mContext;
    }
    //variables from this class specifically assigned as new variables within this class


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }
    //CreateViewHolder method requires parent and viewType (recyclerView)
    //returns UserAdapter from other class and type viewHolder method with view argument


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = mUsers.get(position);
        holder.username.setText(user.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext, message_Screen.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });
    }
    //BindViewHolder method requires ViewHolder variable and int value
    //users variable is created to get position of mUsers
    //holder argument is defined by calling getUsername method on object 'user'
    //it then setsText from variable 'username' that is part of the holder variable
    //setOnlClickListener method is called on holder.itemView variable
    //this allows the user to tap on a item within the holder
    //onClick method requires viewType to function
    //when executed intent method requiring mContext (item view data) and message_Screen.class (intended Activity)
    //makes intent require extra data by using putExtra() method requiring the Id from the user variable
    //the startActivity method is called with the intent variable being passed through by...
    // ...mContext which refers to data within the current activity


    @Override
    public int getItemCount() {
        return mUsers.size();
    }
    //item count method returns mUsers variable size meaning the int value of the total amount of items that defines mUsers

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.recycle_username);
        }
    }
}
//viewHolder class extends recyclerView class
//creating public variable and its type
//viewHolder method calls primary class variable 'itemView' use of super
//assignment of username variable to XML IDs
