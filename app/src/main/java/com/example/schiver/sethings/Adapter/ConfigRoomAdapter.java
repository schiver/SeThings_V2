package com.example.schiver.sethings.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.ConfigActivity;
import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Model.ConfigDeviceDataCondition;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfigRoomAdapter extends RecyclerView.Adapter<ConfigRoomAdapter.ConfigRoomViewHolder> {
    private ArrayList<RoomAdapterData> mDeviceRoomDataList;
    Context myContext;

    public class ConfigRoomViewHolder extends  RecyclerView.ViewHolder {
        public TextView mTextViewRoom;
        public TextView mTextViewInstalledDevice;
        public CardView mRoomCardView;
        public Switch mSwitch;

        public ConfigRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRoom = itemView.findViewById(R.id.room_name);
            mTextViewInstalledDevice = itemView.findViewById(R.id.usage_view);
            mRoomCardView = itemView.findViewById(R.id.cardHolder);
            mSwitch= itemView.findViewById(R.id.switch1);
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
    public void onBindViewHolder(@NonNull final ConfigRoomViewHolder configRoomViewHolder, int i) {
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
        String conditionSwitch = SharedPref.readSharedPref(myContext,currentItem.getRoomName(),"");
        if(conditionSwitch.equals("true")){
            configRoomViewHolder.mSwitch.setChecked(true);
        }else{
            configRoomViewHolder.mSwitch.setChecked(false);
        }
        configRoomViewHolder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(buttonView.getContext(),"Switch ON All Device",Toast.LENGTH_SHORT).show();
                    SharedPref.saveSharefPref(buttonView.getContext(),currentItem.getRoomName(),"true");
                    switchOnAllDevices(currentItem.getRoomName(),buttonView.getContext());
                }else{
                    Toast.makeText(buttonView.getContext(),"Switch OFF All Device",Toast.LENGTH_SHORT).show();
                    SharedPref.saveSharefPref(buttonView.getContext(),currentItem.getRoomName(),"false");
                    switchOffAllDevices(currentItem.getRoomName(),buttonView.getContext());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceRoomDataList.size();
    }

    public void switchOnAllDevices(String roomName, final Context context){
        FirebaseDatabase myDb = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = myDb.getReference("SeThings-Config/"+roomName);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    ConfigDeviceData myConfig = ds.getValue(ConfigDeviceData.class);
                    if(myConfig.getDeviceType().equals("Output")){
                        //Toast.makeText(context,ds.getKey(),Toast.LENGTH_SHORT).show();
                        dbRef.child(ds.getKey()).child("deviceCondition").setValue("Configured");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase myDb2 = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef2 = myDb2.getReference("SeThings-Detail_Config/"+roomName);
        final ConfigDeviceDataCondition myCondition = new ConfigDeviceDataCondition(
                "SWITCH_ON",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#");
        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    dbRef2.child(ds.getKey()).setValue(myCondition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void switchOffAllDevices(String roomName, final Context context){
        FirebaseDatabase myDb = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = myDb.getReference("SeThings-Config/"+roomName);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    ConfigDeviceData myConfig = ds.getValue(ConfigDeviceData.class);
                    if(myConfig.getDeviceType().equals("Output")){
                        //Toast.makeText(context,ds.getKey(),Toast.LENGTH_SHORT).show();
                        dbRef.child(ds.getKey()).child("deviceCondition").setValue("#");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase myDb2 = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef2 = myDb2.getReference("SeThings-Detail_Config/"+roomName);
        final ConfigDeviceDataCondition myCondition = new ConfigDeviceDataCondition(
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#");
        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    dbRef2.child(ds.getKey()).setValue(myCondition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
