package com.example.konyvesbolt;

public class ShoppingItem {
    private String name;
    private String subtitle;
    private String prize;
    private float ratedInfo;
    private final int imageResource;

    public ShoppingItem(String name, String subtitle, String prize, float ratedInfo, int imageResource) {
        this.name = name;
        this.subtitle = subtitle;
        this.prize = prize;
        this.ratedInfo = ratedInfo;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPrize() {
        return prize;
    }

    public float getRatedInfo() {
        return ratedInfo;
    }

    public int getImageResource() {
        return imageResource;
    }
}
