package com.example.nt118project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class Welcome extends AppCompatActivity {
    private ImageButton languageButton;
    private TextView googleSignin;
    private TextView emailSignin;
    private TextView signup;
    private TextView signinText;
    private TextView welcomeText;
    private TextView googleSignInText;
    private TextView accountSignInText;
    private TextView orText;
    private TextView linkToRegister;
    private ImageView logoGG;
    private boolean isEnglish = true;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        languageButton = findViewById(R.id.languageButton);
        googleSignin = findViewById(R.id.googleSignIn);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Khởi tạo các TextView còn thiếu
        emailSignin = findViewById(R.id.email_signin);
        signup = findViewById(R.id.linktoregister);
        signinText = findViewById(R.id.sigin_text);
        welcomeText = findViewById(R.id.welcome_text);
        googleSignInText = findViewById(R.id.googleSignIn);
        accountSignInText = findViewById(R.id.email_signin);
        orText = findViewById(R.id.or_text);
        linkToRegister = findViewById(R.id.linktoregister);
        logoGG = findViewById(R.id.logogoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignin1();
            }
        });

        emailSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent loginIntent = new Intent(Welcome.this, Login.class);
                startActivity(loginIntent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent signupIntent = new Intent(Welcome.this, Register.class);
                startActivity(signupIntent);
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

    private void googleSignin1() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, "SAI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());
                            database.getReference().child("users").child(user.getUid()).setValue(map);
                            Intent intent = new Intent(Welcome.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Welcome.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            fadeView(signinText, R.anim.fade_out, R.string.signin_text_en);
            fadeView(welcomeText, R.anim.fade_out, R.string.wc_text_en);
            fadeView(googleSignInText, R.anim.fade_out, R.string.gg_signin_en);
            fadeView(accountSignInText, R.anim.fade_out, R.string.ac_signin_en);
            fadeView(orText, R.anim.fade_out, R.string.or_text_en);
            fadeView(linkToRegister, R.anim.fade_out, R.string.LinkToRegister_en);
            fadeImageView(logoGG, R.anim.fade_out, R.drawable.logogoogle);
        } else {
            fadeView(signinText, R.anim.fade_out, R.string.signin_text_vn);
            fadeView(welcomeText, R.anim.fade_out, R.string.wc_text_vn);
            fadeView(googleSignInText, R.anim.fade_out, R.string.gg_signin_vn);
            fadeView(accountSignInText, R.anim.fade_out, R.string.ac_signin_vn);
            fadeView(orText, R.anim.fade_out, R.string.or_text_vn);
            fadeView(linkToRegister, R.anim.fade_out, R.string.LinkToRegister_vn);
            fadeImageView(logoGG, R.anim.fade_out, R.drawable.logogoogle);
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
                textView.startAnimation(AnimationUtils.loadAnimation(Welcome.this, R.anim.fade_in));
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
                textView.startAnimation(AnimationUtils.loadAnimation(Welcome.this, R.anim.fade_in));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        textView.startAnimation(animation);
    }
}
