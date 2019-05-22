package com.example.schiver.sethings;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SignupActivity extends AppCompatActivity {

    EditText getEmail,getUsername,getPass,getConfirmPass,getName,getHome;
    ProgressBar progressBar;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    ConstraintLayout myLayout;

    Handler handler;
    Runnable runnable;
    Timer timer;
    boolean valid1, valid2,valid3,valid4,valid5,valid6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Create object from signUp form
        getEmail = (EditText) findViewById(R.id.inputEmail);
        getUsername = (EditText) findViewById(R.id.inputUsername);
        getPass = (EditText) findViewById(R.id.inputPassword);
        getConfirmPass = (EditText) findViewById(R.id.inputConfirmPassword);
        getName = (EditText) findViewById(R.id.inputName);
        getHome = (EditText) findViewById(R.id.inputHomeID);

        // Firebase object
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Users");

        // Main Layput object
        myLayout = (ConstraintLayout) findViewById(R.id.mainLayout);

        // Set up progress bar to hide
        progressBar = (ProgressBar) findViewById(R.id.loadingBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);


        Button btnSubmit = (Button) findViewById(R.id.buttonSignUp);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                myLayout.setAlpha(0.5f);
                // Setting focusable editText to false
                getEmail.setFocusable(false);
                getUsername.setFocusable(false);
                getPass.setFocusable(false);
                getConfirmPass.setFocusable(false);
                getName.setFocusable(false);
                getHome.setFocusable(false);


                // Start handler time
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        myLayout.setAlpha(1);
                        progressBar.setVisibility(View.GONE);
                        validateForm();

                        /*if(getPass.getText().toString().equals(getConfirmPass.getText().toString())){
                            signUp();
                            Intent successIntent = new Intent(SignupActivity.this,SuccessSignupActivity.class);
                            startActivity(successIntent);
                            Toast.makeText(SignupActivity.this, "Success PUSH", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(SignupActivity.this, "Confirm password didn't match", Toast.LENGTH_LONG).show();
                            getEmail.setFocusable(true);
                            getUsername.setFocusable(true);
                            getPass.setFocusable(true);
                            getConfirmPass.setFocusable(true);
                            getName.setFocusable(true);
                            getHome.setFocusable(true);
                        }*/
                    }
                };
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(runnable);
                    }
                },5000,3000);
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void validateForm(){
        // Email
        if(TextUtils.isEmpty(getEmail.getText())){
            getEmail.setError("Email is required");
            valid1 = false;
        }else{
            valid1 = true;
        }

        // Username
        if(TextUtils.isEmpty(getUsername.getText())){
            getUsername.setError("Email is required");
            valid2 = false;
        }else{
            if(getUsername.getText().length() >= 6 && getUsername.getText().length() <= 12){
                valid2 = true;
            }else{
                valid2 = false;
                getUsername.setError("Username must be atleast 6-12 character");
            }
        }

        // Password
        if(TextUtils.isEmpty((getPass).getText().toString())){
            getPass.setError("Password is required");
            valid3 = false;
        }else{
            if(getPass.getText().toString().length() >= 8 && getPass.getText().toString().length() <= 12){
                valid3 = true;
            }else{
                getPass.setError("Password must be atleast 8-12 character");
                valid3 = false;
            }
        }

        // Confirm pass
        if(getPass.getText().toString().equals(getConfirmPass.getText().toString())){
            valid4 = true;
        }else{
            getConfirmPass.setError("Confirm password didn't match");
            valid4 = false;
        }

        // Name
        if(TextUtils.isEmpty(getName.getText())){
            getName.setError("Name is required");
            valid5 = false;
        }else{
            valid5 = true;
        }

        // Home-ID
        if(TextUtils.isEmpty(getHome.getText())){
            getHome.setError("Home-Id is required");
            valid6 = false;
        }else{
            valid6 = true;
        }



        // Final validation
        if(valid1 == true && valid2 == true && valid3 == true && valid4 == true && valid5 == true && valid6 == true){
            Toast.makeText(SignupActivity.this, "Success PUSH", Toast.LENGTH_LONG).show();
        }else{

        }


        getEmail.setFocusableInTouchMode(true);
        getUsername.setFocusableInTouchMode(true);
        getPass.setFocusableInTouchMode(true);
        getConfirmPass.setFocusableInTouchMode(true);
        getName.setFocusableInTouchMode(true);
        getHome.setFocusableInTouchMode(true);
    }
    private void signUp(){
        final ArrayList<String> signUpData = new ArrayList<>();
        signUpData.add(getEmail.getText().toString());
        signUpData.add(getUsername.getText().toString());
        signUpData.add(getPass.getText().toString());
        signUpData.add(getName.getText().toString());
        signUpData.add(getHome.getText().toString());
        final UserData postSignUp =
                new UserData(
                        signUpData.get(0),
                        signUpData.get(1),
                        signUpData.get(2),
                        signUpData.get(3),
                        signUpData.get(4)
                );
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(postSignUp.getUsername()).exists()){
                    getUsername.setError("Username already registered");
                }else{
                    dbRef.child(postSignUp.getUsername()).setValue(postSignUp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
