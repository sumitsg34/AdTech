package com.codingchallenge.adtech.entity.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "install_entity")
public class InstallEntity {

    @Id
    @Column(name = "install_id")
    private UUID installId;

    @Column(name = "click_id")
    private UUID clickId;

    @Column
    private Date time;

    public UUID getInstallId() {
        return installId;
    }

    public void setInstallId(UUID installId) {
        this.installId = installId;
    }

    public UUID getClickId() {
        return clickId;
    }

    public void setClickId(UUID clickId) {
        this.clickId = clickId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
