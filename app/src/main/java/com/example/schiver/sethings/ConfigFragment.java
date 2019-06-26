package com.example.schiver.sethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.schiver.sethings.Adapter.ConfigRoomAdapter;
import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.Model.RoomListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfigFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<RoomAdapterData> roomDataList = new ArrayList<>();
    ArrayList<String> dataRoomName = new ArrayList<>();
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    FirebaseDatabase myDb2;
    DatabaseReference dbRef2;
    ProgressBar loadingBar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_config,container,false);
        loadingBar = rootView.findViewById(R.id.progressBar);
        /*roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));*/

        mRecyclerView = rootView.findViewById(R.id.room_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        mAdapter = new ConfigRoomAdapter(roomDataList,getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadingBar.setVisibility(View.VISIBLE);
        myDb = FirebaseDatabase.getInstance();
        myDb2 = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Config");
        dbRef.addValueEventListener(new ValueEventListener() {
            ArrayList<String> deviceStatus = new ArrayList<>();
            boolean switchVal = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomDataList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //Toast.makeText(getContext(),ds.getKey(),Toast.LENGTH_SHORT).show();
                    for(DataSnapshot ds2 : ds.getChildren()){
                        ConfigDeviceData myConfig = ds2.getValue(ConfigDeviceData.class);
                        deviceStatus.add(myConfig.getDeviceCondition());
                    }
                    if(deviceStatus.size() != 0){
                        switchVal = true;
                    }
                    roomDataList.add(new RoomAdapterData(ds.getKey() ,String.valueOf(ds.getChildrenCount())));
                }
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                loadingBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
