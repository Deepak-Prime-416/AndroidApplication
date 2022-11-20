package com.example.androidapplication.model;

public class Fragment {

    private String title;
    private int activeTime;
    private int restTime;

    public Fragment(String title, int activeTime, int restTime) {
        this.title = title;
        this.activeTime = activeTime;
        this.restTime = restTime;
    }

    public String getTitle() {
        return title;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public int getRestTime() {
        return restTime;
    }

}
