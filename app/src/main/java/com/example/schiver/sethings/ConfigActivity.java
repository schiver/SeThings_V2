package com.example.schiver.sethings;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schiver.sethings.Adapter.DeviceAdapter;
import com.example.schiver.sethings.Adapter.DeviceConfigAdapter;
import com.example.schiver.sethings.Model.ConfigDeviceAdapterData;
import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.Model.DeviceListData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    String roomName;
    TextView label1,label2;
    ImageView logo;
    ConstraintLayout mainFrame;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    ArrayList<ConfigDeviceAdapterData> deviceDataList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mainFrame = findViewById(R.id.mainFrame);
        label1 = findViewById(R.id.label1);
        label2 = findViewById(R.id.label2);
        logo = findViewById(R.id.logo);

        logo.setVisibility(View.GONE);
        label1.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);

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
        TextView mTextViewRoomName = (TextView) findViewById(R.id.labelRoomName);
        mTextViewRoomName.setText(roomName);

        //deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Not configure yet"));

        mRecyclerView = findViewById(R.id.device_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
        mAdapter = new DeviceConfigAdapter(deviceDataList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Config/"+roomName+"/");
        dbRef.addValueEventListener(new ValueEventListener() {
            String devInfo ="";
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    mRecyclerView.setVisibility(View.GONE);
                    mainFrame.setBackgroundColor(Color.parseColor("#ffffff"));
                    logo.setVisibility(View.VISIBLE);
                    label1.setVisibility(View.VISIBLE);
                    label2.setVisibility(View.VISIBLE);
                }else{
                    // Load data here if data exist
                    deviceDataList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ConfigDeviceData dataDevice = ds.getValue(ConfigDeviceData.class);
                        if(dataDevice.getDeviceCondition().equals("#")){
                            if(dataDevice.getDeviceType().equals("Sensor")){
                                devInfo="Installed";
                            }else{
                                devInfo="Not configure yet";
                            }

                        }else{
                            devInfo = dataDevice.getDeviceCondition();
                        }
                        if(!dataDevice.getDeviceType().equals("Sensor")){
                            deviceDataList.add(
                                    new ConfigDeviceAdapterData(
                                            dataDevice.getIcon(),
                                            dataDevice.getDeviceName(),
                                            dataDevice.getDeviceID(),
                                            devInfo,
                                            dataDevice.getDeviceType()
                                    )
                            );
                        }
                    }
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mainFrame.setBackgroundColor(Color.parseColor("#F3F4F8"));
                    logo.setVisibility(View.GONE);
                    label1.setVisibility(View.GONE);
                    label2.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
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
