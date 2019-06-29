package com.example.schiver.sethings.Model;

public class UsageConfigData {
    int dailyAverage,energy;

    public UsageConfigData() {
    }

    public UsageConfigData(int dailyAverage, int energy) {
        this.dailyAverage = dailyAverage;
        this.energy = energy;
    }

    public int getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(int dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
