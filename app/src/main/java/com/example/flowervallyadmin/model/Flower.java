package com.example.flowervallyadmin.model;

public class Flower {
    private String flowerId;
    private String flowerName;
    private String flowerPrice;
    private String flowerQuantity;
    private String flowerDescription;
    private String flowerImageUrl;


    public Flower() {
        //Required constructor
    }

    public Flower(String flowerId, String flowerName, String flowerPrice, String flowerQuantity, String flowerDescription, String flowerImageUrl) {
        this.flowerId = flowerId;
        this.flowerName = flowerName;
        this.flowerPrice = flowerPrice;
        this.flowerQuantity = flowerQuantity;
        this.flowerDescription = flowerDescription;
        this.flowerImageUrl = flowerImageUrl;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerPrice() {
        return flowerPrice;
    }

    public void setFlowerPrice(String flowerPrice) {
        this.flowerPrice = flowerPrice;
    }

    public String getFlowerQuantity() {
        return flowerQuantity;
    }

    public void setFlowerQuantity(String flowerQuantity) {
        this.flowerQuantity = flowerQuantity;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public void setFlowerDescription(String flowerDescription) {
        this.flowerDescription = flowerDescription;
    }

    public String getFlowerImageUrl() {
        return flowerImageUrl;
    }

    public void setFlowerImageUrl(String flowerImageUrl) {
        this.flowerImageUrl = flowerImageUrl;
    }
}
