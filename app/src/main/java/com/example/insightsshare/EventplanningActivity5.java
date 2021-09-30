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
    EditText EventName, Date, Time, Location, MaxParticipants;
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
        EventName= findViewById(R.id.InputEventName5);
        Date= findViewById(R.id.InputDate5);
        Time= findViewById(R.id.InputTime5);
        Location= findViewById(R.id.InputLocation5);
        MaxParticipants= findViewById(R.id.InputMaxParticipants5);
        //Publish= findViewById(R.id.InputPublish5);
        ButtonSave= findViewById(R.id.ButtonSave5);

        //save Data in DB on Button
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode= FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app/");
                reference= rootNode.getReference().child("Events");

                //get all the values
                String ValueEventName= EventName.getEditableText().toString();
                String ValueDate= Date.getEditableText().toString();
                String ValueTime= Time.getEditableText().toString();
                String ValueLocation= Location.getEditableText().toString();
                String ValueMaxParticipants= MaxParticipants.getEditableText().toString();
                //bool ValuePublish= Publish.getEditableText().toString();


                EventHelperClass helperclass = new EventHelperClass(ValueEventName, ValueDate, ValueTime, ValueLocation, ValueMaxParticipants);//, ValuePublish);

                reference.setValue(helperclass);
            }
        }); //End of setOnClickListener
    }
}