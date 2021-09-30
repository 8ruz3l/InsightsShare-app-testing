package com.example.insightsshare;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.example.insightsshare.databinding.ActivityEventplanning5Binding;


public class EventplanningActivity5 extends AppCompatActivity {

    //Variables for transfering Eventdata toDB
    EditText eventName, eventDate, eventTime, eventPlace, eventParticipant;
    //Switch Publish;
    Button ButtonSave;

    //connection with DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventplanning5);

        //Hooks to the xml Elements
        eventName= findViewById(R.id.InputEventName5);
        eventDate= findViewById(R.id.InputDate5);
        eventTime= findViewById(R.id.InputTime5);
        eventPlace= findViewById(R.id.InputLocation5);
        eventParticipant= findViewById(R.id.InputMaxParticipants5);
        //Publish= findViewById(R.id.InputPublish5);
        ButtonSave= findViewById(R.id.ButtonSave5);

        //save Data in DB on Button
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode= FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app/");
                reference= rootNode.getReference().child("Events");

                //get all the values
                String ValueEventName= eventName.getEditableText().toString();
                String ValueDate= eventDate.getEditableText().toString();
                String ValueTime= eventTime.getEditableText().toString(); //TODO: include Time in EventItemClass
                String ValuePlace= eventPlace.getEditableText().toString();
                String ValueParticipant= eventParticipant.getEditableText().toString();
                //Switch doesn't work yet
                //bool ValuePublish= eventPublish.getEditableText().toBool();

                //Mockdata:
                String ValueCreationDate= "21.09.2022";   //TODO:change mockdata to today
                String ValueEventCreator= "me"; //TODO:change mockdata to real automatically shown name

                EventItem helperclass = new EventItem(ValueEventName, ValueEventCreator, ValueCreationDate, ValuePlace, ValueDate, ValueParticipant);//, ValuePublish);

                reference.child(ValueEventName).setValue(helperclass);
            }
        }); //End of setOnClickListener
    }
}