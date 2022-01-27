package com.example.insightsshare;

public class UserClass {

    String username, email, bio;

    public UserClass(){
        // Required empty public constructor
    }

    public UserClass (String username, String email, String bio){
        this.username = username;
        this.email = email;
        this.bio = bio;
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

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }
}
