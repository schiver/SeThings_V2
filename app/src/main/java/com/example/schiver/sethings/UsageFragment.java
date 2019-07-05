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
import android.widget.TextView;
import android.widget.Toast;


import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Adapter.RoomUsageAdapter;
import com.example.schiver.sethings.Model.DeviceUsageData;
import com.example.schiver.sethings.Model.RoomListData;
import com.example.schiver.sethings.Model.UsageAdapterData;
import com.example.schiver.sethings.Model.UsageListData;
import com.example.schiver.sethings.Utils.SharedPref;
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
    private TextView labelPrediction1,labelPrediction2,labelPrediction3,labelTotalUsage,labelEstimate;
    private float allEnergyUsage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPref.saveSharefPref(getContext(),"page","");
        final View rootView =  inflater.inflate(R.layout.fragment_usage,container,false);
        labelPrediction1 = rootView.findViewById(R.id.label_prediction1);
        labelPrediction2 = rootView.findViewById(R.id.label_prediction2);
        labelPrediction3 = rootView.findViewById(R.id.label_prediction3);
        labelTotalUsage = rootView.findViewById(R.id.label_total_usage);
        labelEstimate = rootView.findViewById(R.id.textView8);
        labelEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveSharefPref(getContext(),"Estimation",String.valueOf(estimating(allEnergyUsage)));

            }
        });

        labelPrediction1.setVisibility(View.GONE);
        labelPrediction2.setVisibility(View.GONE);
        labelPrediction3.setVisibility(View.GONE);
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

    public float estimating(float input){
        float monthlyEstimation = (input / 20) * 28;
        return monthlyEstimation;
    }

    public String formatDecimal(float number) {
        float epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number); // sdb
        } else {
            return String.format("%10.2f", number); // dj_segfault
        }
    }
    @Override
    public void onResume() {
        if(!SharedPref.readSharedPref(getContext(),"Estimation","").equals(null)){
            //labelEstimate.setVisibility(View.VISIBLE);
            labelEstimate.setVisibility(View.GONE);
            labelPrediction1.setVisibility(View.VISIBLE);
            labelPrediction2.setVisibility(View.VISIBLE);
            labelPrediction3.setVisibility(View.GONE);
        }else{
            labelEstimate.setVisibility(View.VISIBLE);
        }

        labelTotalUsage.setVisibility(View.GONE);
        usageGraph.setVisibility(View.GONE);
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device_Usage");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allEnergyUsage = 0;
                float usageRoom = 0;
                yValues.clear();
                usageDataList.clear();
                usageGraph.animateY(1000,Easing.EaseInOutCubic);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot ds2 : ds.getChildren()){
                        UsageListData myData = ds2.getValue(UsageListData.class);
                        usageRoom += myData.getTotalUsage();
                    }
                    allEnergyUsage+=usageRoom;
                    DeviceUsageData myData = ds.getValue(DeviceUsageData.class);
                    usageDataList.add(new UsageAdapterData(ds.getKey() ,String.valueOf(Math.floor(usageRoom*100)/100)));
                    yValues.add(new PieEntry(usageRoom,ds.getKey()));
                    usageRoom = 0;
                }
                labelTotalUsage.setVisibility(View.VISIBLE);
                labelTotalUsage.setText(String.valueOf(Math.ceil(allEnergyUsage)).replaceAll(".0*$", " ")+"Kwh");
                labelPrediction2.setText(String.valueOf(Math.ceil(estimating(allEnergyUsage))).replaceAll(".0*$", " ")+"Kwh with "+String.format("%,.2f", estimating(allEnergyUsage)*1200)+" IDR Costs");


                mAdapter = new RoomUsageAdapter(usageDataList,getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                PieDataSet dataSet = new PieDataSet(yValues,"Usage");
                dataSet.setValueFormatter(new PercentFormatter(usageGraph));
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(new int[] {
                        Color.rgb(214, 48, 49),
                        Color.rgb(9, 132, 227),
                        Color.rgb(0, 184, 148),
                        Color.rgb(254, 202, 87),
                        Color.rgb(10, 189, 227)
                });

                PieData data = new PieData((dataSet));
                data.setValueTextSize(14f);
                data.setValueTextColor(Color.WHITE);

                usageGraph.setData(data);
                usageGraph.notifyDataSetChanged();
                usageGraph.invalidate();
                usageGraph.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
