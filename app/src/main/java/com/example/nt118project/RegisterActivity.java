package com.example.nt118project;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class RegisterActivity extends BaseActivity {
    private ImageView back;
    private TextView signUp;
    private EditText username;
    private EditText email;
    private EditText pwd;
    private EditText rePwd;
    private TextView backText;
    private TextView signupBtn;
    private TextView typeHintText;
//    private boolean isEnglish = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        setupLanguageButton(R.id.languageBtn);
        ImageButton languageButton = findViewById(R.id.languageButton);
        ImageView cloud = findViewById(R.id.cloud);
        TextView welcomeText = findViewById(R.id.welcome_text);
        TextView usernameText = findViewById(R.id.textViewUsername);
        TextView emailText = findViewById(R.id.textViewEmail);
        TextView passwordText = findViewById(R.id.textViewPassword);
        TextView cfPasswordText = findViewById(R.id.textViewConfirmPassword);


        //Back button
        back= findViewById(R.id.back_arrow);
        back.setOnClickListener(v -> handleBackButtonClick());
        backText= findViewById(R.id.back_text);
        backText.setOnClickListener(v -> handleBackButtonClick());

        //Input data
        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        pwd = findViewById(R.id.editTextPassword);
        rePwd = findViewById(R.id.editTextConfirmPassword);

        //Sign up button
        signUp = findViewById(R.id.signup_btn);
        signUp.setOnClickListener(view -> {
            if(username.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty() || pwd.getText().toString().trim().isEmpty() || rePwd.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Fill in the blank", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.setClass(this, RegisterEmbed.class);

            intent.putExtra("username", username.getText().toString().trim());
            intent.putExtra("email", email.getText().toString().trim());
            intent.putExtra("password", pwd.getText().toString().trim());
            intent.putExtra("rePassword", rePwd.getText().toString().trim());

            startActivity(intent);
        });
                // Đặt sự kiện khi nhấn vào nút ngôn ngữ
//        languageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Đảm bảo rằng ngôn ngữ được chuyển đổi
//                isEnglish = !isEnglish;
//
//                // Thay đổi ngôn ngữ toàn cục
//                String languageCode = isEnglish ? "en" : "vi";
//                setLocale(languageCode);
//
//
//            }
//        });

    }
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }


    private void fadeView(final TextView textView, int animResource, final int textResource) {
        Animation animation = AnimationUtils.loadAnimation(this, animResource);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(textResource);
                textView.startAnimation(AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.fade_in));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        textView.startAnimation(animation);
    }
    private void handleBackButtonClick() {
        finish();
    }
}



