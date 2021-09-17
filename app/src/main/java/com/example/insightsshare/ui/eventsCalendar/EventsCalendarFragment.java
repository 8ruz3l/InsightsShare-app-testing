package com.example.insightsshare.ui.eventsCalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.insightsshare.databinding.FragmentEventsCalendarBinding;

public class EventsCalendarFragment extends Fragment {

    private EventsCalendarViewModel notificationsViewModel;
    private FragmentEventsCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(EventsCalendarViewModel.class);

        binding = FragmentEventsCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}