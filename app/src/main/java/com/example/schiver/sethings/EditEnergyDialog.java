package com.example.schiver.sethings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schiver.sethings.Utils.AESUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditEnergyDialog extends AppCompatDialogFragment {
    private EditText inputPass,inputConfirmPass;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    String homeId,editUsaget;
    Spinner energyOption;
    boolean next = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_energy,null);
        energyOption = view.findViewById(R.id.spinner_energy);
        List<String> list = new ArrayList<String>();
        list.add("900 VA");
        list.add("1300 VA");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        energyOption.setAdapter(dataAdapter);
        homeId = getArguments().getString("editHomeID");


        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Home power edit");
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Home power edir".length(),
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
                        updateUsageSetting(energyOption.getSelectedItem().toString(),homeId);
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


   public void updateUsageSetting(final String energy, String homeId){
       myDb = FirebaseDatabase.getInstance();
       dbRef = myDb.getReference("SeThings-Usage_Config/"+homeId);
       final String dataUpdate[] = energy.split(" ");
       //Toast.makeText(getContext(),"Energy : "+dataUpdate[0],Toast.LENGTH_SHORT).show();
       dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.child("energy").setValue(Integer.parseInt(dataUpdate[0]));
                Toast.makeText(getContext(),"Home energy has been set",Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }

}
