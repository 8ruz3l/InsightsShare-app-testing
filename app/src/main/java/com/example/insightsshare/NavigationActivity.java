package com.example.insightsshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class NavigationActivity extends AppCompatActivity {

    // Toolbar elements
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Set up the toolbar
        // Profile picture in toolbar
        profilePic = (ImageView) findViewById(R.id.profile);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NavigationActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        // Set up the bottom navigation menu
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}