package com.codingchallenge.adtech.entity.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ads_details")
public class AdsEntity {

    @Id
    @Column(name = "ad_id")
    private UUID adId;

    @Column
    private String owner;

    @Column(name = "cost_per_click")
    private float costPerClick;

    @Column(name = "cost_per_view")
    private float costPerView;

    @Column
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
