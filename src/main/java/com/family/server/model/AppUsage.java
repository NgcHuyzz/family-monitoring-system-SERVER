package com.family.server.model;

import java.sql.Timestamp;
import java.util.UUID;

public class AppUsage {
    private UUID id;
    private UUID deviceId;
    private String appName;
    private Timestamp startAt;
    private int durationSec;
    private Timestamp createAt;

    public AppUsage() {}
    public AppUsage(UUID id, UUID deviceId, String appName, Timestamp startAt, int durationSec, Timestamp createAt)
    {
        this.id = id;
        this.deviceId = deviceId;
        this.appName = appName;
        this.startAt = startAt;
        this.durationSec = durationSec;
        this.createAt = createAt;
    }

    public UUID getId() {return id;}
    public UUID getDeviceId() {return deviceId;}
    public String getAppName() {return appName;}
    public Timestamp getStartAt() {return startAt;}
    public int getDurationSec() {return durationSec;}
    public Timestamp getCreateAt() {return createAt;}

    public void setId(UUID id) {this.id = id;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setAppName(String appName) {this.appName = appName;}
    public void setStartAt(Timestamp startAt) {this.startAt = startAt;}
    public void setDurationSec(int durationSec) {this.durationSec = durationSec;}
    public void setCreateAt(Timestamp createAt) {this.createAt = createAt;}
}
