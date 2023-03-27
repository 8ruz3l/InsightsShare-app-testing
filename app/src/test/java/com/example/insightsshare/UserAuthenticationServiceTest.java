package com.example.insightsshare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

public class UserAuthenticationServiceTest {

    FirebaseAuth fAuth;
    UserAuthenticationService userAuthenticationService;

    @Before
    public void setUp() throws Exception {
        fAuth = mock(FirebaseAuth.class);
        userAuthenticationService = new UserAuthenticationService(fAuth);
    }

    @Test
    public void validateSignInInformation_success_noErrorMessage() {
        String email = "test@test.de";
        String password = "123456";

        String errorMessage = userAuthenticationService.validateSignInInformation(email, password);

        assertNull(errorMessage);
    }

    @Test
    public void validateSignInInformation_fail_emailRequired() {
        String email = "";
        String password = "123456";

        String errorMessage = userAuthenticationService.validateSignInInformation(email, password);

        assert(errorMessage).equals("Email is required");
    }

    @Test
    public void validateSignInInformation_fail_passwordRequired() {
        String email = "test@test.de";
        String password = "";

        String expectedError = "Password is required";

        String errorMessage = userAuthenticationService.validateSignInInformation(email, password);

        assert(errorMessage).equals(expectedError);
    }

    @Test
    public void signIn_success_UserIsSignedIn() {
        String email = "test@example.com";
        String password = "password123";
        Task<AuthResult> expectedResult = mock(Task.class);

        when(fAuth.signInWithEmailAndPassword(email, password)).thenReturn(expectedResult);

        Task<AuthResult> actualResult = userAuthenticationService.signIn(email, password);
        assertEquals(expectedResult, actualResult);
    }
}