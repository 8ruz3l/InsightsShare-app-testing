package com.example.insightsshare;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthenticationService {

    private FirebaseAuth fAuth;

    public UserAuthenticationService(FirebaseAuth fAuth) {
        this.fAuth = fAuth;
    }

    public UserAuthenticationService() {
        fAuth = FirebaseAuth.getInstance();
    }

    public String validateSignInInformation(String email, String password) {
        if(email.isEmpty()){
            return "Email is required";
        }
        if(password.isEmpty()){
            return "Password is required";
        }
        return null;
    }

    public Task<AuthResult> signIn(String email, String password) {
        return fAuth.signInWithEmailAndPassword(email, password);
    }

    public void signOut() {
        fAuth.signOut();
    }

    public FirebaseUser getUser() {
        return fAuth.getCurrentUser();
    }
}
