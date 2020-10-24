package com.codingchallenge.adtech.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ClickEvent implements Serializable {

    private static final long serialVersionUID = 1l;

    private Date time;
    private UUID clickId;
    private UUID deliveryId;


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

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
}
