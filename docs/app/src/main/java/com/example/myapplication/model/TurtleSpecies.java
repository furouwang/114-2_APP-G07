package com.example.myapplication.model;

public class TurtleSpecies {
    private String name;
    private String subTitle;
    private String features;
    private String status;
    private int imageResource; // 👈 增加了這個，用來儲存 drawable 圖片 ID

    // 修改了這個構造方法，增加了 imageResource
    public TurtleSpecies(String name, String subTitle, String features, String status, int imageResource) {
        this.name = name;
        this.subTitle = subTitle;
        this.features = features;
        this.status = status;
        this.imageResource = imageResource;
    }

    public String getName() { return name; }
    public String getSubTitle() { return subTitle; }
    public String getFeatures() { return features; }
    public String getStatus() { return status; }
    public int getImageResource() { return imageResource; } // 👈 增加了這個 Getter
}
