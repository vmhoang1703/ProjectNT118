package com.example.projectnt118.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnt118.R;

public class GraphActivity extends AppCompatActivity {

    private Spinner metricSpinner;
    private RadioGroup timeRangeRadioGroup;
    private Button plotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        metricSpinner = findViewById(R.id.metricSpinner);
        timeRangeRadioGroup = findViewById(R.id.timeRangeRadioGroup);
        plotButton = findViewById(R.id.plotButton);

        // Initialize the spinner with metric options
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
           //     this, R.array.metric_options, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //metricSpinner.setAdapter(adapter);

        // Set a listener for the spinner to handle metric selection
        metricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedMetric = adapterView.getItemAtPosition(position).toString();
                // Handle metric selection here
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle nothing selected
            }
        });

        // Set a listener for the plot button
        plotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle plot button click
                int selectedRadioId = timeRangeRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioId);

                String selectedTimeRange = selectedRadioButton.getText().toString();
                // Handle time range selection and plotting here

                // Example: Display a toast message
                Toast.makeText(GraphActivity.this, "Plotting data for " + selectedTimeRange, Toast.LENGTH_SHORT).show();
            }
        });
    }
}