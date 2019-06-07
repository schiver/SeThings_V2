package com.example.schiver.sethings.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.R;

import java.util.ArrayList;

public class DeviceConfigAdapter extends RecyclerView.Adapter<DeviceConfigAdapter.DeviceConfigViewHolder> {

    private ArrayList<DeviceAdapterData> mDeviceList;
    public class DeviceConfigViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeviceIcon;
        public TextView mDeviceID;
        public TextView mDeviceName;
        public TextView mDeviceInfo;
        public DeviceConfigViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceIcon = itemView.findViewById(R.id.device_icon);
            mDeviceName = itemView.findViewById(R.id.device_name);
            mDeviceID   = itemView.findViewById(R.id.device_id);
            mDeviceInfo = itemView.findViewById(R.id.device_information);
        }
    }

    public DeviceConfigAdapter(ArrayList<DeviceAdapterData> mDeviceList) {
        this.mDeviceList = mDeviceList;
    }

    @NonNull
    @Override
    public DeviceConfigViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device,viewGroup,false);
        DeviceConfigAdapter.DeviceConfigViewHolder dcvh = new DeviceConfigAdapter.DeviceConfigViewHolder(v);
        return dcvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceConfigViewHolder deviceConfigViewHolder, int i) {
        final DeviceAdapterData currentItem = mDeviceList.get(i);
        deviceConfigViewHolder.mDeviceIcon.setImageResource(currentItem.getmImageResource());
        deviceConfigViewHolder.mDeviceID.setText(currentItem.getDeviceID());
        deviceConfigViewHolder.mDeviceName.setText(currentItem.getDeviceName());
        deviceConfigViewHolder.mDeviceInfo.setText(currentItem.getDeviceInfo());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
