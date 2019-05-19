package com.example.schiver.sethings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Open Success Register Activity
        Button btnSubmit = (Button) findViewById(R.id.buttonSignUp);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent successIntent = new Intent(SignupActivity.this,SuccessSignupActivity.class);
                startActivity(successIntent);
            }
        });
    }
}
