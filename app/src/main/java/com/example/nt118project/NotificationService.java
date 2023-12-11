package com.example.nt118project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.nt118project.response.Asset.Asset;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;

import retrofit2.Call;

public class NotificationService extends Service {
    private static final String NOTIFICATION_CHANNEL_ID = "default";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isLoggedIn", false)) {
            return START_STICKY;
        }
        String token = sharedPreferences.getString("user_token", null);
        Call<Asset> call = apiInterface.getAsset1("4lt7fyHy3SZMgUsECxiOgQ","Bearer " + token);
        call.enqueue(new retrofit2.Callback<Asset>() {
            @Override
            public void onResponse(@NonNull Call<Asset> call, @NonNull retrofit2.Response<Asset> response) {
                assert response.body() != null;
                Asset asset = response.body();
                double aqi = asset.getAttributes().calculateAQI();
                sendNotification("AQI: " + aqi + " - " + com.example.nt118project.util.Util.getAirQualityLevel(aqi), NotificationService.this);
            }

            @Override
            public void onFailure(@NonNull Call<Asset> call, @NonNull Throwable t) {

            }
        });
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification(String content, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create NotificationChannel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Default", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Create intent for notification
        Intent notificationIntent = new Intent(context, MainActivity.class); // Replace with the appropriate activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Data fetched")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher) // Replace with your own icon
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
