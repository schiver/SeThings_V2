package com.example.schiver.sethings.Model;

public class DeviceUsageAdapterData {
    String deviceName,deviceUsage,deviceUsagePercentage;
    int deviceIcon,percentageColor;
    float progressPercentage;

    public DeviceUsageAdapterData() {
    }

    public DeviceUsageAdapterData(String deviceName, String deviceUsage, String deviceUsagePercentage, int deviceIcon, int percentageColor, float progressPercentage) {
        this.deviceName = deviceName;
        this.deviceUsage = deviceUsage;
        this.deviceUsagePercentage = deviceUsagePercentage;
        this.deviceIcon = deviceIcon;
        this.percentageColor = percentageColor;
        this.progressPercentage = progressPercentage;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUsage() {
        return deviceUsage;
    }

    public void setDeviceUsage(String deviceUsage) {
        this.deviceUsage = deviceUsage;
    }

    public String getDeviceUsagePercentage() {
        return deviceUsagePercentage;
    }

    public void setDeviceUsagePercentage(String deviceUsagePercentage) {
        this.deviceUsagePercentage = deviceUsagePercentage;
    }

    public int getDeviceIcon() {
        return deviceIcon;
    }

    public void setDeviceIcon(int deviceIcon) {
        this.deviceIcon = deviceIcon;
    }

    public int getPercentageColor() {
        return percentageColor;
    }

    public void setPercentageColor(int percentageColor) {
        this.percentageColor = percentageColor;
    }

    public float getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(float progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
