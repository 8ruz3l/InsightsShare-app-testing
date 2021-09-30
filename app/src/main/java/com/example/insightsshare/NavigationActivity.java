package com.example.insightsshare;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Set up the bottom navigation menu
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}