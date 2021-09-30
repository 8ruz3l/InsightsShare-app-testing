package com.example.insightsshare;

import android.content.Context;
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
        this.eventList = list;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventItem eventItem = eventList.get(position);
        holder.eventName.setText(eventItem.getEventName());
        holder.eventCreator.setText(eventItem.getEventCreator());
        holder.eventCreationDate.setText(eventItem.getEventCreationDate());
        holder.eventPlace.setText(eventItem.getEventPlace());
        holder.eventDate.setText(eventItem.getEventDate());
        holder.eventParticipant.setText(eventItem.getEventParticipant());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventName, eventCreator, eventCreationDate, eventPlace, eventDate,
                eventParticipant;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            eventCreator = itemView.findViewById(R.id.event_creator);
            eventCreationDate = itemView.findViewById(R.id.event_creation_date);
            eventPlace =  itemView.findViewById(R.id.event_place);
            eventDate = itemView.findViewById(R.id.event_date);
            eventParticipant = itemView.findViewById(R.id.event_max_participant);
        }
    }
}
