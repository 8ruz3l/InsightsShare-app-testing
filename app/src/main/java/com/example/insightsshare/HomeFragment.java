package com.example.insightsshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView eventsView;
    FirebaseDatabase database;
    DatabaseReference eventRef;
    EventListAdapter eventListAdapter;
    ArrayList<EventItem> eventList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        eventsView = view.findViewById(R.id.event_list);
        eventsView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        eventRef = database.getReference().child("Event");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        eventList = new ArrayList<>();
        eventListAdapter = new EventListAdapter(getContext(), eventList);
        eventsView.setAdapter(eventListAdapter);

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    EventItem eventItem = dataSnapshot.getValue(EventItem.class);
                    eventList.add(eventItem);
                }
                eventListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
