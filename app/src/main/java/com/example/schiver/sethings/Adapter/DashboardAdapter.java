package com.example.schiver.sethings.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schiver.sethings.Model.DashboardData;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.UsageDetailActivity;
import com.example.schiver.sethings.Utils.SharedPref;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {
    private ArrayList<DashboardData> mDashboardDataList;
    private Context myContext;
    public class DashboardViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewRoom;
        public TextView mTextViewUsage;
        public TextView mTextViewDevice;
        public TextView mDetailsView;
        public CardView cardHolder;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.room_name);
            mTextViewDevice = itemView.findViewById(R.id.usage_view);
            mTextViewUsage = itemView.findViewById(R.id.usage_percentage);
            mDetailsView = itemView.findViewById(R.id.details_button);
            cardHolder = itemView.findViewById(R.id.cardHolder);
        }
    }

    public DashboardAdapter(ArrayList<DashboardData> mDashboardDataList , Context context) {
        this.mDashboardDataList = mDashboardDataList;
        this.myContext = context;
    }

    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dashboard,viewGroup,false);
        DashboardViewHolder dvh = new DashboardViewHolder(v);
        return dvh;

    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder dashboardViewHolder, int i) {
        final DashboardData currentItem = mDashboardDataList.get(i);
        dashboardViewHolder.mTextViewRoom.setText(currentItem.getRoom());
        dashboardViewHolder.mTextViewUsage.setText(currentItem.getPercentage());
        dashboardViewHolder.mTextViewDevice.setText(currentItem.getInstalledDevice());
        dashboardViewHolder.mDetailsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(myContext,UsageDetailActivity.class);
                SharedPref.saveSharefPref(myContext,"Room",currentItem.getRoom());
                SharedPref.saveSharefPref(myContext,"RoomUsage",String.valueOf(currentItem.getTotalUsage()));
                myContext.startActivity(testIntent);
            }
        });
        dashboardViewHolder.cardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(myContext,UsageDetailActivity.class);
                SharedPref.saveSharefPref(myContext,"Room",currentItem.getRoom());
                SharedPref.saveSharefPref(myContext,"RoomUsage",String.valueOf(currentItem.getTotalUsage()));
                myContext.startActivity(testIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDashboardDataList.size();
    }

}
