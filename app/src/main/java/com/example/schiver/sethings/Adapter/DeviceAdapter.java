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

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private ArrayList<DeviceAdapterData> mDeviceList;

    public class DeviceViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeviceIcon;
        public TextView mDeviceID;
        public TextView mDeviceName;
        public TextView mDeviceInfo;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceIcon = itemView.findViewById(R.id.device_icon);
            mDeviceName = itemView.findViewById(R.id.device_name);
            mDeviceID   = itemView.findViewById(R.id.device_id);
            mDeviceInfo = itemView.findViewById(R.id.device_information);
        }
    }

    public DeviceAdapter(ArrayList<DeviceAdapterData> mDeviceList) {
        this.mDeviceList = mDeviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device,viewGroup,false);
        DeviceAdapter.DeviceViewHolder dlvh = new DeviceAdapter.DeviceViewHolder(v);
        return dlvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder deviceViewHolder, int i) {
        final DeviceAdapterData currentItem = mDeviceList.get(i);
        deviceViewHolder.mDeviceIcon.setImageResource(currentItem.getmImageResource());
        deviceViewHolder.mDeviceID.setText(currentItem.getDeviceID());
        deviceViewHolder.mDeviceName.setText(currentItem.getDeviceName());
        deviceViewHolder.mDeviceInfo.setText(currentItem.getDeviceInfo());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
