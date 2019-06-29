package com.example.schiver.sethings;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.schiver.sethings.Adapter.DeviceConfigAdapter;
import com.example.schiver.sethings.Adapter.DeviceUsageAdapter;
import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.Model.DeviceUsageAdapterData;
import com.example.schiver.sethings.Model.DeviceUsageData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class UsageDetailActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    String roomName;
    float roomUsage;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    ArrayList<DeviceUsageAdapterData> deviceUsageList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_detail);
        // Setting toolbar
        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mytoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Get Shared pref roomName
        roomName = SharedPref.readSharedPref(getApplicationContext(),"Room","");
        roomUsage = Float.parseFloat(SharedPref.readSharedPref(getApplicationContext(),"RoomUsage",""));
        TextView mTextViewRoomName = (TextView) findViewById(R.id.labelRoomName);
        mTextViewRoomName.setText(roomName);

        /*deviceUsageList.add(new DeviceUsageAdapterData("TES","20 Kwh","50%",R.drawable.ic_fan,Color.RED,50));
        deviceUsageList.add(new DeviceUsageAdapterData("TES","20 Kwh","15%",R.drawable.ic_fan,Color.RED,15));
        deviceUsageList.add(new DeviceUsageAdapterData("TES","20 Kwh","20%",R.drawable.ic_fan,Color.RED,20));
        deviceUsageList.add(new DeviceUsageAdapterData("TES","20 Kwh","15%",R.drawable.ic_fan,Color.RED,15));*/

        mRecyclerView = findViewById(R.id.usage_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device_Usage/"+roomName+"/");
        dbRef.addValueEventListener(new ValueEventListener() {
            int colorProgress;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    DeviceUsageData myData = ds.getValue(DeviceUsageData.class);
                    float dataPercentUsage = 100*myData.getTotalUsage()/roomUsage;
                    int progressPercentage = (int) Math.floor(dataPercentUsage*100)/100;
                    if(progressPercentage <= 25){
                        colorProgress = rgb(29, 209, 161);
                    }else if(progressPercentage > 25 && progressPercentage <= 60){
                        colorProgress = rgb(254, 202, 87);
                    }else if(progressPercentage >= 60){
                        colorProgress = rgb(255, 107, 107);
                    }
                    deviceUsageList.add(
                            new DeviceUsageAdapterData(
                                    myData.getDeviceName(),
                                    String.valueOf(Math.floor(myData.getTotalUsage()*1000)/1000)+" Kwh",
                                    String.valueOf(Math.floor(dataPercentUsage*100)/100)+"%",
                                     myData.getIcon(),
                                     colorProgress,
                                     progressPercentage
                            )
                    );
                }
                mAdapter = new DeviceUsageAdapter(deviceUsageList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dot_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.account:
                Intent settingIntent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(settingIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(){
        SharedPref.saveSharefPref(getApplicationContext(),"session","false");
        Intent walkThrougIntent = new Intent(getApplicationContext(),WalkthroughActivity.class);
        startActivity(walkThrougIntent);
    }
}
