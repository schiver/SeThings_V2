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
import android.widget.Toast;

import com.example.schiver.sethings.Model.SettingsMenu;
import com.example.schiver.sethings.ProfileActivity;
import com.example.schiver.sethings.R;
import com.example.schiver.sethings.UsageSettingActivity;

import java.util.ArrayList;

public class SettingsMenuAdapter extends RecyclerView.Adapter<SettingsMenuAdapter.SettingsMenuViewHolder> {
    private ArrayList<SettingsMenu> mSettingsMenu = new ArrayList<>();
    private Context myContext;

    public class SettingsMenuViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextMenuName,mTextMenuCaption;
        private ImageView mIconMenu;
        public RelativeLayout mMenuLink;
        public SettingsMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextMenuName = itemView.findViewById(R.id.menu_name);
            mTextMenuCaption = itemView.findViewById(R.id.menu_caption);
            mIconMenu = itemView.findViewById(R.id.icon_menu);
            mMenuLink = itemView.findViewById(R.id.container_menu);
        }
    }

    public SettingsMenuAdapter(ArrayList<SettingsMenu> mSettingsMenu, Context myContext) {
        this.mSettingsMenu = mSettingsMenu;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public SettingsMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_setting_menu,viewGroup,false);
        SettingsMenuViewHolder svh = new SettingsMenuViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsMenuViewHolder settingsMenuViewHolder, int i) {
        final SettingsMenu currentItem = mSettingsMenu.get(i);
        settingsMenuViewHolder.mTextMenuName.setText(currentItem.getMenuName());
        settingsMenuViewHolder.mTextMenuCaption.setText(currentItem.getMenuCaption());
        settingsMenuViewHolder.mIconMenu.setImageResource(currentItem.getIcon());
        settingsMenuViewHolder.mMenuLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentItem.getMenuName()){
                    case "Usage Setting" :
                            Intent usageSetting = new Intent(myContext,UsageSettingActivity.class);
                            myContext.startActivity(usageSetting);
                        break;
                    case "Account Setting" :
                            Intent accountSetting = new Intent(myContext,ProfileActivity.class);
                            myContext.startActivity(accountSetting);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSettingsMenu.size();
    }
}
