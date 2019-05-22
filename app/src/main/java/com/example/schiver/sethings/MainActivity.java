package com.example.schiver.sethings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.schiver.sethings.Utils.SharedPref;

public class MainActivity extends AppCompatActivity {

    boolean session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void mySession(){
        session = Boolean.valueOf(SharedPref.readSharedPref(getApplicationContext(),"session","false"));
        if(session){

        }else{

        }
    }
}
