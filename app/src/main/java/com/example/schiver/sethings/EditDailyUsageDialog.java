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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditDailyUsageDialog extends AppCompatDialogFragment {
    private EditText inputPass,inputConfirmPass;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    String homeId,editUsaget;
    SeekBar averagePerDaySeekBar;
    TextView averageValue;
    boolean next = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_daily_usage,null);

        homeId = getArguments().getString("editHomeID");
        averagePerDaySeekBar = view.findViewById(R.id.daily_average_seekbar);
        averageValue = view.findViewById(R.id.seekbar_value);
        averagePerDaySeekBar.setMax(50);
        averagePerDaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                averageValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Daily Usage");
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Daily Usage".length(),
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
                        updateUsageSetting(averageValue.getText().toString(),homeId);
                        //Toast.makeText(getContext(),"Username : "+userData,Toast.LENGTH_SHORT).show();
                        //submitEditName(inputName.getText().toString(),userData);
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
       dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               dbRef.child("dailyAverage").setValue(Integer.parseInt(energy));
               Toast.makeText(getContext(),"Daily average has been set",Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }

}
