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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.schiver.sethings.Model.ConfigDeviceData;
import com.example.schiver.sethings.Model.ConfigDeviceDataCondition;
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
    private RadioButton radioChoice;
    private EditText timerInput,subTimerInput;
    private EditText inputStart, inputEnd, subInputStart, subInputEnd;
    private int hour,minutes;
    private FirebaseDatabase myDb;
    private DatabaseReference dbRef;
    private FirebaseDatabase myDb2;
    private DatabaseReference dbRef2;
    private TextView labelCondition;
    int valueNow = 0;
    private String postCondition="#", postConditionDuration="#", postConditionStart="#", postConditionEnd="#";
    private String postSubCondition="#", postSubConditionDuration="#", postSubConditionStart="#", postSubConditionEnd="#";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_config,null);

        // get arguments
        final String roomName = SharedPref.readSharedPref(getContext(),"Room","");
        deviceID = getArguments().getString("devID");
        deviceName = getArguments().getString("devName");
        deviceType = getArguments().getString("devType");
        deviceIcon = getArguments().getInt("devIcon");

        labelCondition = view.findViewById(R.id.textView6);
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
                        submitConfig(roomName,deviceID,SharedPref.readSharedPref(getContext(),"myConfigID",""),sAction.getSelectedItem().toString(),"Configured",deviceID,deviceName,deviceType,deviceIcon);
                        getDetailConfig(view,roomName,deviceID);
                        SharedPref.saveSharefPref(getContext(),roomName,"true");
                        //Toast.makeText(getContext(), "Duration : "+deviceType, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "Duration : "+String.valueOf(hour)+" "+String.valueOf(minutes), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "SubCondition : "+sSubCondition.getSelectedItem(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "ID to be connected "+SharedPref.readSharedPref(getContext(),"myConfigID",""), Toast.LENGTH_SHORT).show();
                        //deleteDevice(roomName,deviceID);
                    }
                });

        sConditionValue.setMax(35);
        sensorValue.setText("0");
        labelName.setText(deviceName);
        mDeviceIcon.setImageResource(deviceIcon);
        sConditionValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    labelCondition.setText("Condition");
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
                        labelCondition.setText("Set temperature");
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

                        dataAdapterSubCondition.notifyDataSetChanged();
                        sSubCondition.setAdapter(dataAdapterSubCondition);
                    }else{
                        labelCondition.setText("Motion detected");
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
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog startSchedule = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour_x,minutes_x;
                        hour = hourOfDay;
                        minutes = minute;
                        if(hour < 10) {
                            hour_x = "0"+hour;
                        }else{
                            hour_x = String.valueOf(hour);
                        }

                        if(minutes < 10) {
                            minutes_x = "0"+minutes;
                        }else{
                            minutes_x = String.valueOf(minutes);
                        }
                        inputStart.setText(hour_x+":"+minutes_x);
                        inputStart.setEnabled(false);
                    }
                },hour,minutes,true);
                startSchedule.show();
            }
        });
        timerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog endSchedule = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour_x,minutes_x;
                        hour = hourOfDay;
                        minutes = minute;
                        if(hour < 10) {
                            hour_x = "0"+hour;
                        }else{
                            hour_x = String.valueOf(hour);
                        }

                        if(minutes < 10) {
                            minutes_x = "0"+minutes;
                        }else{
                            minutes_x = String.valueOf(minutes);
                        }
                        inputEnd.setText(hour_x+":"+minutes_x);
                        inputEnd.setEnabled(false);
                    }
                },hour,minutes,true);
                endSchedule.show();
            }
        });
        subTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog startSchedule = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour_x,minutes_x;
                        hour = hourOfDay;
                        minutes = minute;
                        if(hour < 10) {
                            hour_x = "0"+hour;
                        }else{
                            hour_x = String.valueOf(hour);
                        }

                        if(minutes < 10) {
                            minutes_x = "0"+minutes;
                        }else{
                            minutes_x = String.valueOf(minutes);
                        }
                        subInputStart.setText(hour_x+":"+minutes_x);
                        subInputStart.setEnabled(false);
                    }
                },hour,minutes,true);
                startSchedule.show();
            }
        });
        subTimerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog startSchedule = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour_x,minutes_x;
                        hour = hourOfDay;
                        minutes = minute;
                        if(hour < 10) {
                            hour_x = "0"+hour;
                        }else{
                            hour_x = String.valueOf(hour);
                        }

                        if(minutes < 10) {
                            minutes_x = "0"+minutes;
                        }else{
                            minutes_x = String.valueOf(minutes);
                        }
                        subInputEnd.setText(hour_x+":"+minutes_x);
                        subInputEnd.setEnabled(false);
                    }
                },hour,minutes,true);
                startSchedule.show();
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

    public void submitConfig(String roomName, final String deviceID, String connectedDevice, String deviceAction, String deviceCondition, String deviceEvent, String deviceName, String deviceType, int deviceIcon){
        // Grab all variable here

        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Config/"+roomName);
        final ConfigDeviceData myConfig = new ConfigDeviceData(
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
                dbRef.child(deviceID).setValue(myConfig);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void submitDetailConfig(String roomName, String deviceID, String devCondition, String devConStart, String devConEnd, String devTimeDuration, String devSubCondition, String devSubConStart, String devSubConEnd, String devSubTimeDuration){
        final String myID = deviceID;
        myDb2 = FirebaseDatabase.getInstance();
        dbRef2 = myDb2.getReference("SeThings-Detail_Config/"+roomName+"/");
        final ConfigDeviceDataCondition myDetailCondition = new ConfigDeviceDataCondition(
                                                                devCondition,
                                                                devTimeDuration,
                                                                devConStart,
                                                                devConEnd,
                                                                devSubCondition,
                                                                devSubTimeDuration,
                                                                devSubConStart,
                                                                devSubConEnd);
        //Toast.makeText(getContext(),"Condition : "+devCondition,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"Room Name : "+roomName,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"ID : "+myID,Toast.LENGTH_SHORT).show();
        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef2.child(myID).setValue(myDetailCondition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDetailConfig(final View view, String roomName, String deviceID){
        String connectChoice = sConnected.getSelectedItem().toString();
        if(connectChoice.equals("Temperature Sensor")){
            postCondition = "SENSOR_TEMP_VAL_"+valueNow;
        }else if(connectChoice.equals("Motion Sensor")){
            int selectedChoice = radioOption.getCheckedRadioButtonId();
            radioChoice = view.findViewById(selectedChoice);
            postCondition = "SENSOR_MOTION_VAL_"+radioChoice.getText().toString().toUpperCase();
        }else if(connectChoice.equals("None")) {
            postCondition = sCondition.getSelectedItem().toString().toUpperCase();
        }

        if(connectChoice.equals("Temperature Sensor") || connectChoice.equals("Motion Sensor") ){
            // get Sub Condition
            postSubCondition = sSubCondition.getSelectedItem().toString().toUpperCase();
        }

        if(postCondition.equals("TIMER")){
            int secConversion = (hour*3600) + (minutes*60);
            postConditionDuration = String.valueOf(secConversion);
        }else if(postCondition.equals("SCHEDULED")){
            postConditionStart = inputStart.getText().toString();
            postConditionEnd = inputEnd.getText().toString();
        }

        if(postSubCondition.equals("TIMER")){
            int setSubConversion = (hour*3600) + (minutes*60);
            postSubConditionDuration = String.valueOf(setSubConversion);
        }else if(postSubCondition.equals("SCHEDULED")){
            postSubConditionStart = subInputStart.getText().toString();
            postSubConditionEnd = subInputEnd.getText().toString();
        }else{
            postSubCondition = "NO_CONDITION";
        }

        if(postCondition.equals("NO_CONDITION") && postSubCondition.equals("NO_CONDITION")){
            Toast.makeText(getContext(),"Please select atleast 1 condition",Toast.LENGTH_LONG).show();
        }else{
            submitDetailConfig(roomName,deviceID,postCondition,postConditionStart,postConditionEnd,postConditionDuration,postSubCondition,postSubConditionStart,postSubConditionEnd,postSubConditionDuration);
        }

        //Toast.makeText(getContext(),"Condition : "+postConditionDuration,Toast.LENGTH_LONG).show();
    }



}
