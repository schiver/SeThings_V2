package com.example.schiver.sethings.Model;

public class DashboardData {
    String room, percentage, installedDevice;

    public DashboardData(){

    }
    public DashboardData(String room, String percentage, String installedDevice) {
        this.room = room;
        this.percentage = percentage;
        this.installedDevice = installedDevice;
    }

    public String getRoom() {
        return room;
    }

    public String getPercentage() {
        return percentage;
    }

    public String getInstalledDevice() {
        return installedDevice;
    }
}
