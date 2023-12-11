package com.example.nt118project;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.nt118project.response.LoginResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    private boolean isEnglish = true;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set up the alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // Set the alarm to start immediately and repeat every hour
        long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        setContentView(R.layout.activity_welcome);
//        setupLanguageButton(R.id.languageBtn);
        ImageButton languageButton = findViewById(R.id.languageButton);
        TextView googleSignin = findViewById(R.id.googleSignIn);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Khởi tạo các TextView còn thiếu
        TextView emailSignin = findViewById(R.id.email_signin);
        TextView signup = findViewById(R.id.linktoregister);
        TextView signinText = findViewById(R.id.sigin_text);
        TextView welcomeText = findViewById(R.id.welcome_text);
        TextView googleSignInText = findViewById(R.id.googleSignIn);
        TextView accountSignInText = findViewById(R.id.email_signin);
        TextView orText = findViewById(R.id.or_text);
        TextView linkToRegister = findViewById(R.id.linktoregister);
        ImageView logoGG = findViewById(R.id.logogoogle);

        TextView signIn = findViewById(R.id.email_signin);
        TextView signUp = findViewById(R.id.linktoregister);


        signIn.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
        });

        signUp.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, RegisterActivity.class);
            startActivity(intent1);
        });
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảm bảo rằng ngôn ngữ được chuyển đổi
                isEnglish = !isEnglish;

                // Thay đổi ngôn ngữ toàn cục
                String languageCode = isEnglish ? "en" : "vi";
                setLocale(languageCode);

            }
        });

//        resetPwd.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ResetPwdActivity.class);
//            startActivity(intent);
//        });

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        //Request admin token
        Call<LoginResponse> call = apiInterface.login("openremote", "admin", "admin", "password");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    //Save admin token
                    assert response.body() != null;
                    SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("admin_token", response.body().getAccess_token());
                    editor.apply();
                } else {
                    Toast.makeText(MainActivity.this, R.string.connErr, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, R.string.networkErr, Toast.LENGTH_SHORT).show();
            }
        });
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
                textView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        textView.startAnimation(animation);
    }

    private void fadeImageView(final ImageView textView, int animResource, final int imgtResource) {
        Animation animation = AnimationUtils.loadAnimation(this, animResource);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setImageResource(imgtResource);
                textView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        textView.startAnimation(animation);
    }
}
