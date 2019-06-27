package com.example.schiver.sethings.Model;

public class SettingsMenu {
    String menuName,menuCaption;
    int icon;

    public SettingsMenu() {
    }

    public SettingsMenu(String menuName, String menuCaption, int icon) {
        this.menuName = menuName;
        this.menuCaption = menuCaption;
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuCaption() {
        return menuCaption;
    }

    public int getIcon() {
        return icon;
    }
}
