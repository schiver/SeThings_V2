package com.example.schiver.sethings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.schiver.sethings.Model.UsageConfigData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsageSettingActivity extends AppCompatActivity {
    String sharedPrefUsername;
    String sharedPrefHomeID;
    TextView dailyUsage, homeEnergy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_setting);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        mytoolbar.setTitle("Usage Setting");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mytoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Edit component
        sharedPrefUsername = SharedPref.readSharedPref(getApplicationContext(),"username","");
        sharedPrefHomeID = SharedPref.readSharedPref(getApplicationContext(),"home","");

        dailyUsage = findViewById(R.id.usage_value);
        dailyUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEditDailyUsage(dailyUsage.getText().toString(),sharedPrefHomeID);
            }
        });
        homeEnergy = findViewById(R.id.energy_value);
        homeEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEditEnergy(homeEnergy.getText().toString(),sharedPrefHomeID);
            }
        });


    }
    public void openDialogEditEnergy(String editUsage, String homeID){
        EditEnergyDialog editEnergy = new EditEnergyDialog();
        Bundle param = new Bundle();
        param.putString("editUsage",editUsage);
        param.putString("editHomeID",homeID);
        editEnergy.setArguments(param);
        editEnergy.show(getSupportFragmentManager(),"Dialog Edit DailyUsage");
    }

    public void openDialogEditDailyUsage(String dailyUsage, String homeID){
        EditDailyUsageDialog editDaily = new EditDailyUsageDialog();
        Bundle param = new Bundle();
        param.putString("editDaily",dailyUsage);
        param.putString("editHomeID",homeID);
        editDaily.setArguments(param);
        editDaily.show(getSupportFragmentManager(),"Dialog Edit DailyUsage");
    }
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase myDb = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = myDb.getReference("SeThings-Usage_Config/"+sharedPrefHomeID);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsageConfigData myConfigUsageData = dataSnapshot.getValue(UsageConfigData.class);
                if(myConfigUsageData.getDailyAverage() != 0){
                    dailyUsage.setText(String.valueOf(myConfigUsageData.getDailyAverage())+" Kwh");
                }
                if(myConfigUsageData.getEnergy() != 0){
                    homeEnergy.setText(String.valueOf(myConfigUsageData.getEnergy())+" VA");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

