package com.codingchallenge.adtech.entity.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "click_details")
public class ClickEntity {

    @Id
    @Column(name = "click_id")
    private UUID clickId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column
    private Date time;

    public UUID getClickId() {
        return clickId;
    }

    public void setClickId(UUID clickId) {
        this.clickId = clickId;
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
