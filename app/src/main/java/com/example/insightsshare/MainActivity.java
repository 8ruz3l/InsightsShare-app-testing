package com.example.insightsshare;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeRegistScreen(View v){
        Intent changeRegist = new Intent(this, RegistrActivity.class);
        startActivity(changeRegist);
    }

    public void anmelden(View v){

    }
}