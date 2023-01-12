package com.example.insightsshare.eventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightsshare.R;
import com.example.insightsshare.UserClass;

import java.util.ArrayList;

public class ParticipantsListAdapter extends RecyclerView.Adapter<ParticipantsListAdapter.ParticipantsViewHolder> {

    Context context;

    ArrayList<UserClass> participantsList;

    public ParticipantsListAdapter(Context context, ArrayList<UserClass> list) {
        this.context = context;
        this.participantsList = list;
    }

    @NonNull
    @Override
    public ParticipantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participant_row, parent, false);
        return new ParticipantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsViewHolder holder, int position) {
        UserClass userClass = participantsList.get(position);
        holder.username.setText(userClass.getUsername());
    }

    @Override
    public int getItemCount() {
        return participantsList.size();
    }

    public static class ParticipantsViewHolder extends RecyclerView.ViewHolder {

        CardView participantCard;

        TextView username;

        public ParticipantsViewHolder(@NonNull View itemView) {
            super(itemView);

            participantCard = (CardView) itemView.findViewById(R.id.participant_card);

            username = itemView.findViewById(R.id.username);
        }
    }
}
