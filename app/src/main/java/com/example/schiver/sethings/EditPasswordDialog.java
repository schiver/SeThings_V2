package com.example.schiver.sethings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schiver.sethings.Utils.AESUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPasswordDialog extends AppCompatDialogFragment {
    private EditText inputPass,inputConfirmPass;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    String userData,nameEdit;
    FirebaseDatabase myDb2;
    DatabaseReference dbRef2;
    boolean next = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_pass,null);

        inputPass = view.findViewById(R.id.input_pass);
        inputConfirmPass = view.findViewById(R.id.input_confirm_pass);
        userData = getArguments().getString("editUName");

        inputConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!inputConfirmPass.getText().toString().equals(inputPass.getText().toString())){
                    inputConfirmPass.setError("Confirm pass didn't match");
                }else{
                    next = true;
                }
            }
        });

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3e4a59"));
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Password edit");
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Password edit".length(),
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
                        if(next == true){
                            dbUpdate(inputPass.getText().toString(),userData);
                            //Toast.makeText(getContext(),"Ready to update",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(),"Edit password failed",Toast.LENGTH_SHORT).show();
                        }

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


   public void dbUpdate(final String pass, String userName){
       myDb = FirebaseDatabase.getInstance();
       dbRef = myDb.getReference("SeThings-Users/"+userName);
       dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               try {
                   dbRef.child("password").setValue(AESUtils.encrypt(pass));
               } catch (Exception e) {
                   e.printStackTrace();
               }
               Toast.makeText(getContext(),"Edit password success",Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }

}
