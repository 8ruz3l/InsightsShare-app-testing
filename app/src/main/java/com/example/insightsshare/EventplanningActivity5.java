package com.example.insightsshare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class EventplanningActivity5 extends AppCompatActivity {
    //Toolbar elements
    ImageView backButton;

    //Time- and DatePicker
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button datePickerButton;
    private Button timePickerButton;
    int tHour, tMin;

    //variables for transfering Eventdata to DB
    TextView eventCreator;
    EditText eventName, eventDescription, eventPlace, maxParticipants;
    Button ButtonSave;

    //variables for updating existing Eventdata
    Boolean updateExistingEvent = false; // False as default
    String existingEventID;

    //connection with DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference, userReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventplanning5);
      
        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        // Determine between event creation or edit
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        updateExistingEvent = extras.getBoolean("updateExistingEvent");
        existingEventID = extras.getString("existingEventID");

        //Methode to change Date with the Datepicker
        initDatePicker();
        //Methode to change Time with the Timepicker
        initTimePicker();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        userReference = rootNode.getReference().child("User").child(user.getUid());

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);
                eventCreator.setText(userClass.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Hooks to the xml Elements
        eventName= findViewById(R.id.InputEventName5);
        eventDescription= findViewById(R.id.InputDescription5);
        datePickerButton= findViewById(R.id.buttonDatePicker5);
        timePickerButton= findViewById(R.id.buttonTimePicker5);
        eventPlace= findViewById(R.id.InputLocation5);
        maxParticipants = findViewById(R.id.InputMaxParticipants5);
        ButtonSave= findViewById(R.id.ButtonSave5);
        eventCreator= this.findViewById(R.id.OutputReferent5);


        //fill the xml fields with the data of the existing event if (updateExistingEvent= true)
        if (updateExistingEvent==false) {
            // this is only for new Events
            datePickerButton.setText(getTodaysDate());
        } else {
            rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
            reference = rootNode.getReference().child("Event").child(existingEventID);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     EventItem eventItem = snapshot.getValue(EventItem.class);

                     eventName.setText(eventItem.getEventName());
                     eventDescription.setText(eventItem.getEventDescription());
                     eventCreator.setText(eventItem.getEventCreator());
                     datePickerButton.setText(eventItem.getEventDate());
                     timePickerButton.setText(eventItem.getEventTime());
                     eventPlace.setText(eventItem.getEventPlace());
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             }); // end of Listener
        } //end of if else


        //save/ updates Data in DB on Buttonclick
        ButtonSave.setOnClickListener(view -> {

            rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
            reference = rootNode.getReference().child("Event");

            //decides between creating a new Event or updating an existing Event
            if (updateExistingEvent == false) {
                //get all the values of the data (input) in stings so it can be stored
                String ValueEventId = reference.push().getKey();
                String ValueEventName = eventName.getEditableText().toString();
                String ValueEventDescription = eventDescription.getEditableText().toString();
                String ValueDate = datePickerButton.getText().toString();
                String ValueTime = timePickerButton.getText().toString();
                String ValuePlace = eventPlace.getEditableText().toString();
                String ValueMaxParticipants = maxParticipants.getEditableText().toString();
                String todayStr = getTodaysDate();
                String ValueEventCreator = eventCreator.getText().toString();

                //here the data is collected (to be send to the DB in the next step)
                EventItem eventEntry = new EventItem(ValueEventId, ValueEventName, ValueEventDescription,
                        ValueEventCreator, todayStr, ValuePlace, ValueDate, ValueTime, ValueMaxParticipants);//, ValuePublish);

                //data is stored in the DB
                assert ValueEventId != null;
                reference.child(ValueEventId).setValue(eventEntry); //PrimaryKey is ValueEventId
                reference.child(ValueEventId).child("participantsList").child(user.getUid()).setValue(true); // Join the event automatically
            } else {
                //update an existing Event
                //get all the existing and new values of the eventdata in stings so it can be stored
                String eventId = existingEventID;
                String ValueEventName = eventName.getEditableText().toString();
                String ValueEventCreator = eventCreator.getText().toString();
                String ValueEventDescription = eventDescription.getEditableText().toString();
                String ValueDate = datePickerButton.getText().toString();
                String ValueTime = timePickerButton.getText().toString();
                String ValuePlace = eventPlace.getEditableText().toString();
                String ValueMaxParticipants = maxParticipants.getEditableText().toString();

                //put the changeable data in a Map because this is the type in which it can be stored in: reference.child().updateChildren(!!!MAP REQUIRED!!!);
                HashMap<String, Object> EventMap= new HashMap<String, Object>();
                EventMap.put("eventName", ValueEventName);
                EventMap.put("eventCreator", ValueEventCreator);
                EventMap.put("eventDescription", ValueEventDescription);
                EventMap.put("eventDate", ValueDate);
                EventMap.put("eventTime", ValueTime);
                EventMap.put("eventPlace", ValuePlace);
                EventMap.put("maxParticipants", ValueMaxParticipants);

                //changed data is stored in the DB
                assert eventId != null;
                reference.child(eventId).updateChildren(EventMap);
            }

            //Automatically redirect user to NavigationActivity or EventDetailsActivity
            onBackPressed();

            //display a little success-message, so that the user knows the data was saved
            if(updateExistingEvent) {
                Toast.makeText(EventplanningActivity5.this, R.string.toast_event_updated, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(EventplanningActivity5.this, R.string.toast_event_created, Toast.LENGTH_SHORT).show();
            }

        });     //end of setOnClickListener for the buttonSave5
    }           //end of onCreate

    //methode for datePicker
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month=month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    //this is a methode to pick the date (called in OnCreate)
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener= (datePicker, year, month, day) -> {

            //so Jan is 1 instead of 0
            month=month+1;
            String date= makeDateString(day, month, year);
            datePickerButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog= new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        //Set transparent background
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    //this is a methode to pick the time (called in OnCreate)
    private void initTimePicker() {


        // end of onTimeSet
        TimePickerDialog.OnTimeSetListener timeSetListener= (view, hour, min) -> {
            //Initialize hour and min
            tHour = hour;
            tMin = min;

            //Store hour and min in a String
            String time = hour + ":" + min;
            //Initaltize 24h Format
            SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
            try {
                Date date = f24Hours.parse(time);
                //Initialize 12hours time format
                SimpleDateFormat f12Hours = new SimpleDateFormat("HH:mm aa");
                //Set sected Time on Button
                assert date != null;
                timePickerButton.setText(f12Hours.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }           //end of try catch
        };      //end of OnTimeSetListener

        int style = AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, 12, 0, false);

        //Set transparent background
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Display previously selected time
        timePickerDialog.updateTime(tHour, tMin);
    } //end of initTimePicker


    //for DatePicker: called in methode initDatePicker()
    private String makeDateString(int day, int month, int year) {

        return getMonthFormat(month) + " " + day +  " " +year;
    }


    //for DatePicker: called in methode makeDateString()
    private String getMonthFormat(int month) {

        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "AUG";
        if(month==8)
            return "SEP";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEZ";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    public void openTimePicker(View view){
        timePickerDialog.show();
    }
}               //end of EventplanningActivity5