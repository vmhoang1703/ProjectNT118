package com.example.nt118project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {


    static String usr ;
    static String email ;
    static String pwd;
    static String rePwd;
    static String token;

    public static void setValueFromCurrentClass(String username, String eml, String password, String confirmPassword) {
        usr = username;
        email = eml;
        pwd= password;
        rePwd= confirmPassword;
    }
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Xử lý khi đã lấy được token, ví dụ lưu trữ hoặc sử dụng nó cho các yêu cầu khác
            Log.d("Token", token);
        }
    };
//    https://uiot.ixxc.dev/auth/realms/master/login-actions/authenticate?client_id=openremote
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setValueFromCurrentClass(usr,email,pwd,rePwd);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE2OTk5MjMxNDIsImlhdCI6MTY5OTkyMzA4MiwianRpIjoiMGM0ZDEyNWMtNjcxOC00NzU2LWJmODItZGU0YjY4YTdlN2JkIiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI1Yzg3ZWU2ZS0xOGYwLTQzMTgtYjYyZS1mMzI4OGU4MzliMmIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJvcGVucmVtb3RlIiwic2Vzc2lvbl9zdGF0ZSI6IjZhY2RmMGZmLTZhMzctNDQyNy04YTM4LTMxZDQzNWQzMWRjMyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91aW90Lml4eGMuZGV2Il0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiI2YWNkZjBmZi02YTM3LTQ0MjctOGEzOC0zMWQ0MzVkMzFkYzMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJUcmlldSBEaW5oIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiMjE1MjE1NzYiLCJnaXZlbl9uYW1lIjoiVHJpZXUiLCJmYW1pbHlfbmFtZSI6IkRpbmgiLCJlbWFpbCI6InRyaWV1YnVpMDAzQGdtYWlsLmNvbSJ9.UmnH_WE4HYu3Si_8v0SfIeeBt3EsXAJxIWzMeBafRWaYJZvxGutjdLB2mdOpnaOaKWhH0ioXKOvKnBisfRXG47-PU4jaIoQZMTEd3TYp9FQfOyuaWf9rJ9OPAGjMbnrCQCwXWBHwE_xkYPpPz2oLIA4wuads5Z64JZodvRAE141HDNi50vvEwhmOt48SI-krmte-VKTWXxf5BWD_xfWLuNR6jwWoF1f4aevOPN3TDxYQ0SX4NlQ0S7UUd5rDPQUnXCBoEbL_XVnDNSWLpBzYaunbA5oVKeLcCdjAUTEt6AtTACQsKjWKMnU3V-_TFIyQcfz59xw9CBzsAFWNLPus5Q";
//        String url = "https://uiot.ixxc.dev/auth/realms/master/login-actions/registration?client_id=openremote";
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view,String url){
                if(url.contains("openid-connect/registrations")){
                    Log.d("A","onPageFinished: Fill form");



                    String usrScript= "document.getElementById('username').value = '"+ usr +"';";
                    String emailScript= "document.getElementById('email').value = '"+ email +"';";
                    String pwdScript= "document.getElementById('password').value = '"+ pwd +"';";
                    String rePwdScript= "document.getElementById('rePassword').value = '"+ rePwd +"';";

                    view.evaluateJavascript(usrScript,null);
                    view.evaluateJavascript(emailScript,null);
                    view.evaluateJavascript(pwdScript,null);
                    view.evaluateJavascript(rePwdScript,null);
                    view.evaluateJavascript("document.getElementById('submit_button').submit();",null);
            }}
        });

        webView.stopLoading();
        getToken();
//        webView.loadUrl("https://uiot.ixxc.dev/manager/");

    }
    private void getToken() {
        // Thực hiện yêu cầu POST để lấy token
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    String json = "{\"username\":\"your_username\",\"password\":\"your_password\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token")
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseString = response.body().string();

                        token = ""; // Thay bằng cách parse token từ responseString

                        // Gửi thông báo đến Handler để xử lý token
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}