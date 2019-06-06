package com.example.schiver.sethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmptyRoomFragment extends Fragment {
    TextView mLabel;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_empty_room,container,false);
        mLabel = rootView.findViewById(R.id.labelKosong);
        mLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-TEST");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // Load data here
                    DevicesFragment devicesFragment = new DevicesFragment();
                    FragmentManager mFragmentManager = getFragmentManager();
                    FragmentTransaction mFragmentTransaction  = mFragmentManager.beginTransaction().
                            replace(R.id.fragment_container,devicesFragment,DevicesFragment.class.getSimpleName());
                    mFragmentTransaction.addToBackStack(null).commit();
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void openDialog(){
        RoomDialog roomDialog = new RoomDialog();
        roomDialog.show(getFragmentManager(),"TEST");
    }


}
