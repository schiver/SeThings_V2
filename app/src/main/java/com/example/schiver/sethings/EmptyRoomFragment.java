package com.example.schiver.sethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EmptyRoomFragment extends Fragment {
    TextView mLabel;
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

    public void openDialog(){
        RoomDialog roomDialog = new RoomDialog();
        roomDialog.show(getFragmentManager(),"TEST");
    }
}
