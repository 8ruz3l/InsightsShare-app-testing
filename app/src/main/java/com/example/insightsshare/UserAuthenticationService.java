package com.example.insightsshare;

import android.text.TextUtils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserAuthenticationService {

    private FirebaseAuth fAuth;

    public UserAuthenticationService(FirebaseAuth fAuth) {
        this.fAuth = fAuth;
    }

    public String validateSignInInformation(String email, String password) {
        if(TextUtils.isEmpty(email)){
            return "Email is required";
        }
        if(TextUtils.isEmpty(password)){
            return "Password is required";
        }
        return null;
    }

    public Task<AuthResult> signIn(String email, String password) {
        return fAuth.signInWithEmailAndPassword(email, password);
    }
}
