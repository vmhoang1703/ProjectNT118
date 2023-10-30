package com.example.nt118project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    private ImageButton languageButton;
    private TextView googleSignin;
    private TextView emailSignin;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        emailSignin = findViewById(R.id.email_signin);
        emailSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent loginIntent = new Intent(Welcome.this, Login.class);
                startActivity(loginIntent);
            }
        });

        signup = findViewById(R.id.linktoregister);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent signupIntent = new Intent(Welcome.this, Register.class);
                startActivity(signupIntent);
            }
        });
    }
}