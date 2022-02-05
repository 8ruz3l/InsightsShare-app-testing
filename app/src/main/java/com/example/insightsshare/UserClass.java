package com.example.insightsshare;

public class UserClass {

    String userID, username, email, bio, firstname, lastname, birthday, phoneNumber, nationality;
//TODO birthday
    public UserClass(){
        // Required empty public constructor
    }

    public UserClass (String userID,String username, String email, String bio,
                      String firstname, String lastname, String birthday, String phoneNumber, String nationality){
        this.userID= userID;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday= birthday;
        this.phoneNumber =phoneNumber;
        this.nationality =nationality;
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

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getBirthday() { return birthday; }

    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getNationality() { return nationality; }

    public void setNationality(String nationality) { this.nationality = nationality; }
}
