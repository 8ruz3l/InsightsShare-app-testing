package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {
    // Toolbar elements
    ImageView backButton;

    //variables to refer to the layouts xml-elements in this class
    Button resetButton;

    // Database elements
    FirebaseDatabase database;
    DatabaseReference userRef;
    FirebaseAuth fAuth;

    // Get current user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        resetButton = findViewById(R.id.resPassword);

        // Get user id
        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        userRef = database.getReference().child("User").child(user.getUid());

        //get the DB data of the current user
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        resetButton.setOnClickListener(view -> {
            String eMail = user.getEmail();
            fAuth.sendPasswordResetEmail(eMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(SettingActivity.this, "Reset Email sent", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SettingActivity.this, "Failed to send Email", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finishAffinity();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}