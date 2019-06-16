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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.DeviceConfigDialog;
import com.example.schiver.sethings.DeviceEditDialog;
import com.example.schiver.sethings.Model.ConfigDeviceAdapterData;
import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Model.ConfigDeviceDataCondition;
import com.example.schiver.sethings.Model.DeviceAdapterData;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeviceConfigAdapter extends RecyclerView.Adapter<DeviceConfigAdapter.DeviceConfigViewHolder> {
    FirebaseDatabase myDb;
    DatabaseReference dbRef;

    FirebaseDatabase myDb2;
    DatabaseReference dbRef2;

    FirebaseDatabase myDb3;
    DatabaseReference dbRef3;

    FirebaseDatabase myDb4;
    DatabaseReference dbRef4;
    //Context mContext;
    private ArrayList<ConfigDeviceAdapterData> mDeviceList;
    public class DeviceConfigViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeviceIcon;
        public TextView mDeviceID;
        public TextView mDeviceName;
        public TextView mDeviceInfo;
        public CardView mContainer;
        public Switch mSwitch;
        public DeviceConfigViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceIcon = itemView.findViewById(R.id.device_icon);
            mDeviceName = itemView.findViewById(R.id.device_name);
            mDeviceID   = itemView.findViewById(R.id.device_id);
            mDeviceInfo = itemView.findViewById(R.id.device_information);
            mContainer  = itemView.findViewById(R.id.cardConfig);
            mSwitch = itemView.findViewById(R.id.switch_config);
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
    public void onBindViewHolder(@NonNull final DeviceConfigViewHolder deviceConfigViewHolder, int i) {
        final ConfigDeviceAdapterData currentItem = mDeviceList.get(i);
        deviceConfigViewHolder.mDeviceIcon.setImageResource(currentItem.getmImageResource());
        deviceConfigViewHolder.mDeviceID.setText(currentItem.getDeviceID());
        deviceConfigViewHolder.mDeviceName.setText(currentItem.getDeviceName());
        deviceConfigViewHolder.mDeviceInfo.setText(currentItem.getDeviceInfo());
        if(currentItem.getDevicrType().equals("Sensor")){
            deviceConfigViewHolder.mSwitch.setVisibility(View.GONE);
            /*deviceConfigViewHolder.mSwitch.setChecked(true);
            deviceConfigViewHolder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    deviceConfigViewHolder.mSwitch.setChecked(true);
                }
            });*/
        }else{
            if(currentItem.getDeviceInfo().equals("Configured")){
                deviceConfigViewHolder.mSwitch.setChecked(true);
            }else{
                deviceConfigViewHolder.mSwitch.setChecked(false);
            }
            deviceConfigViewHolder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Context context = buttonView.getContext();
                    if(isChecked){
                        //Toast.makeText(buttonView.getContext(),"Switch ON",Toast.LENGTH_SHORT).show();
                        switchOnConfig(
                                context,
                                SharedPref.readSharedPref(buttonView.getContext(),"Room",""),
                                currentItem.getDeviceID(),
                                "#",
                                "#",
                                "Configured",
                                currentItem.getDeviceID(),
                                currentItem.getDeviceName(),
                                currentItem.getDevicrType(),
                                currentItem.getmImageResource()
                        );
                        switchOnDetailConfig(
                                context,
                                SharedPref.readSharedPref(buttonView.getContext(),"Room",""),
                                currentItem.getDeviceID()
                        );
                    }else{
                        //Toast.makeText(buttonView.getContext(),"Switch OFF",Toast.LENGTH_SHORT).show();
                        switchOffConfig(
                                context,
                                SharedPref.readSharedPref(buttonView.getContext(),"Room",""),
                                currentItem.getDeviceID(),
                                "#",
                                "#",
                                "#",
                                currentItem.getDeviceID(),
                                currentItem.getDeviceName(),
                                currentItem.getDevicrType(),
                                currentItem.getmImageResource()
                        );
                        switchOffDetailConfig(
                                context,
                                SharedPref.readSharedPref(buttonView.getContext(),"Room",""),
                                currentItem.getDeviceID()
                        );
                    }
                }
            });
        }
        deviceConfigViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentItem.getDevicrType().equals("Sensor")){
                    //Toast.makeText(v.getContext(),"Type"+currentItem.getDevicrType(),Toast.LENGTH_SHORT).show();
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
        param.putString("devType",deviceType);
        param.putInt("devIcon",deviceIcon);
        configDialog.setArguments(param);
        configDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"CONFIG");
    }

    public void switchOnConfig(Context context, String roomName, final String deviceID, String connectedDevice, String deviceAction, String deviceCondition, String deviceEvent, String deviceName, String deviceType, int deviceIcon){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Config/"+roomName+"/");
        final ConfigDeviceData myConfigData = new ConfigDeviceData(
                deviceIcon,
                deviceID,
                deviceType,
                deviceName,
                deviceEvent,
                deviceCondition,
                connectedDevice,
                deviceAction,
                "#",
                "#"
        );
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.child(deviceID).setValue(myConfigData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*Toast.makeText(context,"RoomName : "+roomName,Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Device-ID : "+deviceId,Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Switch ON",Toast.LENGTH_SHORT).show();*/
    }

    public void switchOnDetailConfig(Context context, String roomName, final String deviceId){
        myDb2 = FirebaseDatabase.getInstance();
        dbRef2 = myDb2.getReference("SeThings-Detail_Config/"+roomName+"/");
        final ConfigDeviceDataCondition myCondition = new ConfigDeviceDataCondition(
                "SWITCH_ON",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#"
        );
        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef2.child(deviceId).setValue(myCondition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void switchOffConfig(Context context, String roomName, final String deviceID, String connectedDevice, String deviceAction, String deviceCondition, String deviceEvent, String deviceName, String deviceType, int deviceIcon){
        myDb3 = FirebaseDatabase.getInstance();
        dbRef3 = myDb3.getReference("SeThings-Config/"+roomName);
        final ConfigDeviceData myConfigData = new ConfigDeviceData(
                deviceIcon,
                deviceID,
                deviceType,
                deviceName,
                deviceEvent,
                deviceCondition,
                connectedDevice,
                deviceAction,
                "#",
                "#"
        );
        dbRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef3.child(deviceID).setValue(myConfigData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*Toast.makeText(context,"RoomName : "+roomName,Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Device-ID : "+deviceId,Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Switch OFF ",Toast.LENGTH_SHORT).show();*/
    }

    public void switchOffDetailConfig(Context context, String roomName, final String deviceId){
        myDb4 = FirebaseDatabase.getInstance();
        dbRef4 = myDb4.getReference("SeThings-Detail_Config/"+roomName+"/");
        final ConfigDeviceDataCondition myCondition = new ConfigDeviceDataCondition(
                "SWITCH_OFF",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#"
        );
        dbRef4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef4.child(deviceId).setValue(myCondition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
