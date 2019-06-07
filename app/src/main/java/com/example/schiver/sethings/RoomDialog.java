package com.example.schiver.sethings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.schiver.sethings.Model.RoomListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomDialog extends AppCompatDialogFragment {
    private EditText inputRoomName;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_room,null);
        inputRoomName = view.findViewById(R.id.input_room_name);


        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Enter room name");

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Enter room name".length(),
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
                        submitRoomData(inputRoomName.getText().toString());
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

    public void submitRoomData(String roomName){
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Room");

        final RoomListData roomData = new RoomListData(roomName);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.child(roomData.getRoomName()).setValue(roomData);
                //Intent intent = getActivity().getIntent();
                //startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
