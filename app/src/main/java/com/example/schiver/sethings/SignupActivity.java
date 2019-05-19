package com.example.schiver.sethings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignupActivity extends AppCompatActivity {

    EditText getEmail,getUsername,getPass,getConfirmPass,getName,getHome;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;

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

        Button btnSubmit = (Button) findViewById(R.id.buttonSignUp);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
                Intent successIntent = new Intent(SignupActivity.this,SuccessSignupActivity.class);
                startActivity(successIntent);
            }
        });
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
                    Toast.makeText(SignupActivity.this, "User already registered", Toast.LENGTH_LONG).show();
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
