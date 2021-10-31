package com.example.insightsshare;

public class UserClass {

    String username, email;

    public UserClass(){
        // Required empty public constructor
    }

    public UserClass (String username, String email){
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
