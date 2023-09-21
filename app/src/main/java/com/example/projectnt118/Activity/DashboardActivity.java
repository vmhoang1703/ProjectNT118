package com.example.projectnt118.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnt118.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView mainInfoTextView = findViewById(R.id.mainInfoTextView);
        Button tab1Button = findViewById(R.id.tab1Button);
        Button tab2Button = findViewById(R.id.tab2Button);
        Button tab3Button = findViewById(R.id.tab3Button);

        // Replace "[Username]" with the actual username
        String username = "John"; // Replace with the actual username
        usernameTextView.setText("Welcome, " + username);

        // Set click listeners for the tab buttons
        tab1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle tab 1 click
                // You can navigate to the content related to tab 1
            }
        });

        tab2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle tab 2 click
                // You can navigate to the content related to tab 2
            }
        });

        tab3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle tab 3 click
                // You can navigate to the content related to tab 3
            }
        });
    }
}