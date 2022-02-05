package com.example.insightsshare;

public class EventItem {

    public String eventId, eventName, eventDescription, eventCreator, eventCreatorsID, eventCreationDate, eventPlace,
            eventDate, eventTime, maxParticipants;

    public int currentParticipants;

    public EventItem() {
        // Required empty public constructor
     }

    public EventItem(String eventId, String eventName, String eventDescription, String eventCreator, String eventCreatorsID, String eventCreationDate,
                     String eventPlace, String eventDate, String eventTime, int currentParticipants, String maxParticipants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription= eventDescription;
        this.eventCreator = eventCreator;
        this.eventCreatorsID= eventCreatorsID;
        this.eventCreationDate = eventCreationDate;
        this.eventPlace = eventPlace;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.currentParticipants = currentParticipants;
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

    public String getEventCreatorsID() { return eventCreatorsID; }

    public void setEventCreatorsID(String eventCreatorsID) { this.eventCreatorsID = eventCreatorsID; }

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

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public String getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(String maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}
