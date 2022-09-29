package com.example.flowervallyadmin.model;

public class Banner {
    private String bannerId;
    private String mName;
    private String mImageUrl;

    public Banner() {
        //empty constructor needed
    }

    public Banner(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
    }

    public Banner(String bannerId, String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        bannerId = bannerId;
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getId() {
        return bannerId;
    }

    public void setId(String id) {
        this.bannerId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
