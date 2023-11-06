package com.example.nt118project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class Welcome extends AppCompatActivity {
    private ImageButton languageButton;
    private TextView googleSignin;
    private TextView emailSignin;
    private TextView signup;

    FirebaseAuth auth;
    FirebaseDatabase database;

    GoogleSignInClient mGoogleSignInClient;

    int RC_SIGN_IN =20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        googleSignin = findViewById(R.id.googleSignIn);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignin1();
            }
        });

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

    private void googleSignin1() {
        Intent intent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode== RC_SIGN_IN){

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuth(account.getIdToken());
                }catch (Exception e){
                    Toast.makeText(this, "SAI", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void firebaseAuth(String idToken) {

        AuthCredential credential = GoogleAuthProvider .getCredential(idToken,null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("name",user.getDisplayName());
                            map.put("profile",user.getPhotoUrl().toString());

                            database.getReference().child("users").child(user.getUid()).setValue(map);

                            Intent intent = new Intent(Welcome.this,MainActivity.class);
                            startActivity(intent);
                        }

                        else{

                            Toast.makeText(Welcome.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}