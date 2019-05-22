package com.example.schiver.sethings;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView myColoredTextView;

    FirebaseDatabase myDb;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myColoredTextView = (TextView) findViewById(R.id.sign_upText);
        String name = getColoredSpanned("New user?", "#3e4a59");
        String surName = getColoredSpanned("SignUp Here","#3836BD");
        myColoredTextView.setText(Html.fromHtml(name+" "+surName));

        final EditText inputUsername = (EditText) findViewById(R.id.username);
        final EditText inputPassword = (EditText) findViewById(R.id.password);
        Button btnSignIn = (Button) findViewById(R.id.btn_login);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(inputUsername.getText().toString() , inputPassword.getText().toString());
                
            }
        });
    }

    private void signIn(String userName, String password){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Users");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private String getColoredSpanned(String text , String color){
        String inputText = "<font color="+color+">"+text+"</font>";
        return inputText;
    }
}
