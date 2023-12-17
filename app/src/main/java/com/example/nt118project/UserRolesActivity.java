package com.example.nt118project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.Adapter.UserRolesAdapter;
import com.example.nt118project.response.UserRoles;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRolesActivity extends AppCompatActivity {
    private List<UserRoles> listUR;
    APIInterface apiInterface;
    String userToken;
    private TextView tvName, tvDesc,tvAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_roles);
        Intent intent = getIntent();
        if (intent != null) {
            userToken = intent.getStringExtra("user_token");
        }

        tvName=findViewById(R.id.usroles_name);
        tvDesc=findViewById(R.id.usroles_description);
        tvAs=findViewById(R.id.usroles_assigned);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonArray> call = apiInterface.getUserRoles("Bearer " + userToken);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().toString();
                    Type lstType = new TypeToken<List<UserRoles>>() {}.getType();
                    listUR = JsonSupport.getListFromJsonArray(jsonString, lstType);
                    tvName.setText(listUR.get(0).name);
                    tvDesc.setText(listUR.get(0).getDescription());
                    tvAs.setText(listUR.get(0).getAssigned());

                } else {
                    Log.e("UserRolesActivity", "Error getting user roles: " + response.message());
                    Toast.makeText(UserRolesActivity.this, "Error getting user roles", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {

            }
        });

    }

}
