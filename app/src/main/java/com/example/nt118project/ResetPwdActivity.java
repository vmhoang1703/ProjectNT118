package com.example.nt118project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.nt118project.response.LoginResponse;
import com.example.nt118project.response.UserResponse;
import com.example.nt118project.util.APIClient;
import com.example.nt118project.util.APIInterface;
import com.example.nt118project.util.ResetPasswordBody;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPwdActivity extends BaseActivity {
    private APIInterface apiInterface;
    private EditText username;
    private EditText oldPwd;
    private EditText newPwd;
    private EditText confirmPwd;
    private EditText editTextOldPassword, editTextNewPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
//        setupLanguageButton(R.id.languageBtn);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        //Back button
        ImageView rs_back = findViewById(R.id.back_arrow);
        rs_back.setOnClickListener(view -> {
            finish();
        });
        TextView backText = findViewById(R.id.back_text);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent backIntent = new Intent(ResetPwdActivity.this, LoginActivity.class);
                startActivity(backIntent);
            }
        });


        //Find view
//        username = findViewById(R.id.us);
//        oldPwd = findViewById(R.id.rs_oldPwd);
//        newPwd = findViewById(R.id.rs_newPwd);
//        confirmPwd = findViewById(R.id.rs_rePwd);

        TextView reset = findViewById(R.id.send_email_btn);
        reset.setOnClickListener(view -> {

            Call<LoginResponse> call = apiInterface.login("openremote", username.getText().toString(), oldPwd.getText().toString(), "password");
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(ResetPwdActivity.this, ResetEmbed.class);
                        //Pass data
                        intent.putExtra("username", username.getText().toString());
                        intent.putExtra("oldPassword", oldPwd.getText().toString());
                        intent.putExtra("newPassword", newPwd.getText().toString());
                        intent.putExtra("confirmPassword", confirmPwd.getText().toString());
                        //Get user id
                        assert response.body() != null;
                        String token = "Bearer " + response.body().getAccess_token();
                        Call<UserResponse> call1 = apiInterface.getUser(token);//Get user
                        call1.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                                //If user exist
                                if (response.isSuccessful()) {
                                    //If password match
                                    if (newPwd.getText().toString().equals(confirmPwd.getText().toString())) {
                                        //Header
                                        ResetPasswordBody body = new ResetPasswordBody(oldPwd.getText().toString(), true);
                                        SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
                                        String token = "Bearer " + sharedPreferences.getString("admin_token", null);
                                        //Send request
                                        assert response.body() != null;
                                        Call<Void> call2 = apiInterface.resetPassword(token, "application/json", "application/json", response.body().getId(), body);
                                        call2.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                                if (response.isSuccessful()) {
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                                Toast.makeText(ResetPwdActivity.this, R.string.networkErr, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(ResetPwdActivity.this, R.string.confirmErr, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                                Toast.makeText(ResetPwdActivity.this, R.string.networkErr, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ResetPwdActivity.this, R.string.errInput, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ResetPwdActivity.this, R.string.networkErr, Toast.LENGTH_SHORT).show();
                }
            });
        });
        TextView sendEmailButton = findViewById(R.id.send_email_btn);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý quên mật khẩu ở đây
                // Lấy địa chỉ email từ EditText
                String email = editTextEmail.getText().toString();

                // Gửi yêu cầu đặt lại mật khẩu
                sendPasswordResetEmail(email);
            }
        });
        //        TextView resetButton = findViewById(R.id.send_email_btn);
//        editTextOldPassword = findViewById(R.id.editTextOldPassword);
//        editTextNewPassword = findViewById(R.id.editTextNewPassword);
//        resetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Xử lý đặt lại mật khẩu ở đây
//                // Lấy dữ liệu từ các EditText
//                String oldPassword = editTextOldPassword.getText().toString();
//                String newPassword = editTextNewPassword.getText().toString();
//
//                // Thực hiện kiểm tra và đặt lại mật khẩu
//                resetPassword(oldPassword, newPassword);
//            }
//        });
    }
        private void sendPasswordResetEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Gửi yêu cầu đặt lại mật khẩu thành công
                            Toast.makeText(ResetPwdActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            // Chuyển đến màn hình chính hoặc màn hình đăng nhập
//                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finish();
                            // Chuyển người dùng đến ứng dụng Gmail
                            Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                            if (intent != null) {
                                intent.setAction(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                startActivity(intent);
                            } else {
                                // Nếu ứng dụng Gmail không được cài đặt, bạn có thể chuyển đến trang web đặt lại mật khẩu trên trình duyệt mặc định.
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com"));
                                startActivity(browserIntent);
                            }
                            finish();
                        } else {
                            // Gửi yêu cầu đặt lại mật khẩu thất bại
                            Toast.makeText(ResetPwdActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void resetPassword(String oldPassword, String newPassword) {
        // Kiểm tra xác thực người dùng
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Xác thực thành công, tiến hành đặt lại mật khẩu
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Đặt lại mật khẩu thành công
                                                Toast.makeText(ResetPwdActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                                                // Chuyển đến màn hình chính hoặc màn hình đăng nhập
                                                Intent intent = new Intent(ResetPwdActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // Đặt lại mật khẩu thất bại
                                                Toast.makeText(ResetPwdActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Xác thực thất bại
                            Toast.makeText(ResetPwdActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}




