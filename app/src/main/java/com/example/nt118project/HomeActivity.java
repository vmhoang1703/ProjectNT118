package com.example.nt118project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.map.MapFragment;
import com.example.nt118project.response.AssetResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    TextView home_username;
    public static String token;
    TextView temperatureNumber, temperatureFigure, rainfallFigure, humidityFigure, windDirectionFigure, windSpeedFigure, placeText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent current_intent = getIntent();
        HomeActivity.token = current_intent.getStringExtra("access_token");

        home_username = findViewById(R.id.usernameText);
        temperatureNumber = findViewById(R.id.temperatureNumber);
        temperatureFigure = findViewById(R.id.temperatureFigure);
        humidityFigure = findViewById(R.id.humidityFigure);
        rainfallFigure = findViewById(R.id.rainfallFigure);
        windDirectionFigure = findViewById(R.id.windDirectionFigure);
        windSpeedFigure = findViewById(R.id.windSpeedFigure);
        placeText = findViewById(R.id.placeText);
        RelativeLayout mapNavi = findViewById(R.id.mapNavi);
        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        RelativeLayout graphNavi = findViewById(R.id.graphNavi);
        RelativeLayout personalNavi = findViewById(R.id.personalNavi);

        // Set OnClickListener for the "Map" icon


        SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
        home_username.setText(sharedPreferences.getString("username", ""));

        String userToken = sharedPreferences.getString("user_token", "");
        // Lấy assetId từ SharedPreferences hoặc từ nơi bạn lưu trữ nó
        String assetId = "5zI6XqkQVSfdgOrZ1MyWEf";

        // Gọi API để lấy dữ liệu temperature
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AssetResponse> call = apiInterface.getAsset(assetId,"Bearer " + userToken);
        call.enqueue(new Callback<AssetResponse>() {
            @Override
            public void onResponse(@NonNull Call<AssetResponse> call, @NonNull Response<AssetResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    AssetResponse.Attributes attributes = response.body().getAttributes();

                    // Hiển thị temperature ở Sum
                    double temperatureValue = attributes.getTemperature().getValue();
                    String formattedTemperature = String.format("%.2f", temperatureValue);
                    temperatureNumber.setText(formattedTemperature);

                    // Hiển thị temperature
                    temperatureFigure.setText(formattedTemperature);

                    // Hiển thị rainfall
                    double rainfallValue = attributes.getRainfall().getValue();
                    String formattedRainfall = String.format("%.2f", rainfallValue);
                    rainfallFigure.setText(formattedRainfall);

                    // Hiển thị humidity
                    int humidityValue = attributes.getHumidity().getValue();
                    String formattedHumidity = String.valueOf(humidityValue);
                    humidityFigure.setText(formattedHumidity);

                    // Hiển thị wind direction
                    int windDirectionValue = attributes.getWindDirection().getValue();
                    String formattedWindDirection = String.valueOf(windDirectionValue);
                    windDirectionFigure.setText(formattedWindDirection);

                    // Hiển thị wind speed
                    double windSpeedValue = attributes.getWindSpeed().getValue();
                    String formattedWindSpeed = String.format("%.2f", windSpeedValue);
                    windSpeedFigure.setText(formattedWindSpeed);

                    //Hiển thị Place
                    String placeStringValue = attributes.getPlace().getValue();
                    placeText.setText(placeStringValue);

                } else {
                    // Xử lý khi có lỗi hoặc response code không phải 200
                    temperatureNumber.setText("N/A");
                    rainfallFigure.setText("N/A");
                    humidityFigure.setText("N/A");
                    windDirectionFigure.setText("N/A");
                    windSpeedFigure.setText("N/A");
                    placeText.setText("N/A");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AssetResponse> call, @NonNull Throwable t) {
                // Xử lý khi có lỗi
                temperatureNumber.setText("N/A");
            }
        });


        RelativeLayout graphNavi  = findViewById(R.id.graphNavi);
        graphNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GraphActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });

        RelativeLayout personalNavi  = findViewById(R.id.personalNavi);
        personalNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                intent.putExtra("user_token", userToken);

        mapNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MapActivity when the "Map" icon is clicked
                Intent intent = new Intent(HomeActivity.this, MapFragment.class);
                startActivity(intent);
            }
        });
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MapActivity when the "Map" icon is clicked
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
