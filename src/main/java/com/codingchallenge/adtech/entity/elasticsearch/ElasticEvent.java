package com.codingchallenge.adtech.entity.elasticsearch;

import com.codingchallenge.adtech.constant.ServiceConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Document(indexName = ServiceConstants.INDEX_NAME)
public class ElasticEvent implements Serializable {


    @Id
    private UUID eventId;

    private UUID clickId;
    private UUID installId;
    private UUID deliveryId;
    private UUID advertisementId;

    private Date time;

    @Field(type = FieldType.Keyword)
    private EventTypeEnum eventType; //delivery, click, install

    //meta data
    @Field(type = FieldType.Keyword)
    private String browser;
    @Field(type = FieldType.Keyword)
    private String os;
    @Field(type = FieldType.Keyword)
    private String site;

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getClickId() {
        return clickId;
    }

    public void setClickId(UUID clickId) {
        this.clickId = clickId;
    }

    public UUID getInstallId() {
        return installId;
    }

    public void setInstallId(UUID installId) {
        this.installId = installId;
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public UUID getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(UUID advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
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
