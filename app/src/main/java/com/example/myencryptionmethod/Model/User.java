package com.example.myencryptionmethod.Model;

public class User {
    private String id;
    private String username;
    //defining private variables and they types


    public User (String id, String username) {
        this.id = id;
        this.username = username;

    }
    //variables from this class specifically assigned as new variables within this class

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(){

    }
}
