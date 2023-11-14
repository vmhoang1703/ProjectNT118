package com.example.nt118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.BreakIterator;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText editTextOldPassword, editTextNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ImageView back = findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent backIntent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(backIntent);
            }
        });

        TextView backText = findViewById(R.id.back_text);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở màn hình đăng nhập
                Intent backIntent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(backIntent);
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

    }

    private void sendPasswordResetEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Gửi yêu cầu đặt lại mật khẩu thành công
                            Toast.makeText(ResetPasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            // Chuyển đến màn hình chính hoặc màn hình đăng nhập
                            Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Gửi yêu cầu đặt lại mật khẩu thất bại
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    private void resetPassword(String oldPassword, String newPassword) {
//        // Kiểm tra xác thực người dùng
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
//
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // Xác thực thành công, tiến hành đặt lại mật khẩu
//                            user.updatePassword(newPassword)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                // Đặt lại mật khẩu thành công
//                                                Toast.makeText(ResetPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
//                                                // Chuyển đến màn hình chính hoặc màn hình đăng nhập
//                                                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            } else {
//                                                // Đặt lại mật khẩu thất bại
//                                                Toast.makeText(ResetPasswordActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                        } else {
//                            // Xác thực thất bại
//                            Toast.makeText(ResetPasswordActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

}