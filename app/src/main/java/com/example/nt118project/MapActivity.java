package com.example.nt118project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.response.MapResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.List;

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
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

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

        // Load osmdroid configuration
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));

        // Create a MapView
        MapView map = findViewById(R.id.map);

        // Set the tile source
        map.setTileSource(TileSourceFactory.MAPNIK);

        // Set center and zoom level based on the JSON configuration
        map.getController().setZoom(16.0);
        map.getController().setCenter(new org.osmdroid.util.GeoPoint(10.87, 106.80324));

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {

        if (userToken.isEmpty()) {
            Log.e("MapActivity", "User token is empty");
            Toast.makeText(this, "Token người dùng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MapResponse> call = apiInterface.getMap("Bearer " + userToken);
        call.enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(@NonNull Call<MapResponse> call, @NonNull Response<MapResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    // Lưu dữ liệu vào MapResponse để sử dụng sau này
                    saveMapData(response.body());

                    // Cập nhật bản đồ dựa trên dữ liệu nhận được
                    updateMap(response.body());
                } else {
                    // Xử lý lỗi hoặc mã phản hồi khác không phải là 200
                    Log.e("MapActivity", "Error response from API: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MapResponse> call, @NonNull Throwable t) {
                // Xử lý lỗi khi cuộc gọi API thất bại
                Log.e("MapActivity", "API call failed: " + t.getMessage());
            }
        });
    }

    private void saveMapData(MapResponse mapResponse) {
        if (mapResponse != null) {
            SharedPreferences.Editor editor = getSharedPreferences("MapData", MODE_PRIVATE).edit();

            MapResponse.Options.Default defaultOptions = mapResponse.getOptions().getDefault();
            if (defaultOptions != null) {
                List<Double> center = defaultOptions.getCenter();
                if (center != null && center.size() >= 2) {
                    editor.putFloat("center_latitude", center.get(1).floatValue());
                    editor.putFloat("center_longitude", center.get(0).floatValue());
                }

                editor.putInt("zoom", defaultOptions.getZoom());
            }

            editor.apply();
        }
    }

    private void updateMap(MapResponse mapResponse) {
        if (mapResponse.getOptions() != null) {
            MapResponse.Options.Default defaultOptions = mapResponse.getOptions().getDefault();
            if (defaultOptions != null && defaultOptions.getCenter() != null) {
                List<Double> center = defaultOptions.getCenter();
                GeoPoint geoPoint = new GeoPoint(center.get(1), center.get(0));
                mapView.getController().setCenter(geoPoint);
                mapView.getController().setZoom(defaultOptions.getZoom());

                mapView.invalidate();
            } else {
                Log.e("MapActivity", "Error getting map data or center is null");
            }
        }
    }
}
