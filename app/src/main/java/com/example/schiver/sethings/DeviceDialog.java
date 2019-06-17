package com.example.schiver.sethings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Model.ConfigDeviceDataCondition;
import com.example.schiver.sethings.Model.DeviceListData;
import com.example.schiver.sethings.Model.DeviceUsageData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceDialog extends AppCompatDialogFragment {
    private EditText inputDeviceID;
    private Spinner optionType,optionName;
    private FirebaseDatabase myDb;
    private DatabaseReference dbRef;
    private FirebaseDatabase myDb2;
    private DatabaseReference dbRef2;
    private FirebaseDatabase myDb3;
    private DatabaseReference dbRef3;
    private FirebaseDatabase myDb4;
    private DatabaseReference dbRef4;
    private int iconName;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_device,null);
        // Get room name from shared pref
        final String roomName = SharedPref.readSharedPref(getContext(),"Room","");

        // Setting up input for dialog
        inputDeviceID = view.findViewById(R.id.input_device_id);
        optionName = view.findViewById(R.id.spinner_name);
        optionType = view.findViewById(R.id.spinner_condition);

        // Create default list for dropdown input dialog
        List<String> list = new ArrayList<String>();
        list.add("Select option below");
        list.add("Sensor");
        list.add("Ouput");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        optionType.setAdapter(dataAdapter);
        List<String> list2 = new ArrayList<String>();
        list2.add("Select option below");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        optionName.setAdapter(dataAdapter2);

        // Set on selected item event
        optionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        List<String> list = new ArrayList<String>();
                        list.add("Motion Sensor");
                        list.add("Temperature Sensor");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        optionName.setAdapter(dataAdapter);
                        // Adding some values to Spinner Name Sensor
                        break;
                    case 2:
                        List<String> list2 = new ArrayList<String>();
                        list2.add("Lamp");
                        list2.add("Fan");
                        list2.add("Air Conditioner");
                        list2.add("PC / Laptop");
                        list2.add("Screen / Television");
                        list2.add("AC Remote");
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list2);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter2.notifyDataSetChanged();
                        optionName.setAdapter(dataAdapter2);
                        //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        // Adding some values to Spinner Name Output
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        optionName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = parent.getItemAtPosition(position).toString();
                switch (choice){
                    case "Motion Sensor" :
                        iconName = R.drawable.ic_motion_sensor;
                        break;
                    case "Temperature Sensor" :
                        iconName = R.drawable.ic_temp;
                        break;
                    case "Lamp" :
                        iconName = R.drawable.ic_lamp;
                        break;
                    case "Fan" :
                        iconName = R.drawable.ic_fan;
                        break;
                    case "Air Conditioner" :
                        iconName = R.drawable.ic_air_conditioner;
                        break;
                    case "PC / Laptop" :
                        iconName = R.drawable.ic_laptop;
                        break;
                    case "Screen / Television" :
                        iconName = R.drawable.ic_screen;
                        break;
                    case "AC Remote" :
                        iconName = R.drawable.ic_remote;
                        break;
                    default:
                        iconName = 0;
                }
                //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Add new device");
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Add new device".length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setView(view)
                .setTitle(ssBuilder)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitDevice(
                                roomName,
                                inputDeviceID.getText().toString(),
                                optionType.getSelectedItem().toString(),
                                optionName.getSelectedItem().toString()
                        );
                        addDetailConfig(roomName,inputDeviceID.getText().toString());

                    }
        });
        final AlertDialog myDialog = builder.create();
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                myDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#005aac"));
                myDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#005aac"));

            }
        });
        return myDialog;
    }

    public void submitDevice(String room,String deviceId,String deviceType,String deviceName){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device2/"+room+"/");
        String deviceInfo = "Installed";
        final DeviceListData deviceData = new DeviceListData(iconName,deviceId,deviceType,deviceName,deviceInfo);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.child(deviceData.getDeviceID()).setValue(deviceData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myDb2 = FirebaseDatabase.getInstance();
        dbRef2 = myDb2.getReference("SeThings-Config/"+room+"/");
        final ConfigDeviceData myConfig = new ConfigDeviceData(
                                                iconName,
                                                deviceId,
                                                deviceType,
                                                deviceName,
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
                dbRef2.child(myConfig.getDeviceID()).setValue(myConfig);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myDb4 = FirebaseDatabase.getInstance();
        dbRef4 = myDb4.getReference("SeThings-Device_Usage/"+room+"/");
        final DeviceUsageData myUsageData = new DeviceUsageData(
                iconName,
                deviceId,
                deviceType,
                deviceName,
                deviceInfo,
                0
        );
        dbRef4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef4.child(myUsageData.getDeviceID()).setValue(myUsageData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addDetailConfig(String room, final String deviceId){
        myDb3  = FirebaseDatabase.getInstance();
        dbRef3 = myDb3.getReference("SeThings-Detail_Config/"+room+"/");
        final ConfigDeviceDataCondition detailCondition = new ConfigDeviceDataCondition(
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#"
        );
        dbRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef3.child(deviceId).setValue(detailCondition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }




}
