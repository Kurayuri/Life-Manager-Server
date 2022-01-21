package com.trashparadise.lifemanagerserver.bean;

public class Answer {
    private int state;
    private String data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Answer(int state, String data) {
        this.state = state;
        this.data = data;
    }
}
