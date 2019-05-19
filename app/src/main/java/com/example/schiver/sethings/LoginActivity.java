package com.example.schiver.sethings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView myColoredTextView;

    private String getColoredSpanned(String text , String color){
        String inputText = "<font color=\" + color + \">\" + text + \"</font>";
        return inputText;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myColoredTextView = (TextView) findViewById(R.id.sign_upText);
        String name = getColoredSpanned("New user?", "#800000");
        String surName = getColoredSpanned("Sign up here","#000080");


    }
}
