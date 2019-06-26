package com.example.schiver.sethings.Model;

import java.util.ArrayList;

public class RoomAdapterData {
    private String roomName , installerdDevice;
    boolean switchValue;

    public RoomAdapterData() {
    }

    public RoomAdapterData(String roomName, String installerdDevice) {
        this.roomName = roomName;
        this.installerdDevice = installerdDevice;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getInstallerdDevice() {
        return installerdDevice;
    }

    public boolean isSwitchValue() {
        return switchValue;
    }
}
