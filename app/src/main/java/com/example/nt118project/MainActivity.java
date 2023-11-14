package com.example.nt118project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class MainActivity extends AppCompatActivity {


    static String usr ;
    static String email ;
    static String pwd;
    static String rePwd;

    public static void setValueFromCurrentClass(String username, String eml, String password, String confirmPassword) {
        usr = username;
        email = eml;
        pwd= password;
        rePwd= confirmPassword;
    }
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
        String url = "https://uiot.ixxc.dev/auth/realms/master/login-actions/registration?client_id=openremote";
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
        webView.loadUrl("https://uiot.ixxc.dev/manager/");

    }
}