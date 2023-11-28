package com.example.nt118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.nt118project.response.LoginResponse;
import com.example.nt118project.response.UserResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;

//public class LoginActivity extends AppCompatActivity {
//    private ImageButton languageButton;
//    private ImageView back;
//    private TextView backText;
//    private EditText editTextEmail, editTextPassword;
//    private TextView signinBtn;
//    private TextView welcomeBackText, signinText, usernameText, emailText, passwordText, typeHintText, signinUppreText, forgotPass;
//    private boolean isEnglish = true;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        languageButton = findViewById(R.id.languageButton);
//        welcomeBackText=findViewById(R.id.welcome_text);
//        signinText=findViewById(R.id.signin_text);
//        emailText=findViewById(R.id.textViewEmail);
//        passwordText=findViewById(R.id.textViewPassword);
//        forgotPass=findViewById(R.id.forgotpassword);
////        typeHintText=findViewById(R.id.welcome_text);
//        signinUppreText=findViewById(R.id.signin_btn);
//        mAuth = FirebaseAuth.getInstance();
//        back = findViewById(R.id.back_arrow);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Tạo Intent để mở màn hình đăng nhập
//                Intent backIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
//                startActivity(backIntent);
//            }
//        });
//
//        backText = findViewById(R.id.back_text);
//        backText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Tạo Intent để mở màn hình đăng nhập
//                Intent backIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
//                startActivity(backIntent);
//            }
//        });
//
//        // Đặt sự kiện khi nhấn vào nút ngôn ngữ
//        languageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Đảm bảo rằng ngôn ngữ được chuyển đổi
//                isEnglish = !isEnglish;
//
//                // Thay đổi ngôn ngữ toàn cục
//                String languageCode = isEnglish ? "en" : "vi";
//                LanguageManager.getInstance().changeLanguage(getResources(), languageCode);
//
//                // Khởi tạo lại Activity để áp dụng ngôn ngữ mới
//                updateUI();
//            }
//        });
//
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//
//        signinBtn = findViewById(R.id.signin_btn);
//        signinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username, email, password, confirmPassword;
//                email = String.valueOf(editTextEmail.getText());
//                password = String.valueOf(editTextPassword.getText());
//
//                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Toast.makeText(LoginActivity.this, "Login sucessfully.", Toast.LENGTH_SHORT).show();
//                                    // Chuyển sang màn hình chính hoặc màn hình đăng nhập
//                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(LoginActivity.this, "Login failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
//
//        TextView resetPassword = findViewById(R.id.forgotpassword);
//        resetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
//                startActivity(intent);
//            }
//        });
//        // Gọi hàm để đặt ngôn ngữ mặc định
//        updateUI();
//    }
//
//    private void updateUI() {
////        if (isEnglish) {
////            setLocale("en");
////        } else {
////            setLocale("vi");
////        }
//
//        // Hình ảnh của cờ mới
//        Drawable[] layers = new Drawable[2];
//        layers[0] = languageButton.getDrawable();
//        layers[1] = getResources().getDrawable(isEnglish ? R.drawable.english : R.drawable.vietnamese);
//
//        TransitionDrawable transitionDrawable = new TransitionDrawable(layers);
//        languageButton.setImageDrawable(transitionDrawable);
//
//        // Chạy hiệu ứng crossfade cho ImageButton
//        transitionDrawable.startTransition(400); // 500 milliseconds (adjust as needed)
//
//        // Ẩn và hiển thị các TextView với animation crossfade
//        if (isEnglish) {
//            fadeView(backText, R.anim.fade_out, R.string.back_en);
//            fadeView(welcomeBackText, R.anim.fade_out, R.string.welcome_back_text_en);
//            fadeView(signinText, R.anim.fade_out, R.string.signin1_text_en);
//            fadeView(emailText, R.anim.fade_out, R.string.email_text);
//            fadeView(passwordText, R.anim.fade_out, R.string.password_text_en);
//            fadeView(forgotPass, R.anim.fade_out, R.string.ForgotPassword_en);
//            fadeView(signinUppreText, R.anim.fade_out, R.string.signin_upper_en);
//        } else {
//            fadeView(backText, R.anim.fade_out, R.string.back_vn);
//            fadeView(welcomeBackText, R.anim.fade_out, R.string.welcome_back_text_vn);
//            fadeView(signinText, R.anim.fade_out, R.string.signin1_text_vn);
//            fadeView(emailText, R.anim.fade_out, R.string.email_text);
//            fadeView(passwordText, R.anim.fade_out, R.string.password_text_vn);
//            fadeView(forgotPass, R.anim.fade_out, R.string.ForgotPassword_vn);
//            fadeView(signinUppreText, R.anim.fade_out, R.string.signin_upper_vn);
//        }
//    }
//
//    private void fadeView(final TextView textView, int animResource, final int textResource) {
//        Animation animation = AnimationUtils.loadAnimation(this, animResource);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                textView.setText(textResource);
//                textView.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_in));
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        textView.startAnimation(animation);
//    }

//    private void setLocale(String languageCode) {
//        Locale locale = new Locale(languageCode);
//        Locale.setDefault(locale);
//        Configuration configuration = new Configuration();
//        configuration.setLocale(locale);
//        Resources resources = getResources();
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//    }

//}
public class LoginActivity extends BaseActivity {
    private APIInterface apiInterface;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        setupLanguageButton(R.id.languageBtn);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        username = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        ImageView back = findViewById(R.id.back_arrow);
        back.setOnClickListener(view -> {
            finish();
        });


        //SignIn
        TextView signIn = findViewById(R.id.signin_btn);
        signIn.setOnClickListener(view -> {
            Log.i("Login", "SignIn");
            String usr = username.getText().toString();
            String pwd = password.getText().toString();

            Call<LoginResponse> call = apiInterface.login("openremote", usr, pwd, "password");
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        //Store user token
                        assert response.body() != null;
                        SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_token", response.body().getAccess_token());
                        //Get user info
                        Call<UserResponse> call1 = apiInterface.getUser("Bearer " + response.body().getAccess_token());
                        call1.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                                //Store user info
                                assert response.body() != null;
                                editor.putString("user_id", response.body().getId());
                                editor.putString("email", response.body().getEmail());
                                editor.putString("realm", response.body().getRealm());
                                editor.putString("realmId", response.body().getRealmId());
                                editor.putString("username", response.body().getUsername());
                                editor.apply();
                                //Start HomeActivity
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                //Show toast
                                Toast.makeText(LoginActivity.this, R.string.loginSucc, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                                Toast.makeText(LoginActivity.this, R.string.networkErr, Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(LoginActivity.this, R.string.loginFailed, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, R.string.networkErr, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}