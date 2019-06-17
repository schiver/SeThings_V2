package com.example.schiver.sethings.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schiver.sethings.DeviceListActivity;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.Utils.SharedPref;

import java.util.ArrayList;

public class DeviceRoomAdapter extends RecyclerView.Adapter<DeviceRoomAdapter.DeviceRoomViewHolder> {
    private ArrayList<RoomAdapterData> mDeviceRoomDataList;
    Context myContext;

    public class DeviceRoomViewHolder extends  RecyclerView.ViewHolder{
        public TextView mTextViewRoom;
        public TextView mTextViewInstalledDevice;
        public CardView mRoomCardView;
        public DeviceRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.room_name);
            mTextViewInstalledDevice = itemView.findViewById(R.id.usage_view);
            mRoomCardView = itemView.findViewById(R.id.cardHolder);
        }
    }

    public DeviceRoomAdapter(ArrayList<RoomAdapterData> mDeviceRoomDataList, Context myContext) {
        this.mDeviceRoomDataList = mDeviceRoomDataList;
        this.myContext = myContext;
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
        final RoomAdapterData currentItem = mDeviceRoomDataList.get(i);
        deviceRoomViewHolder.mTextViewRoom.setText(currentItem.getRoomName());
        deviceRoomViewHolder.mTextViewInstalledDevice.setText(currentItem.getInstallerdDevice());
        deviceRoomViewHolder.mRoomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(myContext,DeviceListActivity.class);
                SharedPref.saveSharefPref(myContext,"Room",currentItem.getRoomName());

                myContext.startActivity(testIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceRoomDataList.size();
        //return 0;
    }
}
