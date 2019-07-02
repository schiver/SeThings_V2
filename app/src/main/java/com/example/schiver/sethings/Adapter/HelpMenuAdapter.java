package com.example.schiver.sethings.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schiver.sethings.Model.SettingsMenu;
import com.example.schiver.sethings.ProfileActivity;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.UsageSettingActivity;

import java.util.ArrayList;

public class HelpMenuAdapter extends RecyclerView.Adapter<HelpMenuAdapter.HelpMenuViewHolder> {
    private ArrayList<SettingsMenu> mSettingsMenu = new ArrayList<>();
    private Context myContext;

    public class HelpMenuViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextMenuName;
        private ImageView mIconMenu;
        public RelativeLayout mMenuLink;

        public HelpMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextMenuName = itemView.findViewById(R.id.question);
            mIconMenu = itemView.findViewById(R.id.icon_menu);
            mMenuLink = itemView.findViewById(R.id.container_menu);
        }
    }

    public HelpMenuAdapter(ArrayList<SettingsMenu> mSettingsMenu, Context myContext) {
        this.mSettingsMenu = mSettingsMenu;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public HelpMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_help_menu,viewGroup,false);
        HelpMenuViewHolder svh = new HelpMenuViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpMenuViewHolder settingsMenuViewHolder, int i) {
        final SettingsMenu currentItem = mSettingsMenu.get(i);
        settingsMenuViewHolder.mTextMenuName.setText(currentItem.getMenuName());
        settingsMenuViewHolder.mIconMenu.setImageResource(currentItem.getIcon());
        settingsMenuViewHolder.mMenuLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSettingsMenu.size();
    }
}
