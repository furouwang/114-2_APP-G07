package com.example.myapplication.model;

public class TurtleSpot {
    private String locationName;
    private String intro;
    private String details;
    private String mainSpecies; // 👈 增加了這個，用來儲存該地點的主要海龜品種

    // 修改構造方法，把 mainSpecies 放進去
    public TurtleSpot(String locationName, String intro, String details, String mainSpecies) {
        this.locationName = locationName;
        this.intro = intro;
        this.details = details;
        this.mainSpecies = mainSpecies;
    }

    public String getLocationName() { return locationName; }
    public String getIntro() { return intro; }
    public String getDetails() { return details; }
    public String getMainSpecies() { return mainSpecies; } // 👈 增加了這個
}