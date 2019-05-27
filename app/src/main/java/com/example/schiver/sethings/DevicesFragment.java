package com.example.schiver.sethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.schiver.sethings.Adapter.DashboardAdapter;
import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Model.RoomDataAdapter;
import com.example.schiver.sethings.Model.RoomListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DevicesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    ArrayList<RoomDataAdapter> roomDataList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_devices,container,false);
        //final ArrayList<RoomDataAdapter> roomDataList = new ArrayList<>();

        /*roomDataList.add(new RoomDataAdapter("Livingroom","2"));
        roomDataList.add(new RoomDataAdapter("Livingroom","2"));
        roomDataList.add(new RoomDataAdapter("Livingroom","2"));
        roomDataList.add(new RoomDataAdapter("Livingroom","2"));
        roomDataList.add(new RoomDataAdapter("Livingroom","2"));
        roomDataList.add(new RoomDataAdapter("Livingroom","2"));*/

        mRecyclerView = rootView.findViewById(R.id.room_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        mAdapter = new DeviceRoomAdapter(roomDataList);
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
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-TEST");
        dbRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   // Load data here
                   roomDataList.clear();
                   for (DataSnapshot ds : dataSnapshot.getChildren()){
                       RoomListData dataRoom = ds.getValue(RoomListData.class);
                       roomDataList.add(new RoomDataAdapter(dataRoom.getRoomName() ,"1"));
                   }
                   mRecyclerView.setAdapter(mAdapter);
               }else{
                   Toast.makeText(getContext(), "Data Tidak Ada", Toast.LENGTH_SHORT).show();
               }
               mAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}
