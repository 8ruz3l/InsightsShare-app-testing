package com.example.insightsshare;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrActivity extends AppCompatActivity {

    TextInputLayout regEmail, regPasswort, regUsername;
    Button regButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        regEmail = findViewById(R.id.inputEmail);
        regPasswort = findViewById(R.id.inputPassword);
        regUsername = findViewById(R.id.inputUsername);
    }

    public void changeAnmeldeScreen(View v){
        Intent changeAnmelden = new Intent(this, MainActivity.class);
        startActivity(changeAnmelden);
    }

    public void registrieren(View v){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("User");

        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String passwort = regPasswort.getEditText().getText().toString();

        UserClass helperClass = new UserClass(username, email, passwort);

        reference.child(username).setValue("");
    }
}