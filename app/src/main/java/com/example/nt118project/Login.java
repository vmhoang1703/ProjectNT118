package com.example.nt118project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private ImageView back;
    private TextView backText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent backIntent = new Intent(Login.this, Welcome.class);
                startActivity(backIntent);
            }
        });

        backText = findViewById(R.id.back_text);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent backIntent = new Intent(Login.this, Welcome.class);
                startActivity(backIntent);
            }
        });

    }
}