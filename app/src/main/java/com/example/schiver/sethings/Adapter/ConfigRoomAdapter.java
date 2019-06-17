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

import com.example.schiver.sethings.ConfigActivity;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.Utils.SharedPref;

import java.util.ArrayList;

public class ConfigRoomAdapter extends RecyclerView.Adapter<ConfigRoomAdapter.ConfigRoomViewHolder> {
    private ArrayList<RoomAdapterData> mDeviceRoomDataList;
    Context myContext;

    public class ConfigRoomViewHolder extends  RecyclerView.ViewHolder {
        public TextView mTextViewRoom;
        public TextView mTextViewInstalledDevice;
        public CardView mRoomCardView;

        public ConfigRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.room_name);
            mTextViewInstalledDevice = itemView.findViewById(R.id.usage_view);
            mRoomCardView = itemView.findViewById(R.id.cardHolder);
        }
    }

    public ConfigRoomAdapter(ArrayList<RoomAdapterData> mDeviceRoomDataList, Context myContext) {
        this.mDeviceRoomDataList = mDeviceRoomDataList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public ConfigRoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room_config,viewGroup,false);
        ConfigRoomAdapter.ConfigRoomViewHolder drvh = new ConfigRoomAdapter.ConfigRoomViewHolder(v);
        return drvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigRoomViewHolder configRoomViewHolder, int i) {
        final RoomAdapterData currentItem = mDeviceRoomDataList.get(i);
        configRoomViewHolder.mTextViewRoom.setText(currentItem.getRoomName());
        configRoomViewHolder.mTextViewInstalledDevice.setText(currentItem.getInstallerdDevice());
        configRoomViewHolder.mRoomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(myContext,ConfigActivity.class);
                SharedPref.saveSharefPref(myContext,"Room",currentItem.getRoomName());
                myContext.startActivity(testIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceRoomDataList.size();
    }
}
