package com.example.konyvesbolt;

public class ShoppingItem {
    private String name;
    private String info;
    private float ratedInfo;
    private final int imageResource;

    public ShoppingItem(String name, String info, float ratedInfo, int imageResource) {
        this.name = name;
        this.info = info;
        this.ratedInfo = ratedInfo;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public float getRatedInfo() {
        return ratedInfo;
    }

    public int getImageResource() {
        return imageResource;
    }
}
