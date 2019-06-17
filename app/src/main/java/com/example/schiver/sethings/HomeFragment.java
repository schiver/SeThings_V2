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
import android.widget.Toast;

import com.example.schiver.sethings.Adapter.DashboardAdapter;
import com.example.schiver.sethings.Model.DashboardData;
import com.example.schiver.sethings.Model.DeviceUsageData;
import com.example.schiver.sethings.Model.UsageListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseDatabase myDb;
    private DatabaseReference dbRef;
    /*private float usageRoom,allUsage;
    private ArrayList<Float> myArrayUsage = new ArrayList<>();
    private ArrayList<String> myArrayRoom = new ArrayList<>();
    private ArrayList<String> myArrayData = new ArrayList<>();
    private ArrayList<String> myArrayInstalledDevice = new ArrayList<>();*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        /*dashboardDataList.add(new DashboardData("Living Room","20%", "15"));
        dashboardDataList.add(new DashboardData("Bedroom 1","50%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));*/

        mRecyclerView = rootView.findViewById(R.id.dashboad_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);


        return rootView;
    }

    @Override
    public void onResume() {
        final ArrayList<Float> myArrayUsage = new ArrayList<>();
        final ArrayList<String> myArrayRoom = new ArrayList<>();
        final ArrayList<String> myArrayData = new ArrayList<>();
        final ArrayList<String> myArrayInstalledDevice = new ArrayList<>();
        final ArrayList<DashboardData> dashboardDataList = new ArrayList<>();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device_Usage");
        dbRef.addValueEventListener(new ValueEventListener() {
            int i =0;
            float usageRoom,allUsage;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot ds2 : ds.getChildren()){
                        UsageListData myData = ds2.getValue(UsageListData.class);
                        usageRoom += myData.getTotalUsage();
                        i++;
                    }
                    myArrayUsage.add(usageRoom);
                    DeviceUsageData myData = ds.getValue(DeviceUsageData.class);
                    myArrayRoom.add(ds.getKey());
                    myArrayData.add(String.valueOf(usageRoom));
                    myArrayInstalledDevice.add(String.valueOf(i));
                    //dashboardDataList.add(new DashboardData(ds.getKey(),String.valueOf(usageRoom)+"/"+String.valueOf(allUsage), String.valueOf(i)));
                    usageRoom = 0;
                    i=0;
                }
                for(int a=0; a<myArrayUsage.size(); a++){
                    allUsage+=myArrayUsage.get(a);
                }
                for(int index = 0; index < myArrayRoom.size(); index++){
                    float percentage = (float) Math.ceil((float)Float.parseFloat(myArrayData.get(index)) / allUsage * 100) ;
                    dashboardDataList.add(new DashboardData(myArrayRoom.get(index),String.valueOf(percentage), myArrayInstalledDevice.get(index)));
                }
                Toast.makeText(getContext(),"Total Usage"+String.valueOf(allUsage),Toast.LENGTH_SHORT).show();
                mAdapter = new DashboardAdapter(dashboardDataList);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onResume();

    }
}
