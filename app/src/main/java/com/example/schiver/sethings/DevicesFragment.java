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
import android.widget.Toast;

import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.Model.RoomListData;
import com.example.schiver.sethings.Utils.DeviceOperation;
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
    List<String> test = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_devices,container,false);
        //final ArrayList<RoomAdapterData> roomDataList = new ArrayList<>();

        /*roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));*/

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
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Room");
        dbRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               int index = 0;
               final ArrayList<Integer> countDevice = new ArrayList<>();
               if(dataSnapshot.exists()){
                   // Load data here
                   roomDataList.clear();
                   for (DataSnapshot ds : dataSnapshot.getChildren()){
                       final RoomListData dataRoom = ds.getValue(RoomListData.class);
                       myDb2 = FirebaseDatabase.getInstance();
                       dbRef2 = myDb2.getReference("SeThings-Device2/"+dataRoom.getRoomName());
                       dbRef2.addValueEventListener(new ValueEventListener() {
                           ArrayList<Integer> devCount = new ArrayList<>();
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               for (DataSnapshot ds : dataSnapshot.getChildren()){
                                   devCount.add((int) dataSnapshot.getChildrenCount());
                               }
                               roomDataList.add(new RoomAdapterData(dataRoom.getRoomName() ,String.valueOf(devCount.size())));
                               mRecyclerView.setAdapter(mAdapter);
                               mAdapter.notifyDataSetChanged();
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                       //roomDataList.add(new RoomAdapterData(dataRoom.getRoomName() ,"1"));
                       //mRecyclerView.setAdapter(mAdapter);
                       //mAdapter.notifyDataSetChanged();
                   }
               }else{
                   /*EmptyRoomFragment emptyRoomFragment = new EmptyRoomFragment();
                   FragmentManager mFragmentManager = getFragmentManager();
                   FragmentTransaction mFragmentTransaction  = mFragmentManager.beginTransaction().
                           replace(R.id.fragment_container,emptyRoomFragment,EmptyRoomFragment.class.getSimpleName());
                   mFragmentTransaction.addToBackStack(null).commit();*/
                   //Toast.makeText(getContext(), "Data Tidak Ada", Toast.LENGTH_SHORT).show();
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    public int getDeviceCount(){
        final ArrayList<Integer> devCount = new ArrayList<>();
        FirebaseDatabase myDb = FirebaseDatabase.getInstance();

        DatabaseReference dbRef = myDb.getReference("SeThings-Device2/Livingroom");
        ValueEventListener a = dbRef.addValueEventListener(new ValueEventListener() {
            int result;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    devCount.add((int) dataSnapshot.getChildrenCount());
                }
                result = devCount.size();
                Toast.makeText(getContext(), "Data Tidak Ada : "+String.valueOf(result), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        //Toast.makeText(getContext(), "Data Tidak Ada : "+String.valueOf(result[0]), Toast.LENGTH_SHORT).show();
        return 0;
    }

}
