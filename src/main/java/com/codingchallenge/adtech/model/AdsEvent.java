package com.codingchallenge.adtech.model;

import java.io.Serializable;
import java.util.UUID;

public class AdsEvent implements Serializable {

    private static final long serialVersionUID = 1l;

    private UUID adId;
    private String owner;
    private float costPerClick;
    private float costPerView;
    private int category; // type code

    public UUID getAdId() {
        return adId;
    }

    public void setAdId(UUID adId) {
        this.adId = adId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public float getCostPerClick() {
        return costPerClick;
    }

    public void setCostPerClick(float costPerClick) {
        this.costPerClick = costPerClick;
    }

    public float getCostPerView() {
        return costPerView;
    }

    public void setCostPerView(float costPerView) {
        this.costPerView = costPerView;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
