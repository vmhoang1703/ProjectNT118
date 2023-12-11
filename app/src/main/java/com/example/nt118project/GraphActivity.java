package com.example.nt118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nt118project.Adapter.CustomSpinnerAdapter;

import com.example.nt118project.request.GraphRequestBody;
import com.example.nt118project.response.GraphResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class GraphActivity extends AppCompatActivity {
    private TextView startingDateTextView;
    private TextView endingDateTextView;
    public String type;
    private String userToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        RelativeLayout mapNavi = findViewById(R.id.mapNavi);
        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        RelativeLayout graphNavi = findViewById(R.id.graphNavi);
        RelativeLayout personalNavi = findViewById(R.id.personalNavi);
        graphNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, GraphActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });

        mapNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MapActivity when the "Map" icon is clicked
                Intent intent = new Intent(GraphActivity.this, MapActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MapActivity when the "Map" icon is clicked
                Intent intent = new Intent(GraphActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        personalNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, ProfileActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });

        RelativeLayout startingDate = findViewById(R.id.startingDate);
        startingDateTextView = findViewById(R.id.startingDateTextView);
        startingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(startingDateTextView);
            }
        });

        RelativeLayout endingDate = findViewById(R.id.endingDate);
        endingDateTextView = findViewById(R.id.endingDateTextView);
        endingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(endingDateTextView);
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
                    if (selectedAttribute.equals("Temperature")) {
                        type = "temperature";
                    }
                    if (selectedAttribute.equals("Humidity")) {
                        type = "humidity";
                    }
                    if (selectedAttribute.equals("Rainfall")) {
                        type = "rainfall";
                    }
                    if (selectedAttribute.equals("Wind direction")) {
                        type = "windDirection";
                    }
                    if (selectedAttribute.equals("Wind speed")) {
                        type = "windSpeed";
                    }
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

        Intent intent = getIntent();
        if (intent != null) {
            userToken = intent.getStringExtra("user_token");
        }

        TextView show_btn = findViewById(R.id.show_btn);
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestToApi();
            }
        });
    }

    private void sendRequestToApi() {
        try {
            String assetId = "5zI6XqkQVSfdgOrZ1MyWEf";
            long fromTimestamp = convertDateTimeToTimestamp(startingDateTextView.getText().toString());
            long toTimestamp = convertDateTimeToTimestamp(endingDateTextView.getText().toString());

            GraphRequestBody requestBody = new GraphRequestBody(fromTimestamp, toTimestamp, type);

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<JsonArray> call = apiInterface.postData(assetId, type, "Bearer " + userToken, requestBody);
            call.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                    if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                        String graphResponse = response.body().toString();
                        Log.d("GraphActivity", "Response: " + graphResponse);

                        try {
                            Gson gson = new Gson();
                            GraphResponse[] data = gson.fromJson(graphResponse, GraphResponse[].class);

                            // Log the parsed data for debugging
                            Log.d("GraphActivity", "Parsed Data: " + Arrays.toString(data));

                            DataPoint[] dataPoints = new DataPoint[data.length];
                            for (int i = 0; i < data.length; i++) {
                                dataPoints[i] = new DataPoint(data[i].getTimestamp(), data[i].getAttributeValue());
                            }

                            // Log the data points for debugging
                            Log.d("GraphActivity", "Data Points: " + Arrays.toString(dataPoints));

                            plotGraph(dataPoints);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("GraphActivity", "Error parsing JSON: " + e.getMessage());
                        }
                    } else {
                        Log.e("GraphActivity", "Unsuccessful response: " + response.message());
                    }
                }


                @Override
                public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDateTimePicker(final TextView dateTextView) {
        // Hiển thị DatePickerDialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        final int selectedYear = year;
                        final int selectedMonth = monthOfYear;
                        final int selectedDay = dayOfMonth;

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                GraphActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                        String selectedTime = hourOfDay + ":" + minute;

                                        dateTextView.setTextColor(Color.BLACK);
                                        dateTextView.setText(selectedDate + " " + selectedTime);
                                    }
                                },
                                hour,
                                minute,
                                true
                        );

                        timePickerDialog.show();
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    private long convertDateTimeToTimestamp(String dateTimeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateTimeString);
            assert date != null;
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void plotGraph(DataPoint[] dataPoints) {
        // Sort the DataPoint array based on x-values
        Arrays.sort(dataPoints, (point1, point2) -> Double.compare(point1.getX(), point2.getX()));

        GraphView graph = findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);

        // Create LineGraphSeries
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        // Add the series to the graph
        graph.addSeries(series);

        final Date[] prevDate = {null};
        // Customize the x-axis label formatting
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Date currentDate = new Date((long) value);
                    // Convert timestamp to Date and format as a string
                    if (prevDate[0] == null || !isSameDay(prevDate[0], currentDate)) {
                        prevDate[0] = currentDate;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
                        return dateFormat.format(currentDate);
                    } else {
                        return ""; // Empty string to skip the label if it's the same day
                    }
                } else {
                    // Format y-axis labels if needed
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        // Set manual x bounds
        double minX = dataPoints[0].getX();
        double maxX = dataPoints[dataPoints.length - 1].getX();
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.setCursorMode(true);
        graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.length);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);

    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}