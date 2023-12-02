package com.example.nt118project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.widget.TextView;

import com.example.nt118project.Adapter.CustomSpinnerAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    private TextView startingDateTextView;
    private TextView endingDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        RelativeLayout personalNavi = findViewById(R.id.personalNavi);
        personalNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, UserInformationActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout startingDate = findViewById(R.id.startingDate);
        startingDateTextView = findViewById(R.id.startingDateTextView);
        startingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startingDateTextView);
            }
        });

        RelativeLayout endingDate = findViewById(R.id.endingDate);
        endingDateTextView = findViewById(R.id.endingDateTextView);
        endingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endingDateTextView);
            }
        });

        // Initialize Spinner
        Spinner attributesSpinner = findViewById(R.id.attributesSpinner);
        List<String> attributesList = Arrays.asList(getResources().getStringArray(R.array.attributes_array));
        CustomSpinnerAdapter attributesAdapter = new CustomSpinnerAdapter(
                this,
                android.R.layout.simple_spinner_item,
                attributesList
        );
        attributesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attributesSpinner.setAdapter(attributesAdapter);

// Set up a listener for the Spinner to handle the selected attribute
        attributesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item
                String selectedAttribute = parentView.getItemAtPosition(position).toString();
                if (!selectedAttribute.equals("Select Attribute")) {
                    // Handle the selected item
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Initialize Spinner
        Spinner timeframeSpinner = findViewById(R.id.timeframeSpinner);
        List<String> timeframeList = Arrays.asList(getResources().getStringArray(R.array.timeframe_array));
        CustomSpinnerAdapter timeframeAdapter = new CustomSpinnerAdapter(
                this,
                android.R.layout.simple_spinner_item,
                timeframeList
        );
        timeframeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeframeSpinner.setAdapter(timeframeAdapter); // Corrected line

// Set up a listener for the Spinner to handle the selected gender
        timeframeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedTimeframe = parentView.getItemAtPosition(position).toString();
                if (!selectedTimeframe.equals("Select Timeframe")) {
                    // Handle the selected item
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

    }

    private void showDatePickerDialog(TextView date) {
        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog and set the current date as the default
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the TextView
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        date.setText(selectedDate);
                        date.setTextColor(Color.parseColor("#000000"));

                    }
                },
                year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}