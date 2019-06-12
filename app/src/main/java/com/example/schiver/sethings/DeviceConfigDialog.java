package com.example.schiver.sethings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class DeviceConfigDialog extends AppCompatDialogFragment /* implements  TimePickerDialog.OnTimeSetListener*/{
    TimePickerDialog.OnTimeSetListener from_dateListener,to_dateListener;
    private String deviceID,deviceName,deviceType;
    private int deviceIcon;
    private String roomName;
    private Spinner sConnected;
    private Spinner sCondition;
    private Spinner sSubCondition;
    private Spinner sAction;
    private SeekBar sConditionValue;
    private TextView sensorValue,labelName;
    private ImageView mDeviceIcon;
    private Button timerPicker,timerStart,timerEnd, subTimerPicker, subTimerStart, subTimerEnd;
    private RadioGroup radioOption;
    private EditText timerInput,subTimerInput;
    private EditText inputStart, inputEnd, subInputStart, subInputEnd;
    private int hour,minutes;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_config,null);

        // get arguments
        final String roomName = SharedPref.readSharedPref(getContext(),"Room","");
        deviceID = getArguments().getString("devID");
        deviceName = getArguments().getString("devName");
        deviceType = getArguments().getString("devType");
        deviceIcon = getArguments().getInt("devIcon");

        sConnected = view.findViewById(R.id.spinner_connected);
        sCondition = view.findViewById(R.id.spinner_condition);
        sSubCondition = view.findViewById(R.id.spinner_sub_condition);
        sAction = view.findViewById(R.id.spinner_action);
        sConditionValue = view.findViewById(R.id.conditionValue);
        sensorValue = view.findViewById(R.id.sensor_value);
        labelName = view.findViewById(R.id.labelName);
        mDeviceIcon = view.findViewById(R.id.device_config_icon);
        timerPicker = view.findViewById(R.id.time_picker);
        timerInput = view.findViewById(R.id.timer_input);
        inputStart = view.findViewById(R.id.scheduled_input_start);
        inputEnd = view.findViewById(R.id.scheduled_input_end);
        timerStart = view.findViewById(R.id.scheduled_picker_start);
        timerEnd = view.findViewById(R.id.scheduled_picker_end);

        subTimerPicker = view.findViewById(R.id.sub_time_picker);
        subTimerInput = view.findViewById(R.id.sub_timer_input);
        subInputStart = view.findViewById(R.id.sub_scheduled_input_start);
        subInputEnd = view.findViewById(R.id.sub_scheduled_input_end);
        subTimerStart = view.findViewById(R.id.sub_scheduled_picker_start);
        subTimerEnd = view.findViewById(R.id.sub_scheduled_picker_end);
        radioOption = view.findViewById(R.id.radio_group);

        radioOption.setVisibility(View.GONE);
        sCondition.setVisibility(View.INVISIBLE);
        timerInput.setVisibility(View.GONE);
        inputStart.setVisibility(View.GONE);
        inputEnd.setVisibility(View.GONE);
        timerPicker.setVisibility(View.GONE);
        timerStart.setVisibility(View.GONE);
        timerEnd.setVisibility(View.GONE);

        subTimerInput.setVisibility(View.GONE);
        subInputStart.setVisibility(View.GONE);
        subInputEnd.setVisibility(View.GONE);
        subTimerPicker.setVisibility(View.GONE);
        subTimerStart.setVisibility(View.GONE);
        subTimerEnd.setVisibility(View.GONE);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Device configuration");

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Device configuration".length(),
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
                //Toast.makeText(getContext(), "ID to be connected "+SharedPref.readSharedPref(getContext(),"myConfigID",""), Toast.LENGTH_SHORT).show();
                //deleteDevice(roomName,deviceID);
            }
        });

        sConditionValue.setMax(35);
        sensorValue.setText("0");
        labelName.setText(deviceName);
        mDeviceIcon.setImageResource(deviceIcon);
        sConditionValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valueNow = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueNow = progress;
                sensorValue.setText(String.valueOf(valueNow));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        final List<String> list = new ArrayList<String>();
        final List<String> listID = new ArrayList<String>();
        final List<String> myCondition = new ArrayList<String>();

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get data sensor from current room
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Config/"+roomName);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.add("Select option below"); listID.add("*");
                list.add("None"); listID.add("#");

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ConfigDeviceData myConfigData = ds.getValue(ConfigDeviceData.class);
                    //list.add(myConfigData.getDeviceName());
                    if(myConfigData.getDeviceType().equals("Sensor")){
                        list.add(myConfigData.getDeviceName());
                        listID.add(myConfigData.getDeviceID());
                    }
                }
                dataAdapter.notifyDataSetChanged();
                sConnected.setAdapter(dataAdapter);




            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sConnected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPref.saveSharefPref(getContext(),"myConfigID",listID.get(position));
                String choice = listID.get(position);
                String otherChoice = sConnected.getSelectedItem().toString();
                if(choice.equals("#") || choice.equals("*")){
                    sCondition.setVisibility(View.VISIBLE);
                    sConditionValue.setVisibility(View.INVISIBLE);
                    sensorValue.setVisibility(View.INVISIBLE);
                    radioOption.setVisibility(View.GONE);
                    inputStart.setVisibility(View.GONE);
                    inputEnd.setVisibility(View.GONE);
                    timerStart.setVisibility(View.GONE);
                    timerEnd.setVisibility(View.GONE);
                    timerInput.setVisibility(View.GONE);
                    timerPicker.setVisibility(View.GONE);
                }else{
                    if(otherChoice.equals("Temperature Sensor")){
                        timerInput.setVisibility(View.GONE);
                        timerPicker.setVisibility(View.GONE);
                        sCondition.setVisibility(View.INVISIBLE);
                        sConditionValue.setVisibility(View.VISIBLE);
                        sensorValue.setVisibility(View.VISIBLE);
                        radioOption.setVisibility(View.GONE);
                        inputStart.setVisibility(View.GONE);
                        inputEnd.setVisibility(View.GONE);
                        timerStart.setVisibility(View.GONE);
                        timerEnd.setVisibility(View.GONE);
                        List<String> mySubCondition1 = new ArrayList<String>();
                        ArrayAdapter<String> dataAdapterSubCondition = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, mySubCondition1);
                        dataAdapterSubCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySubCondition1.add("Select option below");
                        mySubCondition1.add("Timer");
                        mySubCondition1.add("Scheduled");
                        dataAdapterSubCondition.notifyDataSetChanged();
                        sSubCondition.setAdapter(dataAdapterSubCondition);
                    }else{
                        timerInput.setVisibility(View.GONE);
                        timerPicker.setVisibility(View.GONE);
                        radioOption.setVisibility(View.VISIBLE);
                        sCondition.setVisibility(View.GONE);
                        sConditionValue.setVisibility(View.GONE);
                        sensorValue.setVisibility(View.GONE);
                        inputStart.setVisibility(View.GONE);
                        inputEnd.setVisibility(View.GONE);
                        timerStart.setVisibility(View.GONE);
                        timerEnd.setVisibility(View.GONE);
                        List<String> mySubCondition1 = new ArrayList<String>();
                        ArrayAdapter<String> dataAdapterSubCondition = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, mySubCondition1);
                        dataAdapterSubCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySubCondition1.add("Select option below");
                        mySubCondition1.add("Timer");
                        mySubCondition1.add("Scheduled");
                        dataAdapterSubCondition.notifyDataSetChanged();
                        sSubCondition.setAdapter(dataAdapterSubCondition);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> dataAdapterCondition = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, myCondition);
        dataAdapterCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myCondition.add("Select option below");
        myCondition.add("Timer");
        myCondition.add("Scheduled");
        dataAdapterCondition.notifyDataSetChanged();
        sCondition.setAdapter(dataAdapterCondition);
        sCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String conditionChoice = sCondition.getSelectedItem().toString();
                if(conditionChoice.equals("Timer")){
                    timerInput.setVisibility(View.VISIBLE);
                    timerPicker.setVisibility(View.VISIBLE);
                    inputStart.setVisibility(View.GONE);
                    inputEnd.setVisibility(View.GONE);
                    timerStart.setVisibility(View.GONE);
                    timerEnd.setVisibility(View.GONE);
                    List<String> mySubCondition1 = new ArrayList<String>();
                    ArrayAdapter<String> dataAdapterSubCondition = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, mySubCondition1);
                    dataAdapterSubCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySubCondition1.add("Select option below");
                    mySubCondition1.add("None");
                    dataAdapterSubCondition.notifyDataSetChanged();
                    sSubCondition.setAdapter(dataAdapterSubCondition);
                }else if(conditionChoice.equals("Scheduled")){
                    timerPicker.setVisibility(View.GONE);
                    timerInput.setVisibility(View.GONE);
                    inputStart.setVisibility(View.VISIBLE);
                    inputEnd.setVisibility(View.VISIBLE);
                    timerStart.setVisibility(View.VISIBLE);
                    timerEnd.setVisibility(View.VISIBLE);

                    List<String> mySubCondition1 = new ArrayList<String>();
                    ArrayAdapter<String> dataAdapterSubCondition = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, mySubCondition1);
                    dataAdapterSubCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySubCondition1.add("Select option below");
                    mySubCondition1.add("None");
                    dataAdapterSubCondition.notifyDataSetChanged();
                    sSubCondition.setAdapter(dataAdapterSubCondition);
                }else{
                    timerPicker.setVisibility(View.GONE);
                    timerInput.setVisibility(View.GONE);
                    inputStart.setVisibility(View.GONE);
                    inputEnd.setVisibility(View.GONE);
                    timerStart.setVisibility(View.GONE);
                    timerEnd.setVisibility(View.GONE);

                    List<String> mySubCondition1 = new ArrayList<String>();
                    ArrayAdapter<String> dataAdapterSubCondition = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, mySubCondition1);
                    dataAdapterSubCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySubCondition1.add("Select option below");
                    mySubCondition1.add("None");
                    dataAdapterSubCondition.notifyDataSetChanged();
                    sSubCondition.setAdapter(dataAdapterSubCondition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sSubCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String subConditionChoice;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subConditionChoice = sSubCondition.getSelectedItem().toString();
                if(subConditionChoice.equals("Timer")){
                    subTimerInput.setVisibility(View.VISIBLE);
                    subTimerPicker.setVisibility(View.VISIBLE);
                    subInputStart.setVisibility(View.GONE);
                    subInputEnd.setVisibility(View.GONE);
                    subTimerStart.setVisibility(View.GONE);
                    subTimerEnd.setVisibility(View.GONE);
                }else if(subConditionChoice.equals("Scheduled")){
                    subTimerInput.setVisibility(View.GONE);
                    subTimerPicker.setVisibility(View.GONE);
                    subInputStart.setVisibility(View.VISIBLE);
                    subInputEnd.setVisibility(View.VISIBLE);
                    subTimerStart.setVisibility(View.VISIBLE);
                    subTimerEnd.setVisibility(View.VISIBLE);
                }else{
                    subTimerInput.setVisibility(View.GONE);
                    subTimerPicker.setVisibility(View.GONE);
                    subInputStart.setVisibility(View.GONE);
                    subInputEnd.setVisibility(View.GONE);
                    subTimerStart.setVisibility(View.GONE);
                    subTimerEnd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timerPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(getContext(), 3, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;
                        timerInput.setText(String.valueOf(hour)+" Hour "+String.valueOf(minutes)+" Minutes");
                        timerInput.setEnabled(false);
                    }
                }, hour, minutes, true);
                timePicker.show();

            }
        });
        subTimerPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(getContext(), 3, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;
                        subTimerInput.setText(String.valueOf(hour)+" Hour "+String.valueOf(minutes)+" Minutes");
                        subTimerInput.setEnabled(false);
                    }
                }, hour, minutes, true);
                timePicker.show();
            }
        });
        final List<String> myAction = new ArrayList<String>();
        final ArrayAdapter<String> dataAdapterAction = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, myAction);
        dataAdapterAction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myAction.add("Select option below");
        myAction.add("Once");
        myAction.add("Repeat");
        dataAdapterCondition.notifyDataSetChanged();
        sAction.setAdapter(dataAdapterAction);
        return myDialog;
    }

    /*@Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        minutes = minute;
        timerInput.setText(String.valueOf(hour)+" Hour "+String.valueOf(minutes)+" Minutes");
        timerInput.setEnabled(false);
    }*/


}
