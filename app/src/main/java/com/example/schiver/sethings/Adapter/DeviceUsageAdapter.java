package com.example.schiver.sethings.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.schiver.sethings.Model.DeviceUsageAdapterData;
import com.example.schiver.sethings.Model.DeviceUsageData;
import com.example.schiver.sethings.R;

import java.util.ArrayList;

public class DeviceUsageAdapter extends RecyclerView.Adapter<DeviceUsageAdapter.DeviceUsageViewHolder> {
    private ArrayList<DeviceUsageAdapterData> mUsageList = new ArrayList<>();
    public class DeviceUsageViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeviceIcon;
        public TextView mDeviceName;
        public TextView mDeviceUsage;
        public TextView mPercentageUsage;
        public ProgressBar mPercentageProgress;

        public DeviceUsageViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceIcon = itemView.findViewById(R.id.device_icon);
            mDeviceName = itemView.findViewById(R.id.device_name);
            mDeviceUsage = itemView.findViewById(R.id.device_usage);
            mPercentageUsage = itemView.findViewById(R.id.view_usage_percentage);
            mPercentageProgress = itemView.findViewById(R.id.usage_percentage);
        }
    }

    public DeviceUsageAdapter(ArrayList<DeviceUsageAdapterData> mUsageList) {
        this.mUsageList = mUsageList;
    }

    @NonNull
    @Override
    public DeviceUsageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device_usage,viewGroup,false);
        DeviceUsageAdapter.DeviceUsageViewHolder duvh = new DeviceUsageAdapter.DeviceUsageViewHolder(v);
        return duvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceUsageViewHolder deviceUsageViewHolder, int i) {
        final DeviceUsageAdapterData currentItem = mUsageList.get(i);
        deviceUsageViewHolder.mDeviceIcon.setImageResource(currentItem.getDeviceIcon());
        deviceUsageViewHolder.mDeviceName.setText(currentItem.getDeviceName());
        deviceUsageViewHolder.mDeviceUsage.setText(currentItem.getDeviceUsage());
        deviceUsageViewHolder.mPercentageUsage.setText(currentItem.getDeviceUsagePercentage());
        deviceUsageViewHolder.mPercentageProgress.setProgress((int)currentItem.getProgressPercentage());

        deviceUsageViewHolder.mPercentageProgress.getProgressDrawable().setColorFilter(
                currentItem.getPercentageColor(), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        return mUsageList.size();
    }
}
