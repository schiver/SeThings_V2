package com.example.schiver.sethings;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView myColoredTextView;

    private String getColoredSpanned(String text , String color){
        String inputText = "<font color="+color+">"+text+"</font>";
        return inputText;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myColoredTextView = (TextView) findViewById(R.id.sign_upText);
        String name = getColoredSpanned("New user?", "#3e4a59");
        String surName = getColoredSpanned("SignUp Here","#3836BD");

        myColoredTextView.setText(Html.fromHtml(name+" "+surName));
    }
}
