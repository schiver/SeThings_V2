package com.example.schiver.sethings.Model;

public class UsageAdapterData {
    private String roomName,roomUsage;

    public UsageAdapterData(String roomName, String roomUsage) {
        this.roomName = roomName;
        this.roomUsage = roomUsage;
    }

    public UsageAdapterData() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomUsage() {
        return roomUsage;
    }

    public void setRoomUsage(String roomUsage) {
        this.roomUsage = roomUsage;
    }
}
