package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

public class RegistrActivity extends AppCompatActivity /*implements OnClickListener*/{

    EditText regEmail, regPasswort, regUsername;
    Button regButton, backAnmButton;

    FirebaseAuth fAuth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        regEmail = findViewById(R.id.inputEmail);
        regPasswort = findViewById(R.id.inputPassword);
        regUsername = findViewById(R.id.inputUsername);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), NavigationActivity.class ));
            finish();
        }

        regButton = (Button) findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String email = regEmail.getText().toString().trim();
                String password = regPasswort.getText().toString().trim();
                String username = regUsername.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    regEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    regUsername.setError("Username is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPasswort.setError("Password is required");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //verification link
                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegistrActivity.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {

                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(RegistrActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), NavigationActivity.class));

                            rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app/");
                            reference = rootNode.getReference("User");

                            UserClass userInfo = new UserClass(username, email, password);

                            reference.child(username).setValue(userInfo);

                        }
                        else{
                            Toast.makeText(RegistrActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        backAnmButton = (Button) findViewById(R.id.backToAnmButton);
        backAnmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}