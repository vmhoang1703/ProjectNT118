package com.example.projectnt118.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnt118.R;

public class PrepareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        Button languageButton = findViewById(R.id.languageButton);
        Button signInButton = findViewById(R.id.signInButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button continueWithGoogleButton = findViewById(R.id.continueWithGoogleButton);
        Button forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Set click listeners for the buttons
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle language change here
                // You can open a language selection dialog or activity
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-in button click
                // Start the sign-in activity
                Intent signInIntent = new Intent(PrepareActivity.this, LoginActivity.class);
                startActivity(signInIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-up button click
                // Start the sign-up activity
                Intent signUpIntent = new Intent(PrepareActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
            }
        });

        continueWithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "Continue with Google" button click
                // You can implement Google Sign-In here
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "Forgot Password?" button click
                // You can open a forgot password activity or dialog
            }
        });
    }
}