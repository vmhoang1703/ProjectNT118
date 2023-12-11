package com.example.nt118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nt118project.response.AssetResponse;
import com.example.nt118project.response.UserResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    String userToken;
    TextView showFirstname, showLastname, showUsername, showEmail, usernameText;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        showFirstname = findViewById(R.id.showFirstname);
        showLastname = findViewById(R.id.showLastname);
        showUsername = findViewById(R.id.showUsername);
        showEmail = findViewById(R.id.showEmail);
        usernameText = findViewById(R.id.usernameText);

        Intent intent = getIntent();
        if (intent != null) {
            userToken = intent.getStringExtra("user_token");
        }

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<UserResponse> call = apiInterface.getUser("Bearer " + userToken);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    UserResponse userResponse = response.body();
                    displayUserInfo(userResponse);
                } else {

                }
            }
            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {

            }
        });

        TextView logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void displayUserInfo(UserResponse userResponse) {
        // Display user information in your TextViews
        showFirstname.setText(userResponse.getFirstName());
        showLastname.setText(userResponse.getLastName());
        showUsername.setText(userResponse.getUsername());
        showEmail.setText(userResponse.getEmail());
        usernameText.setText(userResponse.getUsername());
    }
}