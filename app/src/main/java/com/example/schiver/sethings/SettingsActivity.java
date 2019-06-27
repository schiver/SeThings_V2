package com.example.schiver.sethings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.schiver.sethings.Adapter.SettingsMenuAdapter;
import com.example.schiver.sethings.Model.SettingsMenu;
import com.example.schiver.sethings.Model.UserData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SettingsMenu> data = new ArrayList<>();
    private FirebaseDatabase myDb;
    private DatabaseReference dbRef;
    private String sharedPrefUsername;
    private TextView mLabelUsername,mLabelName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPrefUsername = SharedPref.readSharedPref(getApplicationContext(),"username","");
        mLabelName = findViewById(R.id.label_name);
        mLabelUsername = findViewById(R.id.label_username);

        // Setting toolbar
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        mytoolbar.setTitle("Settings");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mytoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        data.add(new SettingsMenu("Usage Setting","Control your device usage",R.drawable.ic_setting_usage));
        data.add(new SettingsMenu("Account Setting","Update your profile",R.drawable.ic_person));

        //Recycle View
        mRecyclerView = (RecyclerView) findViewById(R.id.menu_recycler);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SettingsMenuAdapter(data, this);
        mRecyclerView.setAdapter(mAdapter);

        //Data


    }

    @Override
    protected void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Users/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData myAccount = dataSnapshot.child(sharedPrefUsername).getValue(UserData.class);
                mLabelName.setText(myAccount.getName());
                mLabelUsername.setText("@"+myAccount.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
