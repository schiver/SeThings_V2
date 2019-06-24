package com.example.schiver.sethings.Model;

public class DashboardData {
    String room, percentage, installedDevice;
    float totalUsage;

    public DashboardData(){

    }

    public DashboardData(String room, String percentage, String installedDevice, float totalUsage) {
        this.room = room;
        this.percentage = percentage;
        this.installedDevice = installedDevice;
        this.totalUsage = totalUsage;
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

    public float getTotalUsage() {
        return totalUsage;
    }
}
