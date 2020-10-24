package com.codingchallenge.adtech.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Statistic implements Serializable {

    private static final long serialVersionUID = 1l;

    @JsonProperty("delivery")
    private long deliveryCount;

    @JsonProperty("click")
    private long clickCount;

    @JsonProperty("install")
    private long installCount;

    public long getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(long deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public long getInstallCount() {
        return installCount;
    }

    public void setInstallCount(long installCount) {
        this.installCount = installCount;
    }
}
