package com.example.schiver.sethings.Model;

public class ConfigDeviceAdapterData {
    private int mImageResource;
    private String deviceName, deviceID, deviceInfo, devicrType;

    public ConfigDeviceAdapterData() {
    }

    public ConfigDeviceAdapterData(int mImageResource, String deviceName, String deviceID, String deviceInfo, String devicrType) {
        this.mImageResource = mImageResource;
        this.deviceName = deviceName;
        this.deviceID = deviceID;
        this.deviceInfo = deviceInfo;
        this.devicrType = devicrType;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getDevicrType() {
        return devicrType;
    }
}
