package com.example.schiver.sethings.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schiver.sethings.Model.RoomDataAdapter;
import com.example.schiver.sethings.R;

import java.util.ArrayList;

public class DeviceRoomAdapter extends RecyclerView.Adapter<DeviceRoomAdapter.DeviceRoomViewHolder> {
    private ArrayList<RoomDataAdapter> mDeviceRoomDataList;

    public class DeviceRoomViewHolder extends  RecyclerView.ViewHolder{
        public TextView mTextViewRoom;
        public TextView mTextViewInstalledDevice;

        public DeviceRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.input_room_name);
            mTextViewInstalledDevice = itemView.findViewById(R.id.installed_device);
        }
    }

    public DeviceRoomAdapter(ArrayList<RoomDataAdapter> mDeviceRoomDataList) {
        this.mDeviceRoomDataList = mDeviceRoomDataList;
    }

    @NonNull
    @Override
    public DeviceRoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room,viewGroup,false);
        DeviceRoomViewHolder drvh = new DeviceRoomViewHolder(v);
        return drvh;

    }

    @Override
    public void onBindViewHolder(@NonNull DeviceRoomViewHolder deviceRoomViewHolder, int i) {
        RoomDataAdapter currentItem = mDeviceRoomDataList.get(i);
        deviceRoomViewHolder.mTextViewRoom.setText(currentItem.getRoomName());
        deviceRoomViewHolder.mTextViewInstalledDevice.setText(currentItem.getInstallerdDevice());
    }

    @Override
    public int getItemCount() {
        return mDeviceRoomDataList.size();
        //return 0;
    }
}
