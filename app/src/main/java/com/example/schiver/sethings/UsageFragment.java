package com.example.schiver.sethings;

import android.graphics.Color;
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


import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Adapter.RoomUsageAdapter;
import com.example.schiver.sethings.Model.DeviceUsageData;
import com.example.schiver.sethings.Model.RoomListData;
import com.example.schiver.sethings.Model.UsageAdapterData;
import com.example.schiver.sethings.Model.UsageListData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsageFragment extends Fragment {
    PieChart usageGraph;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<UsageAdapterData> usageDataList = new ArrayList<>();
    private ArrayList<PieEntry> yValues = new ArrayList<>();
    private FirebaseDatabase myDb;
    private DatabaseReference dbRef;
    private FirebaseDatabase myDb2;
    private DatabaseReference dbRef2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.fragment_usage,container,false);

        usageGraph = (PieChart) rootView.findViewById(R.id.consumable_chart);
        usageGraph.setUsePercentValues(true);
        usageGraph.getDescription().setEnabled(false);
        usageGraph.getLegend().setEnabled(false);
        usageGraph.setDragDecelerationFrictionCoef(0.95f);
        usageGraph.setDrawHoleEnabled(true);
        usageGraph.setHoleRadius(20f);
        usageGraph.setHoleColor(Color.WHITE);
        usageGraph.setTransparentCircleRadius(30f);

        mRecyclerView = rootView.findViewById(R.id.recycler_usage);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device_Usage");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float usageRoom = 0;
                yValues.clear();
                usageDataList.clear();
                usageGraph.animateY(1000,Easing.EaseInOutCubic);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot ds2 : ds.getChildren()){
                        UsageListData myData = ds2.getValue(UsageListData.class);
                        usageRoom += myData.getTotalUsage();
                    }
                    DeviceUsageData myData = ds.getValue(DeviceUsageData.class);
                    usageDataList.add(new UsageAdapterData(ds.getKey() ,String.valueOf(usageRoom)));
                    yValues.add(new PieEntry(usageRoom,ds.getKey()));
                    usageRoom = 0;
                }
                mAdapter = new RoomUsageAdapter(usageDataList,getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                PieDataSet dataSet = new PieDataSet(yValues,"Usage");
                dataSet.setValueFormatter(new PercentFormatter(usageGraph));
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(14f);
                data.setValueTextColor(Color.WHITE);

                usageGraph.setData(data);
                usageGraph.notifyDataSetChanged();
                usageGraph.invalidate();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
