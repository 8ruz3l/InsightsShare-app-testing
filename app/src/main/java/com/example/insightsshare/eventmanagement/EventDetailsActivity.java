package com.example.insightsshare.eventmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insightsshare.NavigationActivity;
import com.example.insightsshare.R;
import com.example.insightsshare.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {
    // Toolbar elements
    ImageView backButton;

    // Database elements
    FirebaseDatabase database;
    DatabaseReference eventRef, userRef, participantsListRef;
    ValueEventListener eventListener;

    // View elements
    String eventId, eventCreatorsID;
    TextView eventName, eventCreator, eventCreationDate, eventPlace, eventDate, eventTime, eventDescription, currentParticipantsNumber, maxParticipantsNumber, participantsMaxedNotification;
    RecyclerView participantsView;
    LinearLayout bottomContainer, participantsInfo, eventControl;
    Button joinButton, leaveButton, editButton, deleteButton;

    // Get current user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // Variables to compare the current and maximum number of participants
    int currentParticipants, maxParticipants;

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
        userRef = database.getReference().child("User");
        participantsListRef = eventRef.child("participantsList");

        // Define main view elements of the event details
        eventName = findViewById(R.id.event_name);
        eventCreator = findViewById(R.id.event_creator);
        eventCreationDate = findViewById(R.id.event_creation_date);
        eventDate = findViewById(R.id.event_date);
        eventTime = findViewById(R.id.event_time);
        eventPlace =  findViewById(R.id.event_place);
        eventDescription = findViewById(R.id.event_description);

        // Participants info on participant view
        participantsInfo = findViewById(R.id.participants_info);
        participantsMaxedNotification = findViewById(R.id.participants_maxed_notification);
        currentParticipantsNumber = findViewById(R.id.event_current_participants);
        maxParticipantsNumber = findViewById(R.id.event_max_participants);
        participantsView = findViewById(R.id.participants_list);
        participantsView.setLayoutManager(new LinearLayoutManager(this));

        // Participant view
        leaveButton = findViewById(R.id.leave_button);

        // Creator view
        eventControl = findViewById(R.id.event_control);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        // Bottom container on non participant view
        bottomContainer = findViewById(R.id.bottom_container);
        joinButton = findViewById(R.id.join_button);

        eventListener = eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EventItem eventItem = snapshot.getValue(EventItem.class);

                eventName.setText(eventItem.getEventName());
                eventCreator.setText(eventItem.getEventCreator());
                eventCreatorsID=eventItem.getEventCreatorsID();
                eventCreationDate.setText(eventItem.getEventCreationDate());
                eventDate.setText(eventItem.getEventDate());
                eventTime.setText(eventItem.getEventTime());
                eventPlace.setText(eventItem.getEventPlace());
                eventDescription.setText(eventItem.getEventDescription());

                maxParticipants = Integer.valueOf(eventItem.getMaxParticipants());
                maxParticipantsNumber.setText(eventItem.getMaxParticipants());

                currentParticipants = eventItem.getCurrentParticipants();
                currentParticipantsNumber.setText(String.valueOf(currentParticipants));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Determine the view elements to display based on user's role (Non-Participant/Participant/Creator)
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot listSnapshot) {

                userRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                        UserClass userClass = userSnapshot.getValue(UserClass.class);
                        if (userClass.getUserID().equals (eventCreatorsID)) {
                            setCreatorView();
                        } else if (listSnapshot.child("participantsList").hasChild(user.getUid())){
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void setParticipantView() {
        participantsInfo.setVisibility(View.VISIBLE);
        leaveButton.setVisibility(View.VISIBLE);
        eventControl.setVisibility(View.GONE);
        bottomContainer.setVisibility(View.GONE);

        generateParticipantsList();

        leaveButton.setOnClickListener(view -> {
            eventRef.child("participantsList").child(user.getUid()).removeValue();
            eventRef.child("currentParticipants").setValue(--currentParticipants);
            Toast.makeText(EventDetailsActivity.this,R.string.toast_event_leave,Toast.LENGTH_SHORT).show();
        });
    }


    private void setCreatorView() {
        participantsInfo.setVisibility(View.VISIBLE);
        leaveButton.setVisibility(View.GONE);
        eventControl.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.GONE);

        generateParticipantsList();

        editButton.setOnClickListener(view -> {
            Intent i = new Intent(EventDetailsActivity.this, EventplanningActivity5.class);
            Bundle extras = new Bundle();
            extras.putBoolean("updateExistingEvent", true);
            extras.putString("existingEventID", eventId);
            i.putExtras(extras);
            startActivity(i);
        });

        deleteButton.setOnClickListener(view -> {
            Intent i = new Intent(EventDetailsActivity.this, NavigationActivity.class);

        //  following line not implemented due to bugs
        //  Toast.makeText(EventDetailsActivity.this,R.string.toast_event_deleted,Toast.LENGTH_SHORT).show();

            startActivity(i);
            finish();
            eventRef.removeEventListener(eventListener);
            eventRef.removeValue();
        });
    }


    private void setNonParticipantView() {
        participantsInfo.setVisibility(View.GONE);
        leaveButton.setVisibility(View.GONE);
        eventControl.setVisibility(View.GONE);
        bottomContainer.setVisibility(View.VISIBLE);

        joinButton.setOnClickListener(view -> {
            eventRef.child("participantsList").child(user.getUid()).setValue(false);
            eventRef.child("currentParticipants").setValue(++currentParticipants);
            Toast.makeText(EventDetailsActivity.this,R.string.toast_event_participate,Toast.LENGTH_SHORT).show();
        });

        if (currentParticipants == maxParticipants){
            participantsMaxedNotification.setVisibility(View.VISIBLE);
            joinButton.setEnabled(false);
        } else {
            participantsMaxedNotification.setVisibility(View.GONE);
            joinButton.setEnabled(true);
        }
    }


    public void generateParticipantsList() {
        ArrayList<UserClass> participantsList = new ArrayList<>();
        ParticipantsListAdapter participantsListAdapter = new ParticipantsListAdapter(this, participantsList);
        participantsView.setAdapter(participantsListAdapter);

        participantsListRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot listSnapshot) {

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                        participantsList.clear();

                        for (DataSnapshot dataSnapshot : userSnapshot.getChildren()) {
                            if (listSnapshot.hasChild(dataSnapshot.getKey())) {
                                UserClass userClass = dataSnapshot.getValue(UserClass.class);
                                participantsList.add(userClass);
                            }
                        }
                        participantsListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}