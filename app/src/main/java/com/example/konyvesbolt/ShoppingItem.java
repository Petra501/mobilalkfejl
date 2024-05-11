package com.example.konyvesbolt;

public class ShoppingItem {
    private String id;
    private String name;
    private String subtitle;
    private String prize;
    private float ratedInfo;
    private int imageResource;
    private int cartedCount;

    public ShoppingItem(String name, String subtitle, String prize, float ratedInfo, int imageResource, int cartedCount) {
        this.name = name;
        this.subtitle = subtitle;
        this.prize = prize;
        this.ratedInfo = ratedInfo;
        this.imageResource = imageResource;
        this.cartedCount = cartedCount;
    }

    public ShoppingItem() {
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
    public int getCartedCount() {
        return cartedCount;
    }
    public String _getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
