package com.example.schiver.sethings.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.schiver.sethings.Model.UsageAdapterData;
import com.example.schiver.sethings.R;

import java.util.ArrayList;

public class RoomUsageAdapter extends RecyclerView.Adapter<RoomUsageAdapter.RoomUsageViewHolder> {
    private ArrayList<UsageAdapterData> mDeviceRoomUsage;
    Context myContext;

    public class RoomUsageViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewRoom;
        public TextView mTextViewUsage;
        public Button mButton;
        public RoomUsageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.room_name);
            mTextViewUsage = itemView.findViewById(R.id.usage_view);
            mButton = itemView.findViewById(R.id.button_check);
        }

    }

    public RoomUsageAdapter(ArrayList<UsageAdapterData> mDeviceRoomUsage, Context myContext) {
        this.mDeviceRoomUsage = mDeviceRoomUsage;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public RoomUsageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room_usage,viewGroup,false);
        RoomUsageAdapter.RoomUsageViewHolder druvh = new RoomUsageAdapter.RoomUsageViewHolder(v);
        return druvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomUsageViewHolder roomUsageViewHolder, int i) {
        final UsageAdapterData currentItem = mDeviceRoomUsage.get(i);
        roomUsageViewHolder.mTextViewRoom.setText(currentItem.getRoomName());
        roomUsageViewHolder.mTextViewUsage.setText(currentItem.getRoomUsage());
    }

    @Override
    public int getItemCount() {
        return mDeviceRoomUsage.size();
    }
}
