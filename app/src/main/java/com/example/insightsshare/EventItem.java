package com.example.insightsshare;

public class EventItem {

    public String eventId, eventName, eventDescription, eventCreator, eventCreationDate, eventPlace,
            eventDate, eventTime, maxParticipants;

    public EventItem() {
        // Required empty public constructor
     }


    public EventItem(String eventId, String eventName, String eventDescription, String eventCreator, String eventCreationDate,
                     String eventPlace, String eventDate, String eventTime, String maxParticipants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription= eventDescription;
        this.eventCreator = eventCreator;
        this.eventCreationDate = eventCreationDate;
        this.eventPlace = eventPlace;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.maxParticipants = maxParticipants;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() { return eventDescription; }

    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }

    public String getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(String eventCreator) {
        this.eventCreator = eventCreator;
    }

    public String getEventCreationDate() {
        return eventCreationDate;
    }

    public void setEventCreationDate(String eventCreationDate) { this.eventCreationDate = eventCreationDate; }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(String maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}
