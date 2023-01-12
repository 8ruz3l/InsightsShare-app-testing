package com.example.insightsshare.eventmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightsshare.R;
import com.example.insightsshare.eventmanagement.EventItem;
import com.example.insightsshare.eventmanagement.EventListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyEventFragment extends Fragment {

    // Database elements
    FirebaseDatabase database;
    DatabaseReference eventRef;

    // View elements
    RecyclerView createdEventsView, participatedEventsView;
    TextView noCreatedEvent, noParticipatedEvent;

    EventListAdapter createdEventListAdapter, participatedEventListAdapter;
    ArrayList<EventItem> createdEventList, participatedEventList;

    // Get current user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MyEventFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_event, container, false);

        // Set fragment title in toolbar
        getActivity().setTitle(R.string.title_my_event_toolbar);

        createdEventsView = view.findViewById(R.id.my_created_event_list);
        createdEventsView.setLayoutManager(new LinearLayoutManager(getContext()));

        participatedEventsView = view.findViewById(R.id.my_participated_event_list);
        participatedEventsView.setLayoutManager(new LinearLayoutManager(getContext()));

        noCreatedEvent = view.findViewById(R.id.no_created_event);
        noParticipatedEvent = view.findViewById(R.id.no_participated_event);

        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        eventRef = database.getReference().child("Event");

        createdEventList = new ArrayList<>();
        createdEventListAdapter = new EventListAdapter(getContext(), createdEventList);
        createdEventsView.setAdapter(createdEventListAdapter);

        participatedEventList = new ArrayList<>();
        participatedEventListAdapter = new EventListAdapter(getContext(), participatedEventList);
        participatedEventsView.setAdapter(participatedEventListAdapter);

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                createdEventList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("eventCreatorsID").getValue().equals(user.getUid())) {
                        EventItem eventItem = dataSnapshot.getValue(EventItem.class);
                        createdEventList.add(eventItem);
                    }
                }
                createdEventListAdapter.notifyDataSetChanged();

                if (createdEventList.size() == 0) {
                    noCreatedEvent.setVisibility(View.VISIBLE);
                } else {
                    noCreatedEvent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                participatedEventList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("participantsList").hasChild(user.getUid()) && !dataSnapshot.child("eventCreatorsID").getValue().equals(user.getUid())) {
                        EventItem eventItem = dataSnapshot.getValue(EventItem.class);
                        participatedEventList.add(eventItem);
                    }
                }
                participatedEventListAdapter.notifyDataSetChanged();

                if (participatedEventList.size() == 0) {
                    noParticipatedEvent.setVisibility(View.VISIBLE);
                } else {
                    noParticipatedEvent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}