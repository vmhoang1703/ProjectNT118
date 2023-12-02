package com.example.nt118project;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    TextView home_username;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_username = findViewById(R.id.usernameText);
        SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
        ;
        home_username.setText(getString(R.string.welcome) + sharedPreferences.getString("username", ""));
    }
}
