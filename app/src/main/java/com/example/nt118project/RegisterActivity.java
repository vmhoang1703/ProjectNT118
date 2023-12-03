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
    private ImageButton languageButton;

    private TextView backText;
    public EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private TextView signupBtn;
    private TextView welcomeText, signupText, usernameText, emailText, passwordText, cfPasswordText, typeHintText, signupUppreText;
    private boolean isEnglish = true;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        setupLanguageButton(R.id.languageBtn);
        languageButton = findViewById(R.id.languageButton);
        welcomeText = findViewById(R.id.welcome_text);
        signupText = findViewById(R.id.signup_text);
        usernameText = findViewById(R.id.textViewUsername);
        emailText = findViewById(R.id.textViewEmail);
        passwordText = findViewById(R.id.textViewPassword);
        cfPasswordText = findViewById(R.id.textViewConfirmPassword);
        signupUppreText = findViewById(R.id.signup_btn);

        //Back button
        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(view -> {
            finish();
        });
        backText = findViewById(R.id.back_text);
        backText.setOnClickListener(view -> {
            finish();
        });

        //Input data
        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        pwd = findViewById(R.id.editTextPassword);
        rePwd = findViewById(R.id.editTextConfirmPassword);

        //Sign up button
        signUp = findViewById(R.id.signup_btn);
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(this, RegisterEmbed.class);

            intent.putExtra("username", username.getText().toString().trim());
            intent.putExtra("email", email.getText().toString().trim());
            intent.putExtra("password", pwd.getText().toString().trim());
            intent.putExtra("rePassword", rePwd.getText().toString().trim());

            startActivity(intent);
        });
                // Đặt sự kiện khi nhấn vào nút ngôn ngữ
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảm bảo rằng ngôn ngữ được chuyển đổi
                isEnglish = !isEnglish;

                // Thay đổi ngôn ngữ toàn cục
                String languageCode = isEnglish ? "en" : "vi";
                setLocale(languageCode);

                updateUI();
            }
        });
        mAuth = FirebaseAuth.getInstance();



        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        signupBtn = findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, confirmPassword;
                username = String.valueOf(editTextUsername.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                confirmPassword = String.valueOf(editTextConfirmPassword.getText());


                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Đăng ký thành công
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegisterActivity.this, "Account created.", Toast.LENGTH_SHORT).show();
                                    // Chuyển sang màn hình chính hoặc màn hình đăng nhập
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Đăng ký thất bại
                                    Exception e = task.getException();
                                    Toast.makeText(RegisterActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        // Gọi hàm để đặt ngôn ngữ mặc định
        updateUI();
    }
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void updateUI() {
        // Hình ảnh của cờ mới
        Drawable[] layers = new Drawable[2];
        layers[0] = languageButton.getDrawable();
        layers[1] = getResources().getDrawable(isEnglish ? R.drawable.english : R.drawable.vietnamese);

        TransitionDrawable transitionDrawable = new TransitionDrawable(layers);
        languageButton.setImageDrawable(transitionDrawable);

        // Chạy hiệu ứng crossfade cho ImageButton
        transitionDrawable.startTransition(400); // 500 milliseconds (adjust as needed)

        // Ẩn và hiển thị các TextView với animation crossfade
        if (isEnglish) {
            fadeView(backText, R.anim.fade_out, R.string.back_en);
            fadeView(welcomeText, R.anim.fade_out, R.string.welcome_text_en);
            fadeView(signupText, R.anim.fade_out, R.string.signup_text_en);
            fadeView(usernameText, R.anim.fade_out, R.string.username_text_en);
            fadeView(emailText, R.anim.fade_out, R.string.email_text);
            fadeView(passwordText, R.anim.fade_out, R.string.password_text_en);
            fadeView(cfPasswordText, R.anim.fade_out, R.string.confirm_password_text_en);
            fadeView(signupUppreText, R.anim.fade_out, R.string.signup_upper_en);
        } else {
            fadeView(backText, R.anim.fade_out, R.string.back_vn);
            fadeView(welcomeText, R.anim.fade_out, R.string.welcome_text_vn);
            fadeView(signupText, R.anim.fade_out, R.string.signup_text_vn);
            fadeView(usernameText, R.anim.fade_out, R.string.username_text_vn);
            fadeView(emailText, R.anim.fade_out, R.string.email_text);
            fadeView(passwordText, R.anim.fade_out, R.string.password_text_vn);
            fadeView(cfPasswordText, R.anim.fade_out, R.string.confirm_password_text_vn);
            fadeView(signupUppreText, R.anim.fade_out, R.string.signup_upper_vn);
        }
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
}



