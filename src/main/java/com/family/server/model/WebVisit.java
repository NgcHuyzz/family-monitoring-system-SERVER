package com.family.server.model;

import java.sql.Timestamp;
import java.util.UUID;

public class WebVisit {
    private UUID id;
    private UUID deviceId;
    private String domain;
    private String url;
    private Timestamp startAt;
    private int durationSec;
    private Timestamp createAt;

    public WebVisit() {}
    public WebVisit(UUID id, UUID deviceId, String appName, String windowTitle, Timestamp startAt, int durationSec, Timestamp createAt)
    {
        this.id = id;
        this.deviceId = deviceId;
        this.domain = appName;
        this.url = windowTitle;
        this.startAt = startAt;
        this.durationSec = durationSec;
        this.createAt = createAt;
    }

    public UUID getId() {return id;}
    public UUID getDeviceId() {return deviceId;}
    public String getAppName() {return domain;}
    public String getWindowTitle() {return url;}
    public Timestamp getStartAt() {return startAt;}
    public int getDurationSec() {return durationSec;}
    public Timestamp getCreateAt() {return createAt;}

    public void setId(UUID id) {this.id = id;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setAppName(String appName) {this.domain = appName;}
    public void setWindowTitle(String windowTitle) {this.url = windowTitle;}
    public void setStartAt(Timestamp startAt) {this.startAt = startAt;}
    public void setDurationSec(int durationSec) {this.durationSec = durationSec;}
    public void setCreateAt(Timestamp createAt) {this.createAt = createAt;}
}
