package com.example.insightsshare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
    EditText eventName, eventDescription, eventPlace, maxParticipants;
    Button ButtonSave;

    //connection with DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventplanning5);
      
        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Methode to change Date with the Datepicker
        initDatePicker();
        //Methode to change Time with the Timepicker
        initTimePicker();

        //Hooks to the xml Elements
        eventName= findViewById(R.id.InputEventName5);
        eventDescription= findViewById(R.id.InputDescription5);
        datePickerButton= findViewById(R.id.buttonDatePicker5);
        timePickerButton= findViewById(R.id.buttonTimePicker5);
        eventPlace= findViewById(R.id.InputLocation5);
        maxParticipants = findViewById(R.id.InputMaxParticipants5);
        ButtonSave= findViewById(R.id.ButtonSave5);

        datePickerButton.setText(getTodaysDate());

        //save Data in DB on Buttonclick
        //end of onClick
        ButtonSave.setOnClickListener(view -> {
            rootNode= FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app/");
            reference= rootNode.getReference().child("Event");

            //get all the values of the data (input) in stings so it can be stored
            String ValueEventId = reference.push().getKey();
            String ValueEventName= eventName.getEditableText().toString();
            String ValueEventDescription= eventDescription.getEditableText().toString();
            String ValueDate= datePickerButton.getText().toString();
            String ValueTime= timePickerButton.getText().toString();
            String ValuePlace= eventPlace.getEditableText().toString();
            String ValueParticipant= maxParticipants.getEditableText().toString();
            String todayStr= getTodaysDate();
            String ValueEventCreator= "me"; //TODO:change mockdata to real automatically shown name

            //here the data is collected (to be send to the DB in the next step)
            EventItem eventEntry = new EventItem(ValueEventId, ValueEventName, ValueEventDescription, ValueEventCreator, todayStr, ValuePlace, ValueDate, ValueTime, ValueParticipant);//, ValuePublish);

            //data is stored in the DB
            assert ValueEventId != null;
            reference.child(ValueEventId).setValue(eventEntry); //PrimaryKey is ValueEventId

            //Automatically redirect user to NavigationActivity
            onBackPressed();

            //display a little success-message, so that the user knows the data was saved
            Toast.makeText(EventplanningActivity5.this,"Erfolgreich gespeichert!",Toast.LENGTH_SHORT).show();

        });     //end of setOnClickListener
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