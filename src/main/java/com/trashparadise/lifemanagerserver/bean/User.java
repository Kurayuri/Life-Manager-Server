package com.trashparadise.lifemanagerserver.bean;

public class User {
    String uuid;
    String username;
    String password;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String uuid, String username, String password) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
    }
}
