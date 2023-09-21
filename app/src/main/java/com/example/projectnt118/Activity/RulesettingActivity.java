package com.example.projectnt118.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnt118.R;

public class RulesettingActivity extends AppCompatActivity {

    private EditText rule1EditText;
    private EditText rule2EditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rulesetting);

        rule1EditText = findViewById(R.id.rule1EditText);
        rule2EditText = findViewById(R.id.rule2EditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle save button click
                String rule1 = rule1EditText.getText().toString();
                String rule2 = rule2EditText.getText().toString();

                // Save the rules to your data storage (e.g., SharedPreferences)
                // You can also implement validation and error handling here

                // Example: Display a toast message upon saving
                if (!rule1.isEmpty() && !rule2.isEmpty()) {
                    // Rules are not empty, proceed to save
                    // Replace with your actual save logic
                    saveRulesToStorage(rule1, rule2);
                } else {
                    // Display an error message if rules are empty
                    // Replace with your error handling logic
                    showErrorToast("Rules cannot be empty.");
                }
            }
        });
    }

    // Example method to save rules to storage (e.g., SharedPreferences)
    private void saveRulesToStorage(String rule1, String rule2) {
        // Implement your logic to save rules here
        // For example, you can use SharedPreferences to store rules
        // SharedPreferences.Editor editor = getSharedPreferences("RulesPrefs", MODE_PRIVATE).edit();
        // editor.putString("Rule1", rule1);
        // editor.putString("Rule2", rule2);
        // editor.apply();

        // Display a success message
        // Replace with your own success message
        showSuccessToast("Rules saved successfully.");
    }

    // Example method to display a toast message for success
    private void showSuccessToast(String message) {
        // Implement your toast message display here
    }

    // Example method to display a toast message for error
    private void showErrorToast(String message) {
        // Implement your toast message display here
    }
}