package com.example.projectnt118;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.signupUsernameEditText);
        passwordEditText = findViewById(R.id.signupPasswordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button registeredButton = findViewById(R.id.registeredButton);

        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = dbHelper.addUser(username, password);
                if (isInserted) {
                    Toast.makeText(SignupActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Username is already taken", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registeredButton.setOnClickListener(view -> {
            // Move to Log In Screen (LoginActivity)
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
