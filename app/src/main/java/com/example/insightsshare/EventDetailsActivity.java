package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {

    //Toolbar elements
    ImageView backButton;

    FirebaseDatabase database;
    DatabaseReference eventRef;

    String eventId;
    TextView eventName, eventCreator, eventCreationDate, eventPlace, eventDate, eventTime, eventDescription;
    RecyclerView participantsView;
    LinearLayout bottomContainer, participantsInfo;
    Button joinButton;

    // Get current user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        // Toolbar back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        // Get clicked event id
        eventId = getIntent().getStringExtra("eventId");
        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        eventRef = database.getReference().child("Event").child(eventId);

        eventName = findViewById(R.id.event_name);
        eventCreator = findViewById(R.id.event_creator);
        eventCreationDate = findViewById(R.id.event_creation_date);
        eventDate = findViewById(R.id.event_date);
        eventTime = findViewById(R.id.event_time);
        eventPlace =  findViewById(R.id.event_place);
        eventDescription = findViewById(R.id.event_description);
        participantsInfo = findViewById(R.id.participants_info);
        participantsView = findViewById(R.id.participants_list);
        bottomContainer = findViewById(R.id.bottom_container);
        joinButton = findViewById(R.id.join_button);

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EventItem eventItem = snapshot.getValue(EventItem.class);

                eventName.setText(eventItem.getEventName());
                eventCreator.setText(eventItem.getEventCreator());
                eventCreationDate.setText(eventItem.getEventCreationDate());
                eventDate.setText(eventItem.getEventDate());
                eventTime.setText(eventItem.getEventTime());
                eventPlace.setText(eventItem.getEventPlace());
                eventDescription.setText(eventItem.getEventDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("participantsList").hasChild(user.getUid())) {
                    setParticipantView();
                } else {
                    setNonParticipantView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void setParticipantView() {

        participantsInfo.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.GONE);
    }


    public void setNonParticipantView() {

        participantsInfo.setVisibility(View.GONE);
        bottomContainer.setVisibility(View.VISIBLE);

        joinButton.setOnClickListener(view -> {
            eventRef.child("participantsList").child(user.getUid()).setValue(true);
        });
    }


    public void generateParticipantsList(View view) {

        ArrayList<UserClass> participantsList = new ArrayList<>();
        ParticipantsListAdapter participantsListAdapter = new ParticipantsListAdapter(view.getContext(), participantsList);
        participantsView.setAdapter(participantsListAdapter);

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                participantsList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserClass userClass = dataSnapshot.getValue(UserClass.class);
                    participantsList.add(userClass);
                }
                participantsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}