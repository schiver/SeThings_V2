package com.example.schiver.sethings;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.UserData;
import com.example.schiver.sethings.Utils.AESUtils;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    TextView myColoredTextView;
    ProgressBar progressBar;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    ConstraintLayout myLayout;
    Handler handler;
    Runnable runnable;
    Timer timer;
    EditText inputUsername;
    EditText inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myColoredTextView = (TextView) findViewById(R.id.sign_upText);
        String name = getColoredSpanned("New user?", "#3e4a59");
        String surName = getColoredSpanned("SignUp Here","#005AAC");
        myColoredTextView.setText(Html.fromHtml(name+" "+surName));

        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);

        // Set up progress bar to hide
        progressBar = (ProgressBar) findViewById(R.id.loading_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        // Main Layput object
        myLayout = (ConstraintLayout) findViewById(R.id.mainLayout);

        Button btnSignIn = (Button) findViewById(R.id.btn_login);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                myLayout.setAlpha(0.5f);
                inputUsername.setFocusable(false);
                inputPassword.setFocusable(false);
                signIn(inputUsername.getText().toString() , inputPassword.getText().toString());
                // Start handler time
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        myLayout.setAlpha(1);
                        inputUsername.setFocusableInTouchMode(true);
                        inputPassword.setFocusableInTouchMode(true);
                    }
                };
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(runnable);
                    }
                },5000,3000);
                //signIn(inputUsername.getText().toString() , inputPassword.getText().toString());

            }
        });
    }

    private void signIn(final String userName, final String password){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Users");
        if(TextUtils.isEmpty(userName)){
            inputUsername.setError("Pelase input username");
        }else{
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(userName).exists()){
                        UserData loginData = dataSnapshot.child(userName).getValue(UserData.class);
                        String decrypted = "";
                        try {
                            decrypted = AESUtils.decrypt(loginData.getPassword());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //Toast.makeText(LoginActivity.this, "Pass : "+decrypted, Toast.LENGTH_LONG).show();
                        if(loginData.getUsername().equals(userName) && password.equals(decrypted) ){
                            // Success Login
                            // Saving value for true session
                            SharedPref.saveSharefPref(getApplicationContext(),"session","true");
                            Intent mainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(mainActivityIntent);
                            //Toast.makeText(LoginActivity.this, "Sukses Login", Toast.LENGTH_LONG).show();
                        }else{
                            // Failed to login
                            inputUsername.setError("Account not registered yet");
                        }
                    }else{
                        // Account not registered
                        inputUsername.setError("Account not registered yet");
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    private String getColoredSpanned(String text , String color){
        String inputText = "<font color="+color+">"+text+"</font>";
        return inputText;
    }
}
