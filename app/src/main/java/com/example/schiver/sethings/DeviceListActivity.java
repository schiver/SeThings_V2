package com.example.schiver.sethings;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Adapter.DeviceAdapter;
import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.Model.DeviceListData;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.Model.RoomListData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeviceListActivity extends AppCompatActivity {
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
    ArrayList<DeviceAdapterData> deviceDataList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        // Hidden component
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

        // Recycler view data
        //deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        //deviceDataList.add(new DeviceAdapterData(R.drawable.ic_home,"PC","Dev-01","Installed"));

        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        mRecyclerView = findViewById(R.id.device_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
        mAdapter = new DeviceAdapter(deviceDataList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device2/"+roomName+"/");
        dbRef.addValueEventListener(new ValueEventListener() {
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
                            DeviceListData dataDevice = ds.getValue(DeviceListData.class);
                            deviceDataList.add(
                                    new DeviceAdapterData(
                                            dataDevice.getIcon(),
                                            dataDevice.getDeviceName(),
                                            dataDevice.getDeviceID(),
                                            dataDevice.getDeviceInfo()
                                    )
                            );
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

    public void openDialog(){
        DeviceDialog deviceDialog = new DeviceDialog();
        deviceDialog.show(getSupportFragmentManager(),"TEST");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dot_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
