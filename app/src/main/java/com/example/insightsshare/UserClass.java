package com.example.insightsshare;

public class UserClass {

    String userID, username, email, bio;

    public UserClass(){
        // Required empty public constructor
    }

    public UserClass (String userID,String username, String email, String bio){
        this.userID= userID;
        this.username = username;
        this.email = email;
        this.bio = bio;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

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
