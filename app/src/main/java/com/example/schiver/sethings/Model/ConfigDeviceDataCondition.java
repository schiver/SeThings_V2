package com.example.schiver.sethings.Model;

public class ConfigDeviceDataCondition {
    String devCondition,devConditionTimerDuration,
           devConditionStartScheduled,devConditionEndScheduled;
    String devSubCondition,devSubConditioTimerDuration,
           devSubConditionStartScheduled,devSubConditionEndScheduled;

    public ConfigDeviceDataCondition() {
    }

    public ConfigDeviceDataCondition(String devCondition, String devConditionTimerDuration, String devConditionStartScheduled, String devConditionEndScheduled, String devSubCondition, String devSubConditioTimerDuration, String devSubConditionStartScheduled, String devSubConditionEndScheduled) {
        this.devCondition = devCondition;
        this.devConditionTimerDuration = devConditionTimerDuration;
        this.devConditionStartScheduled = devConditionStartScheduled;
        this.devConditionEndScheduled = devConditionEndScheduled;
        this.devSubCondition = devSubCondition;
        this.devSubConditioTimerDuration = devSubConditioTimerDuration;
        this.devSubConditionStartScheduled = devSubConditionStartScheduled;
        this.devSubConditionEndScheduled = devSubConditionEndScheduled;
    }

    public String getDevCondition() {
        return devCondition;
    }

    public String getDevConditionTimerDuration() {
        return devConditionTimerDuration;
    }

    public String getDevConditionStartScheduled() {
        return devConditionStartScheduled;
    }

    public String getDevConditionEndScheduled() {
        return devConditionEndScheduled;
    }

    public String getDevSubCondition() {
        return devSubCondition;
    }

    public String getDevSubConditioTimerDuration() {
        return devSubConditioTimerDuration;
    }

    public String getDevSubConditionStartScheduled() {
        return devSubConditionStartScheduled;
    }

    public String getDevSubConditionEndScheduled() {
        return devSubConditionEndScheduled;
    }
}
