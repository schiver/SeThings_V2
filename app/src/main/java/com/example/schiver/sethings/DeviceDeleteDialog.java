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
import android.widget.Toast;

import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeviceDeleteDialog extends AppCompatDialogFragment {
    private String deviceID;
    private String roomName;
    private FirebaseDatabase myDb;
    private DatabaseReference dbRef;
    private FirebaseDatabase myDb2;
    private DatabaseReference dbRef2;
    private FirebaseDatabase myDb3;
    private DatabaseReference dbRef3;
    private FirebaseDatabase myDb4;
    private DatabaseReference dbRef4;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete_device,null);
        deviceID = getArguments().getString("devID");
        roomName = SharedPref.readSharedPref(getContext(),"Room","");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Delete confirmation");

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Delete confirmation".length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setView(view)
                .setTitle(ssBuilder)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "Delete "+deviceID+"Room : "+roomName, Toast.LENGTH_SHORT).show();
                deleteDevice(roomName,deviceID);
                deleteConfig(roomName,deviceID);
                deleteDetailConfig(roomName,deviceID);
                deleteUsage(roomName,deviceID);
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
    public void deleteDevice(String room, final String devID){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device2/"+roomName+"/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.child(devID).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteConfig(String room, final String devID){
        myDb2 = FirebaseDatabase.getInstance();
        dbRef2 = myDb2.getReference("SeThings-Config/"+roomName+"/");
        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef2.child(devID).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void deleteDetailConfig(String room, final String devID){
        myDb3 = FirebaseDatabase.getInstance();
        dbRef3 = myDb3.getReference("SeThings-Detail_Config/"+roomName+"/");
        dbRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef3.child(devID).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteUsage(String room, final String devID){
        myDb4 = FirebaseDatabase.getInstance();
        dbRef4 = myDb3.getReference("SeThings-Device_Usage/"+roomName+"/");
        dbRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef4.child(devID).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
