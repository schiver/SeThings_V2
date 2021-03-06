package com.example.schiver.sethings.Model;

public class DeviceAdapterData {
    private int mImageResource;
    private String deviceName, deviceID, deviceInfo;

    public DeviceAdapterData() {
    }

    public DeviceAdapterData(int mImageResource, String deviceName, String deviceID, String deviceInfo) {
        this.mImageResource = mImageResource;
        this.deviceName = deviceName;
        this.deviceID = deviceID;
        this.deviceInfo = deviceInfo;
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
}
