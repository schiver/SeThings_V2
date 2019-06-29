package com.example.schiver.sethings.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.schiver.sethings.DeviceDeleteDialog;
import com.example.schiver.sethings.DeviceEditDialog;
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
        public TextView mLinkMenu;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceIcon = itemView.findViewById(R.id.device_icon);
            mDeviceName = itemView.findViewById(R.id.device_name);
            mDeviceID   = itemView.findViewById(R.id.device_id);
            mDeviceInfo = itemView.findViewById(R.id.device_information);
            mLinkMenu = itemView.findViewById(R.id.menuLink);
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
    public void onBindViewHolder(@NonNull final DeviceViewHolder deviceViewHolder, int i) {
        final DeviceAdapterData currentItem = mDeviceList.get(i);
        deviceViewHolder.mDeviceIcon.setImageResource(currentItem.getmImageResource());
        deviceViewHolder.mDeviceID.setText(currentItem.getDeviceID());
        deviceViewHolder.mDeviceName.setText(currentItem.getDeviceName());
        deviceViewHolder.mDeviceInfo.setText(currentItem.getDeviceInfo());
        deviceViewHolder.mLinkMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Edit "+currentItem.getDeviceID(), Toast.LENGTH_SHORT).show();
                final View insideView = v;
                PopupMenu popup = new PopupMenu(v.getContext(),deviceViewHolder.mLinkMenu);
                popup.inflate(R.menu.device_menu);
                popup.setGravity(Gravity.END);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuEdit:
                                //handle menu1 click
                                openDialogEdit(insideView.getContext(),currentItem.getDeviceID());
                                //Toast.makeText(insideView.getContext(), "Edit "+currentItem.getDeviceID(), Toast.LENGTH_SHORT).show();
                                //editShowDialog(insideView,currentItem.getmDeviceId(),currentItem.getmText1(),currentItem.getmText2());
                                break;
                            case R.id.menuDelete:
                                //handle menu2 click
                                openDialogDelete(insideView.getContext(),currentItem.getDeviceID());
                                //Toast.makeText(insideView.getContext(), "Delete "+currentItem.getDeviceID(), Toast.LENGTH_SHORT).show();
                                //confirmDelete(currentItem.getmDeviceId());
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    public void openDialogEdit(Context context, String deviceID){
        DeviceEditDialog editDialog = new DeviceEditDialog();
        Bundle param = new Bundle();
        param.putString("devID",deviceID);
        editDialog.setArguments(param);
        editDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"EDIT");
    }

    public void openDialogDelete(Context context, String deviceID){
        DeviceDeleteDialog deleteDialog = new DeviceDeleteDialog();
        Bundle paramDevID = new Bundle();
        paramDevID.putString("devID",deviceID);
        deleteDialog.setArguments(paramDevID);
        deleteDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"DELETE");
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
