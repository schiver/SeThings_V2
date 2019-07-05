package com.example.schiver.sethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.Model.RoomListData;
import com.example.schiver.sethings.Utils.DeviceOperation;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class DevicesFragment extends Fragment {
    private int count;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    FirebaseDatabase myDb2;
    DatabaseReference dbRef2;
    ArrayList<RoomAdapterData> roomDataList = new ArrayList<>();
    ArrayList<String> dataRoomName = new ArrayList<>();
    ProgressBar loadingBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPref.saveSharefPref(getContext(),"page","");
        final View rootView = inflater.inflate(R.layout.fragment_devices,container,false);
        loadingBar = rootView.findViewById(R.id.progressBar);
        mRecyclerView = rootView.findViewById(R.id.room_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        mAdapter = new DeviceRoomAdapter(roomDataList,getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        FloatingActionButton addButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return rootView;
    }

    public void openDialog(){
        RoomDialog roomDialog = new RoomDialog();
        roomDialog.show(getFragmentManager(),"TEST");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadingBar.setVisibility(View.VISIBLE);
        myDb = FirebaseDatabase.getInstance();
        myDb2 = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device2");
        dbRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               roomDataList.clear();
               for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //Toast.makeText(getContext(),ds.getKey(),Toast.LENGTH_SHORT).show();
                   roomDataList.add(new RoomAdapterData(ds.getKey() ,String.valueOf(ds.getChildrenCount())));
               }
               mRecyclerView.setAdapter(mAdapter);
               mAdapter.notifyDataSetChanged();
               loadingBar.setVisibility(View.GONE);
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }



}
