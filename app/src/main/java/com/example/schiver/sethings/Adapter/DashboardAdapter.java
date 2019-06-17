package com.example.schiver.sethings.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schiver.sethings.Model.DashboardData;
import com.example.schiver.sethings.R;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {
    private ArrayList<DashboardData> mDashboardDataList;
    public class DashboardViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewRoom;
        public TextView mTextViewUsage;
        public TextView mTextViewDevice;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.room_name);
            mTextViewDevice = itemView.findViewById(R.id.usage_view);
            mTextViewUsage = itemView.findViewById(R.id.usage_percentage);
        }
    }

    public DashboardAdapter(ArrayList<DashboardData> mDashboardDataList) {
        this.mDashboardDataList = mDashboardDataList;
    }

    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dashboard,viewGroup,false);
        DashboardViewHolder dvh = new DashboardViewHolder(v);
        return dvh;

    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder dashboardViewHolder, int i) {
        DashboardData currentItem = mDashboardDataList.get(i);
        dashboardViewHolder.mTextViewRoom.setText(currentItem.getRoom());
        dashboardViewHolder.mTextViewUsage.setText(currentItem.getPercentage());
        dashboardViewHolder.mTextViewDevice.setText(currentItem.getInstalledDevice());
    }

    @Override
    public int getItemCount() {
        return mDashboardDataList.size();
    }

}
