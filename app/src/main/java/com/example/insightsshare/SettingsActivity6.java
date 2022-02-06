package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity6 extends AppCompatActivity {
    // Toolbar elements
    ImageView backButton;

    // View elements: output profiledata
    TextView username, bio, firstname, lastname, birthday, phonenumber, nationality;
    //View elements: labels
    TextView labelFirstname, labelLastname, labelBirthday, labelPhonenumber, labelNationality;

    //Navigation to screen EditUserProfile
    Button navigation;

    // Database elements
    FirebaseDatabase database;
    DatabaseReference userRef;

    // Get current user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings6);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        // Get user id
        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        userRef = database.getReference().child("User").child(user.getUid());

        // variables, which refer to xml layout elements to output data
        username = findViewById(R.id.outputUsername6);
        bio =findViewById(R.id.outputBiography6);
        firstname=findViewById(R.id.outputFirstName6);
        lastname=findViewById(R.id.outputLastName6);
        birthday=findViewById(R.id.outputBirthday6);
        phonenumber=findViewById(R.id.outputPhonenumber6);
        nationality=findViewById(R.id.outputNationality6);

        // variables, which refer to xml layout elements labels
        labelFirstname=findViewById(R.id.labelFirstName6);
        labelLastname=findViewById(R.id.labelLastName6);
        labelBirthday=findViewById(R.id.labelBirthday6);
        labelPhonenumber=findViewById(R.id.labelPhonenumber6);
        labelNationality=findViewById(R.id.labelNationality6);

        navigation= findViewById(R.id.ButtonChangeProfil6);

        navigation.setOnClickListener(view -> openProfile());
    }

    /*this will be executed when the Settings become visible again
    DO NOT DELETE, because if you do, the user can change the profile, but when they come back
    to settings the old data is shown even though the data was updated in the database> irritating for the user*/
    protected void onStart(){
        super.onStart();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);

                username.setText(userClass.getUsername());
                bio.setText(userClass.getBio());
                firstname.setText(userClass.getFirstname());
                lastname.setText(userClass.getLastname());
                birthday.setText(userClass.getBirthday());
                phonenumber.setText(userClass.getPhoneNumber());
                nationality.setText(userClass.getNationality());

                //if there is "no entry" in the database for the optional contactinformations
                //then the label and the outputtextview should be invisible
                if(userClass.getFirstname()==""){
                    firstname.setVisibility(View.GONE);
                    labelFirstname.setVisibility(View.GONE);
                }

                if(userClass.getLastname()==""){
                    lastname.setVisibility(View.GONE);
                    labelLastname.setVisibility(View.GONE);
                }

                if(userClass.getBirthday()==""){
                    birthday.setVisibility(View.GONE);
                    labelBirthday.setVisibility(View.GONE);
                }

                if(userClass.getPhoneNumber()==""){
                    phonenumber.setVisibility(View.GONE);
                    labelPhonenumber.setVisibility(View.GONE);
                }

                if(userClass.getNationality()==""){
                    nationality.setVisibility(View.GONE);
                    labelNationality.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    // Methode to navigate to screen EditUserProfile
    private void openProfile() {
        Intent intent= new Intent(this, EditUserProfile.class);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}