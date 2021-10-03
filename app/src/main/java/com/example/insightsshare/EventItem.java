package com.example.insightsshare;

public class EventItem {

    public String eventId, eventName, eventCreator, eventCreationDate, eventPlace, eventDate, eventTime, eventParticipant;

    public EventItem() {
        // Required empty public constructor
     }


    public EventItem(String eventId, String eventName, String eventCreator, String eventCreationDate,
                     String eventPlace, String eventDate, String eventTime, String eventParticipant) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventCreator = eventCreator;
        this.eventCreationDate = eventCreationDate;
        this.eventPlace = eventPlace;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventParticipant = eventParticipant;
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

    public String getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(String eventCreator) {
        this.eventCreator = eventCreator;
    }

    public String getEventCreationDate() {
        return eventCreationDate;
    }

    public void setEventCreationDate(String eventCreationDate) {
        this.eventCreationDate = eventCreationDate;
    }

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

    public String getEventParticipant() {
        return eventParticipant;
    }

    public void setEventParticipant(String eventParticipant) {
        this.eventParticipant = eventParticipant;
    }
}
