package com.example.schiver.sethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schiver.sethings.Adapter.ConfigRoomAdapter;
import com.example.schiver.sethings.Adapter.DeviceRoomAdapter;
import com.example.schiver.sethings.Model.RoomAdapterData;
import com.example.schiver.sethings.Model.RoomListData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfigFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<RoomAdapterData> roomDataList = new ArrayList<>();
    ArrayList<String> dataRoomName = new ArrayList<>();
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    FirebaseDatabase myDb2;
    DatabaseReference dbRef2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_config,container,false);

        /*roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));
        roomDataList.add(new RoomAdapterData("Livingroom","2"));*/

        mRecyclerView = rootView.findViewById(R.id.room_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        mAdapter = new ConfigRoomAdapter(roomDataList,getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        myDb2 = FirebaseDatabase.getInstance();
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
}
