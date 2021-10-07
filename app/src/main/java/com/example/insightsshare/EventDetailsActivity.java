package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDetailsActivity extends AppCompatActivity {
    //Toolbar elements
    ImageView backButton;

    FirebaseDatabase database;
    DatabaseReference idRef;

    String eventId;
    TextView eventName, eventCreator, eventCreationDate, eventPlace, eventDate, eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        // Toolbar back button
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Get clicked event id
        eventId = getIntent().getStringExtra("eventId");

        eventName = findViewById(R.id.event_name);
        eventCreator = findViewById(R.id.event_creator);
        eventCreationDate = findViewById(R.id.event_creation_date);
        eventDate = findViewById(R.id.event_date);
        eventTime = findViewById(R.id.event_time);
        eventPlace =  findViewById(R.id.event_place);

        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        idRef = database.getReference().child("Event").child(eventId);

        idRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EventItem eventItem = snapshot.getValue(EventItem.class);

                eventName.setText(eventItem.getEventName());
                eventCreator.setText(eventItem.getEventCreator());
                eventCreationDate.setText(eventItem.getEventCreationDate());
                eventDate.setText(eventItem.getEventDate());
                eventTime.setText(eventItem.getEventTime());
                eventPlace.setText(eventItem.getEventPlace());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}