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

import com.example.schiver.sethings.Adapter.ContactMenuAdapter;
import com.example.schiver.sethings.Adapter.HelpMenuAdapter;
import com.example.schiver.sethings.Adapter.SettingsMenuAdapter;
import com.example.schiver.sethings.Model.SettingsMenu;

import java.util.ArrayList;

public class HelpFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SettingsMenu> data = new ArrayList<>();

    private RecyclerView mRecyclerView2;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;
    private ArrayList<SettingsMenu> data2 = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_help,container,false);

        data.add(new SettingsMenu("What is SeThings","",R.drawable.ic_receipt));
        data.add(new SettingsMenu("Setting up device on SeThings","",R.drawable.ic_receipt));
        data.add(new SettingsMenu("Setting up profile","",R.drawable.ic_receipt));
        data.add(new SettingsMenu("Manage your device energy usage","",R.drawable.ic_receipt));
        //Recycle View
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.help_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HelpMenuAdapter(data, getContext());
        mRecyclerView.setAdapter(mAdapter);

        data2.add(new SettingsMenu("enggarswahadika@gmail.com","",R.drawable.ic_mail));
        data2.add(new SettingsMenu("+6281907770604","",R.drawable.ic_phone));

        //Recycle View
        mRecyclerView2 = (RecyclerView) rootView.findViewById(R.id.contact_recycler);
        mLayoutManager2 = new LinearLayoutManager(getContext());
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setNestedScrollingEnabled(false);
        mAdapter2 = new ContactMenuAdapter(data2, getContext());
        mRecyclerView2.setAdapter(mAdapter2);
        return rootView;


    }
}
