package com.example.insightsshare.ui.eventsCalendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventsCalendarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventsCalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}