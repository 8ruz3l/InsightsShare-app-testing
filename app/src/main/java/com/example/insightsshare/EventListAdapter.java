package com.example.insightsshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.maxParticipants.setText(eventItem.getMaxParticipants());

        holder.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EventDetailsActivity.class);
                i.putExtra("eventId", eventItem.getEventId());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        CardView eventCard;

        TextView eventName, eventCreator, eventCreationDate, eventPlace, eventDate,
                maxParticipants;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventCard = (CardView) itemView.findViewById(R.id.event_card);

            eventName = itemView.findViewById(R.id.event_name);
            eventCreator = itemView.findViewById(R.id.event_creator);
            eventCreationDate = itemView.findViewById(R.id.event_creation_date);
            eventPlace =  itemView.findViewById(R.id.event_place);
            eventDate = itemView.findViewById(R.id.event_date);
            maxParticipants = itemView.findViewById(R.id.event_max_participants);
        }
    }
}
