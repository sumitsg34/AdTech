package com.codingchallenge.adtech.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class DeliveryEvent implements Serializable {

    private static final long serialVersionUID = 1l;

    private UUID adId;
    private UUID deliveryId;
    private Date time;
    private String browser;
    private String os;
    private String site;

    public UUID getAdId() {
        return adId;
    }

    public void setAdId(UUID adId) {
        this.adId = adId;
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

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
