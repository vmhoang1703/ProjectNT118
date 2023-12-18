package com.example.nt118project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.response.Asset.Asset;
import com.example.nt118project.response.AssetResponse;
import com.example.nt118project.response.MapResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;
import com.google.gson.Gson;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    String userToken;
    private MapView mapView;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            userToken = intent.getStringExtra("user_token");
        }
        setContentView(R.layout.activity_map);
        RelativeLayout mapNavi = findViewById(R.id.mapNavi);
        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        RelativeLayout graphNavi = findViewById(R.id.graphNavi);
        RelativeLayout personalNavi = findViewById(R.id.personalNavi);
        graphNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, GraphActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });

        mapNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MapActivity when the "Map" icon is clicked
                Intent intent = new Intent(MapActivity.this, MapActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the MapActivity when the "Map" icon is clicked
                Intent intent = new Intent(MapActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        personalNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ProfileActivity.class);
                intent.putExtra("user_token", userToken);
                startActivity(intent);
            }
        });
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));


        // Create a MapView
        mapView = findViewById(R.id.map);

        // Set the tile source
        mapView.setMultiTouchControls(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        HandleMapResponse();
    }

    private void HandleMapResponse() {
        if (userToken.isEmpty()) {
            Log.e("MapActivity", "User token is empty");
            Toast.makeText(this, "Token người dùng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MapResponse> call = apiInterface.getMap("Bearer " + userToken);
        call.enqueue(new Callback<MapResponse>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(@NonNull Call<MapResponse> call, @NonNull Response<MapResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    Gson gson = new Gson();
                    MapResponse mapResponse = gson.fromJson(gson.toJson(response.body()), MapResponse.class);

                    // Log the received data for debugging
                    Log.d("MapActivity", "Received MapResponse: " + gson.toJson(mapResponse));

                    if (mapResponse.getOptions() != null) {
                        MapResponse.Options.Default defaultOptions = mapResponse.getOptions().getDefault();
                        if (defaultOptions != null && defaultOptions.getCenter() != null ) {

                            // Log intermediate values for debugging
                            Log.d("MapActivity", "Center: " + defaultOptions.getCenter());
                            Log.d("MapActivity", "Bounds: " + defaultOptions.getBounds());
                            Log.d("MapActivity", "Zoom: " + defaultOptions.getZoom());
                            // Update the map
                            List<Double> center = defaultOptions.getCenter();
//                            BoundingBox boundingBox = new BoundingBox(
//                                    defaultOptions.getBounds().get(1),  // south
//                                    defaultOptions.getBounds().get(0),  // west
//                                    defaultOptions.getBounds().get(3),  // north
//                                    defaultOptions.getBounds().get(2)   // east
//                            );
                            GeoPoint geoPoint = new org.osmdroid.util.GeoPoint(center.get(1), center.get(0));
                            mapView.getController().setZoom(defaultOptions.getZoom());
                            mapView.getController().setCenter(geoPoint);
//                            mapView.zoomToBoundingBox(boundingBox, true);

                            // Add a marker at the specified location
                            Marker marker = new Marker(mapView);
                            int iconResource = R.drawable.weather_partly_cloudy;
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable icon = getResources().getDrawable(iconResource);
                            marker.setImage(icon);
                            marker.setPosition(geoPoint);
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            marker.setIcon(getDrawable(R.drawable.weather_partly_cloudy));
                            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker, MapView mapView) {
                                    try {
                                        showDefaultWeatherMarker(mapView,marker.getTitle());
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return false;
                                }
                            });
                            mapView.getOverlays().add(marker);
                            mapView.invalidate();
                        } else {
                            Log.e("MapActivity", "Invalid center data or options");
                        }
                    } else {
                        Log.e("MapActivity", "Invalid or null map response");
                    }
                } else {
                    Log.e("MapActivity", "Error response from API: " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MapResponse> call, @NonNull Throwable t) {
                Log.e("MapActivity", "API call failed: " + t.getMessage());
            }
        });
    }
    void showDefaultWeatherMarker(View current_view, String title) throws InterruptedException {
        if (userToken.isEmpty()) {
            Log.e("MapActivity", "User token is empty");
            Toast.makeText(this, "Token người dùng trống!", Toast.LENGTH_SHORT).show();
            return;
        }
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AssetResponse> call = apiInterface.getAsset("5zI6XqkQVSfdgOrZ1MyWEf","Bearer " + userToken);
        call.enqueue(new Callback<AssetResponse>() {
            @Override
            public void onResponse(@NonNull Call<AssetResponse> call, @NonNull Response<AssetResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    AssetResponse.Attributes asset = response.body().getAttributes();
                    Dialog dialog = new Dialog(current_view.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.weather_asset_marker);
                    Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    TextView title_textview = dialog.findViewById(R.id.txtview_title);
                    TextView humidity_value = dialog.findViewById(R.id.txtview_humidity_value);
                    TextView manufacturer = dialog.findViewById(R.id.txtview_manufacturer_value);
                    TextView place = dialog.findViewById(R.id.txtview_place_value);
                    TextView rainfall_value = dialog.findViewById(R.id.txtview_rainfall_value);
                    TextView sun_altitude = dialog.findViewById(R.id.txtview_sunaltitude_value);
                    TextView sun_zenith = dialog.findViewById(R.id.txtview_zenith_value);
                    TextView tags = dialog.findViewById(R.id.txtview_tags_value);
                    TextView temp_value = dialog.findViewById(R.id.txtview_temperature_value);
                    TextView uv_index = dialog.findViewById(R.id.txtview_uvindex_value);
                    TextView wind_direction = dialog.findViewById(R.id.txtview_winddirection_value);
                    TextView wind_speed = dialog.findViewById(R.id.txtview_windspeed_value);


                    manufacturer.setText(String.valueOf(asset.getManufacturer().getValue()));

                    // Hiển thị temperature ở Sum
                    double temperatureValue = asset.getTemperature().getValue();
                    String formattedTemperature = String.format("%.2f", temperatureValue);
                    temp_value.setText(formattedTemperature);


                    // Hiển thị rainfall
                    double rainfallValue = asset.getRainfall().getValue();
                    String formattedRainfall = String.format("%.2f", rainfallValue);
                    rainfall_value.setText(formattedRainfall);

                    // Hiển thị humidity
                    int humidityValue = asset.getHumidity().getValue();
                    String formattedHumidity = String.valueOf(humidityValue);
                    humidity_value.setText(formattedHumidity);

                    // Hiển thị wind direction
                    int windDirectionValue = asset.getWindDirection().getValue();
                    String formattedWindDirection = String.valueOf(windDirectionValue);
                    wind_direction.setText(formattedWindDirection);

                    // Hiển thị wind speed
                    double windSpeedValue = asset.getWindSpeed().getValue();
                    String formattedWindSpeed = String.format("%.2f", windSpeedValue);
                    wind_speed.setText(formattedWindSpeed);

                    //Hiển thị Place
                    String placeStringValue = asset.getPlace().getValue();
                    place.setText(placeStringValue);


                    title_textview.setText(title);
                    dialog.show();

                } else {
                    Log.e("MapActivity", "Error response from API: " + response.message());
                }

            }
            @Override
            public void onFailure(@NonNull Call<AssetResponse> call, @NonNull Throwable t) {
                Log.e("MapActivity", "API call failed: " + t.getMessage());
            }
        });

    }
}
