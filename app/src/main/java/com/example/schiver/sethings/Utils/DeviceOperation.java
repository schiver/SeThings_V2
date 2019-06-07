package com.example.schiver.sethings.Utils;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeviceOperation {
    private String roomName;
    private int count;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    public DeviceOperation() {
    }

    public DeviceOperation(String roomName) {
        this.roomName = roomName;
    }

    public int getDeviceCount(){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device2/Livingroom");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return count;
    }
}
