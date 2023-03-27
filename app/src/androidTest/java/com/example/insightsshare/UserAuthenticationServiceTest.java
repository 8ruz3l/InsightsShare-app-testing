package com.example.insightsshare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Semaphore;

public class UserAuthenticationServiceTest {

    UserAuthenticationService userAuthenticationService;

    @Before
    public void setUp() throws Exception {
        userAuthenticationService = new UserAuthenticationService();
        userAuthenticationService.signOut();
    }

    @Test
    public void signIn_success_UserIsSignedIn() throws InterruptedException {
        String email = "test@abc.de";
        String password = "123456";

        userAuthenticationService.signIn(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                assertTrue(authResult.getUser().getEmail().equals(email));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fail("Login failed"+ e.getMessage());
            }
        });
    }

    @Test
    public void signIn_fail_UserDoesNotExist() {
        String email = "a";
        String password = "1";

        userAuthenticationService.signIn(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                fail("User should not exist");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                assertTrue(true);
            }
        });
    }
}