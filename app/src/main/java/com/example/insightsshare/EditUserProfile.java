package com.example.insightsshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class EditUserProfile extends AppCompatActivity {
    //Toolbar elements
    ImageView backButton;

    //variables to refer to the layouts xml-elements in this class
    Button resetButton;

    private EditText bio, firstname, lastname, phoneNumber, nationality;
    //private EditText userName; eventuell später einkommentieren und outputUsername rausnehmen
    private TextView outputUsername;
    private Button buttonSave, buttonDeleteBirthday;

    //DatePicker
    private DatePickerDialog datePickerDialog;
    private Button birthday;

    //variables for the connection with the DB:
    FirebaseDatabase rootNode;
    DatabaseReference reference, userReference;

    FirebaseAuth fAuth;

    //primarykey of database/User
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //Methode to change Date with the Datepicker
        initDatePicker();

        //Hooks to the xml Elements
        //userName= findViewById(R.id.inputUserName); eventuell später einkommentieren
        outputUsername= findViewById(R.id.labelUserName);
        bio= findViewById(R.id.inputBiography);
        firstname= findViewById(R.id.inputFirstName);
        lastname= findViewById(R.id.inputLastName);
        phoneNumber=findViewById(R.id.inputPhonenumber);
        nationality=findViewById(R.id.inputNationality);
        buttonSave= findViewById(R.id.ButtonSave);
        resetButton = findViewById(R.id.resPassword);
        birthday =findViewById(R.id.buttonBirthday);
        buttonDeleteBirthday=findViewById(R.id.buttonDeleteBirthday);


        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        //Toolbar back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> onBackPressed());

        //get the DB data of the current user
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
        if (user == null) throw new AssertionError();
        userReference = rootNode.getReference().child("User").child(user.getUid());

        //get the current userinfos (to prefill the EditTextforms with the current data)
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userClass = snapshot.getValue(UserClass.class);
                //userName.setText(userClass.getUsername()); eventuell später einkommentieren
                outputUsername.setText(userClass.getUsername());
                bio.setText(userClass.getBio());
                userID= userClass.getUserID();
                firstname.setText(userClass.getFirstname());
                lastname.setText(userClass.getLastname());
                birthday.setText(userClass.getBirthday());
                phoneNumber.setText(userClass.getPhoneNumber());
                nationality.setText(userClass.getNationality());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //save/ update changed data
        buttonSave.setOnClickListener(view -> {
            /*//check if the username is there --> no anonymous creator with blank username possible
            if (userName.getText().toString().isEmpty()){
                Toast.makeText(EditUserProfile.this, R.string.toast_write_username, Toast.LENGTH_SHORT).show();
            } else {}
            eventuell später einkommentieren, dann aber den Rest des onClickListeners in die else Klammern aufnehmen!!!*/

                rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
                reference = rootNode.getReference().child("User");

                //put data into Strings to get stored in DB
                //String valueProfileName = userName.getEditableText().toString(); eventuell später einkommentieren
                String valueBio = bio.getEditableText().toString();
                String valueFirstname = firstname.getEditableText().toString();
                String valueLastname = lastname.getEditableText().toString();
                String valueBirthday = birthday.getText().toString();
                String valuePhoneNumber = phoneNumber.getEditableText().toString();
                String valueNationality = nationality.getEditableText().toString();

                //put the changeable data in a Map because this is the type in which it can be stored in: reference.child().updateChildren(!!!MAP REQUIRED!!!);
                HashMap<String, Object> UserMap = new HashMap<>();
                //UserMap.put("username", valueProfileName); eventuell später einkommentieren
                UserMap.put("bio", valueBio);
                UserMap.put("firstname", valueFirstname);
                UserMap.put("lastname", valueLastname);
                UserMap.put("birthday", valueBirthday);
                UserMap.put("phoneNumber", valuePhoneNumber);
                UserMap.put("nationality", valueNationality);

                //changed data is stored in the DB
                reference.child(userID).updateChildren(UserMap);

                //Automatically redirect user to NavigationActivity or EventDetailsActivity
                onBackPressed();

                //display a little success-message, so that the user knows the data was saved
                Toast.makeText(EditUserProfile.this, R.string.toast_profile_changed, Toast.LENGTH_SHORT).show();
        });


        resetButton.setOnClickListener(view -> {
            String eMail = user.getEmail();
            fAuth.sendPasswordResetEmail(eMail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(EditUserProfile.this, "Reset Email sent", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditUserProfile.this, "Failed to send Email", Toast.LENGTH_SHORT).show();
                }
            });
        });
        buttonDeleteBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app");
                reference = rootNode.getReference().child("User");

                HashMap<String, Object> UserMap= new HashMap<>();
                UserMap.put("birthday", "");

                reference.child(userID).updateChildren(UserMap);

                birthday.setText("");
            }
        });
    }


    //this is a methode to pick the birthday (called in OnCreate)
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener= (datePicker, year, month, day) -> {

            //so Jan is 1 instead of 0
            month=month+1;
            String date= makeDateString(day, month, year);
            birthday.setText(date);
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
}