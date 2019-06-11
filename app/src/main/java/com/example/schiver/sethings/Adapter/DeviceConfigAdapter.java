package com.example.schiver.sethings.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.DeviceConfigDialog;
import com.example.schiver.sethings.DeviceEditDialog;
import com.example.schiver.sethings.Model.ConfigDeviceAdapterData;
import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.R;

import java.util.ArrayList;

public class DeviceConfigAdapter extends RecyclerView.Adapter<DeviceConfigAdapter.DeviceConfigViewHolder> {
    //Context mContext;
    private ArrayList<ConfigDeviceAdapterData> mDeviceList;
    public class DeviceConfigViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeviceIcon;
        public TextView mDeviceID;
        public TextView mDeviceName;
        public TextView mDeviceInfo;
        public CardView mContainer;
        public DeviceConfigViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceIcon = itemView.findViewById(R.id.device_icon);
            mDeviceName = itemView.findViewById(R.id.device_name);
            mDeviceID   = itemView.findViewById(R.id.device_id);
            mDeviceInfo = itemView.findViewById(R.id.device_information);
            mContainer  = itemView.findViewById(R.id.cardConfig);
        }
    }

    public DeviceConfigAdapter(ArrayList<ConfigDeviceAdapterData> mDeviceList) {
        this.mDeviceList = mDeviceList;
    }

    @NonNull
    @Override
    public DeviceConfigViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device_config,viewGroup,false);
        DeviceConfigAdapter.DeviceConfigViewHolder dcvh = new DeviceConfigAdapter.DeviceConfigViewHolder(v);
        return dcvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceConfigViewHolder deviceConfigViewHolder, int i) {
        final ConfigDeviceAdapterData currentItem = mDeviceList.get(i);
        deviceConfigViewHolder.mDeviceIcon.setImageResource(currentItem.getmImageResource());
        deviceConfigViewHolder.mDeviceID.setText(currentItem.getDeviceID());
        deviceConfigViewHolder.mDeviceName.setText(currentItem.getDeviceName());
        deviceConfigViewHolder.mDeviceInfo.setText(currentItem.getDeviceInfo());
        deviceConfigViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentItem.getDevicrType().equals("Sensor")){
                    openDialogConfig(v.getContext(),currentItem.getDeviceID(),currentItem.getDeviceName(),currentItem.getDevicrType(),currentItem.getmImageResource());
                }else{
                    Toast.makeText(v.getContext(),"You can only configure output type device",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    public void openDialogConfig(Context context, String deviceID, String deviceName, String deviceType, int deviceIcon){
        DeviceConfigDialog configDialog = new DeviceConfigDialog();
        Bundle param = new Bundle();
        param.putString("devID",deviceID);
        param.putString("devName",deviceName);
        param.putString("deviceType",deviceType);
        param.putInt("devIcon",deviceIcon);
        configDialog.setArguments(param);
        configDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"CONFIG");
    }

}
