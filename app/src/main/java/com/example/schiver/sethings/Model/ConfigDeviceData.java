package com.example.schiver.sethings.Model;

public class ConfigDeviceData {
    private int icon;
    private String deviceID, deviceType,deviceName;
    private String deviceEvent, deviceCondition,getDeviceConditionConnected,deviceAction,
                   deviceActionStart,deviceActionEnd;

    public ConfigDeviceData() {
    }

    public ConfigDeviceData(int icon, String deviceID, String deviceType, String deviceName, String deviceEvent, String deviceCondition, String getDeviceConditionConnected, String deviceAction, String deviceActionStart, String deviceActionEnd) {
        this.icon = icon;
        this.deviceID = deviceID;
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.deviceEvent = deviceEvent;
        this.deviceCondition = deviceCondition;
        this.getDeviceConditionConnected = getDeviceConditionConnected;
        this.deviceAction = deviceAction;
        this.deviceActionStart = deviceActionStart;
        this.deviceActionEnd = deviceActionEnd;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceEvent() {
        return deviceEvent;
    }

    public void setDeviceEvent(String deviceEvent) {
        this.deviceEvent = deviceEvent;
    }

    public String getDeviceCondition() {
        return deviceCondition;
    }

    public void setDeviceCondition(String deviceCondition) {
        this.deviceCondition = deviceCondition;
    }

    public String getGetDeviceConditionConnected() {
        return getDeviceConditionConnected;
    }

    public void setGetDeviceConditionConnected(String getDeviceConditionConnected) {
        this.getDeviceConditionConnected = getDeviceConditionConnected;
    }

    public String getDeviceAction() {
        return deviceAction;
    }

    public void setDeviceAction(String deviceAction) {
        this.deviceAction = deviceAction;
    }

    public String getDeviceActionStart() {
        return deviceActionStart;
    }

    public void setDeviceActionStart(String deviceActionStart) {
        this.deviceActionStart = deviceActionStart;
    }

    public String getDeviceActionEnd() {
        return deviceActionEnd;
    }

    public void setDeviceActionEnd(String deviceActionEnd) {
        this.deviceActionEnd = deviceActionEnd;
    }
}
