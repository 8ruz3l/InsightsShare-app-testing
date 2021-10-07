package com.example.insightsshare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class EventplanningActivity5 extends AppCompatActivity {
    //Toolbar elements
    ImageView backButton;

    //Variables for transfering Eventdata toDB
    EditText eventName, eventDate, eventTime, eventPlace, maxParticipants;
    Button ButtonSave;

    //connection with DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventplanning5);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Hooks to the xml Elements
        eventName= findViewById(R.id.InputEventName5);
        eventDate= findViewById(R.id.InputDate5);
        eventTime= findViewById(R.id.InputTime5);
        eventPlace= findViewById(R.id.InputLocation5);
        maxParticipants = findViewById(R.id.InputMaxParticipants5);
        ButtonSave= findViewById(R.id.ButtonSave5);

        //save Data in DB on Button
        ButtonSave.setOnClickListener(new View.OnClickListener() {

                @Override
            public void onClick(View view) {
                rootNode= FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app/");
                reference= rootNode.getReference().child("Event");

                    //later used in ValueCreationDate
                    Date today= new Date();

                //get all the values of the data (input) in stings so it can be stored
                String ValueEventId = reference.push().getKey();
                String ValueEventName= eventName.getEditableText().toString();
                String ValueDate= eventDate.getEditableText().toString();
                String ValueTime= eventTime.getEditableText().toString();
                String ValuePlace= eventPlace.getEditableText().toString();
                String ValueParticipant= maxParticipants.getEditableText().toString();
                String ValueCreationDate= today.toString();
                String ValueEventCreator= "me"; //TODO:change mockdata to real automatically shown name

                //this is the point in which the data is stored in the DB
                EventItem eventEntry = new EventItem(ValueEventId, ValueEventName, ValueEventCreator, ValueCreationDate, ValuePlace, ValueDate, ValueTime, ValueParticipant);//, ValuePublish);

                reference.child(ValueEventId).setValue(eventEntry); //PrimaryKey is ValueEventId

                //Automatically redirect user to NavigationActivity
                onBackPressed();

                //display a little success-message, so that the user knows the data was saved
                    Toast.makeText(EventplanningActivity5.this,"Erfolgreich gespeichert!",Toast.LENGTH_SHORT).show();
                    //TODO: it would be nice to have a message that shows that the data is saved as to avoid redundant data because users don't belive it is saved

            }   //end of onClick
        });     //end of setOnClickListener
    }           //end of onCreate
}               //end of EventplanningActivity5