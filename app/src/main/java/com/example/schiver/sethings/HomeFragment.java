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

import com.example.schiver.sethings.Adapter.DashboardAdapter;
import com.example.schiver.sethings.Model.DashboardData;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        final ArrayList<DashboardData> dashboardDataList = new ArrayList<>();
        dashboardDataList.add(new DashboardData("Living Room","20%", "15"));
        dashboardDataList.add(new DashboardData("Bedroom 1","50%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        dashboardDataList.add(new DashboardData("Bedroom 2","10%", "5"));
        mRecyclerView = rootView.findViewById(R.id.dashboad_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        mAdapter = new DashboardAdapter(dashboardDataList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
