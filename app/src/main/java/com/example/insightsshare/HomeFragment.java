package com.example.insightsshare;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    // Database elements
    FirebaseDatabase database;
    DatabaseReference eventRef;

    // View elements
    RecyclerView eventsView;
    TextView noEvent;

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

        // Set fragment title in toolbar
        getActivity().setTitle(R.string.title_home_toolbar);

        eventsView = view.findViewById(R.id.event_list);
        eventsView.setLayoutManager(new LinearLayoutManager(getContext()));

        noEvent = view.findViewById(R.id.no_event);

        database = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        eventRef = database.getReference().child("Event");

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

                if (eventList.size() == 0) {
                    noEvent.setVisibility(View.VISIBLE);
                } else {
                    noEvent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
