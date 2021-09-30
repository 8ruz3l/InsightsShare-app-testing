package com.example.insightsshare;

public class EventHelperClass {

    String EventName, Date, Time, Location, MaxParticipants; //Publish ;

    public EventHelperClass() {
    }

    public EventHelperClass(String eventName, String date, String time, String location, String maxParticipants){//, String publish) {
        this.EventName = eventName;
        this.Date = date;
        this.Time = time;
        this.Location = location;
        this.MaxParticipants = maxParticipants;
        //this.Publish= publish;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMaxParticipants() {
        return MaxParticipants;
    }

    public void setMaxParticipants(String maxParticipants) {
        MaxParticipants = maxParticipants;
    }

    //public String getPublish() {
       // return Publish;
   // }

    //public void setPublish(String publish) {
        //Publish = publish;
    //}
}
