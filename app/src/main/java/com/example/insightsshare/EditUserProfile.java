package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUserProfile extends AppCompatActivity {
    //Toolbar elements
    ImageView backButton;

    //variables to refer to the layouts xml-elements in this class
    EditText userName, bio;
    Button buttonSave;

    //connection with DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference, userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        //get the DB data of the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        userReference = rootNode.getReference().child("User").child(user.getUid());

        //get the current userinfos
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);
                userName.setText(userClass.getUsername());
                bio.setText(userClass.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Hooks to the xml Elements
        userName= findViewById(R.id.OutputUserName);
        bio= findViewById(R.id.OutputBiography);
        buttonSave= findViewById(R.id.ButtonSave);

        rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        reference = rootNode.getReference().child("User").child(user.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);

                userName.setText(userClass.getUsername());
                bio.setText(userClass.getBio());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); // end of Listener

        //save/ update changed data
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //put data into strings for them to be stored
               String valueProfileName= userName.getEditableText().toString();
               String valueBio= bio.getEditableText().toString();

            }
        })
    }
}