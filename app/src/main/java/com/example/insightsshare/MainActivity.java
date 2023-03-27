package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView forgotPasswordLabel;
    UserAuthenticationService userAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.inputEmail2);
        loginPassword = findViewById(R.id.inputPassword2);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        userAuthService = new UserAuthenticationService(fAuth);

        if(userAuthService.getUser() != null){
            startActivity(new Intent(getApplicationContext(), NavigationActivity.class ));
            finish();
        }

        loginButton = findViewById(R.id.anmeldeButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                String validationError = userAuthService.validateSignInInformation(email, password);

                if (validationError != null) {
                    displayError(validationError);
                    return;
                }

                signIn(email, password);
            }
        });

        Button toRegButton = findViewById(R.id.backToAnmButton);
        toRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegistrActivity.class);
                startActivity(i);
            }
        });

      /*  public void resetPassword() {
            final EditText resetMail = new EditText(view.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Password");
            passwordResetDialog.setMessage("Enter Email to receive a reset link");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String eMail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(eMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this, "Reset Email sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to send Email", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } */

        forgotPasswordLabel = findViewById(R.id.forgotPw);
        forgotPasswordLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    private void signIn(String email, String password) {
        userAuthService.signIn(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayError(String validationError) {
        if (validationError.equals("Email is required")){
            loginEmail.setError("Email is required");
        }
        if(validationError.equals("Password is required")){
            loginPassword.setError("Password is required");
        }
    }
}