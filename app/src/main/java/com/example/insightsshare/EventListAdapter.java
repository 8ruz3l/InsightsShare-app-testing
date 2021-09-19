package com.example.insightsshare;

import android.content.Context;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    Context context;

    ArrayList<EventItem> eventList;

    public EventListAdapter(Context context, ArrayList<EventItem> list) {
        this.context = context;
        eventList = list;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventItem eventItem = eventList.get(position);
        holder.eventName.setText(eventItem.getEventName());
        holder.eventCreator.setText(eventItem.getEventCreator());
        holder.eventCreationDate.setText(eventItem.getEventCreationDate());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventName, eventCreator, eventCreationDate, eventPlace, eventDate,
                eventParticipant, eventDescription;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventCreator = itemView.findViewById(R.id.event_creator);
            eventCreationDate = itemView.findViewById(R.id.event_creation_date);
        }
    }
}
