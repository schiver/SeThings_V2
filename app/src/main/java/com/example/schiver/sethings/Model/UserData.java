package com.example.schiver.sethings.Model;

public class UserData {
    private String Email,Username,Password,Name,HomeID;

    public UserData(String email, String username, String password, String name, String homeID) {
        Email = email;
        Username = username;
        Password = password;
        Name = name;
        HomeID = homeID;
    }

    public UserData(){

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHomeID() {
        return HomeID;
    }

    public void setHomeID(String homeID) {
        HomeID = homeID;
    }
}
