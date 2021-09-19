package com.example.insightsshare.ui.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.insightsshare.databinding.FragmentEventsCalendarBinding;

public class EventsCalendarFragment extends Fragment {

    private FragmentEventsCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventsCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}