package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditUserProfile extends AppCompatActivity {
    //Toolbar elements
    ImageView backButton;

    //variables to refer to the layouts xml-elements in this class
    EditText userName, bio;
    Button buttonSave;

    //variables for the connection with the DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference, userReference;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //Hooks to the xml Elements
        userName= findViewById(R.id.OutputUserName);
        bio= findViewById(R.id.OutputBiography);
        buttonSave= findViewById(R.id.ButtonSave);


        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        //get the DB data of the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        if (user == null) throw new AssertionError();
        userReference = rootNode.getReference().child("User").child(user.getUid());

        //get the current userinfos (to prefill the EditTextforms with the current data)
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);
                userName.setText(userClass.getUsername());
                bio.setText(userClass.getBio());
                userID= userClass.getUserID();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //save/ update changed data
        buttonSave.setOnClickListener(view -> {
            rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
            reference = rootNode.getReference().child("User");

            //put data into Strings to get stored in DB
            String valueProfileName = userName.getEditableText().toString();
            String valueBio = bio.getEditableText().toString();

            //put the changeable data in a Map because this is the type in which it can be stored in: reference.child().updateChildren(!!!MAP REQUIRED!!!);
            HashMap<String, Object> UserMap = new HashMap<>();
            UserMap.put("username", valueProfileName);
            UserMap.put("bio", valueBio);
            
            //changed data is stored in the DB
            reference.child(userID).updateChildren(UserMap);

            //Automatically redirect user to NavigationActivity or EventDetailsActivity
            onBackPressed();

            //display a little success-message, so that the user knows the data was saved
            Toast.makeText(EditUserProfile.this, R.string.toast_profile_changed, Toast.LENGTH_SHORT).show();
        });
    }
}