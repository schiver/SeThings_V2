package com.example.schiver.sethings;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schiver.sethings.Adapter.IntroViewPagerAdapter;
import com.example.schiver.sethings.Model.ScreenItem;

import java.util.ArrayList;
import java.util.List;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        // views

        tabIndicator = findViewById(R.id.tabLayout);

        // fill list screen

        List<ScreenItem>  mList  = new ArrayList<>();
        mList.add(new ScreenItem("Lets get started !","Welcome to SeThings",R.drawable.main_content1_1));
        mList.add(new ScreenItem("Setting up your smart home and monitor it","Config and Monitoring",R.drawable.main_content2));
        mList.add(new ScreenItem("Check it out explore SeThings more","Login to your account",R.drawable.main_content3));


        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        // Sign Up Intent

        // Login Intent
        Button btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInActivity  = new Intent(WalkthroughActivity.this,LoginActivity.class);
                startActivity(signInActivity);
            }
        });
        Button btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(WalkthroughActivity.this,SignupActivity.class);
                startActivity(signUpActivity);
            }
        });
    }
}
