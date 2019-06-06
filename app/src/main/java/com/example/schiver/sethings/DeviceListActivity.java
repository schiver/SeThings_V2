package com.example.schiver.sethings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Adapter.DeviceAdapter;
import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.Model.DeviceListData;
import com.example.schiver.sethings.Utils.SharedPref;

import java.util.ArrayList;

public class DeviceListActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    String roomName;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<DeviceAdapterData> deviceDataList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
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
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));
        deviceDataList.add(new DeviceAdapterData(R.drawable.ic_help,"Lamp","Dev-01","Installed"));


        mRecyclerView = findViewById(R.id.device_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
        mAdapter = new DeviceAdapter(deviceDataList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
