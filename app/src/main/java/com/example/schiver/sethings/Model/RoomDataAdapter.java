package com.example.schiver.sethings.Model;

import java.util.ArrayList;

public class RoomDataAdapter {
    private String roomName , installerdDevice;

    public RoomDataAdapter(ArrayList<RoomDataAdapter> roomDataList){

    }

    public RoomDataAdapter(String roomName, String installerdDevice) {
        this.roomName = roomName;
        this.installerdDevice = installerdDevice;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getInstallerdDevice() {
        return installerdDevice;
    }
}
