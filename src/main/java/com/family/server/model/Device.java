package com.family.server.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class Device {
    private UUID id;
    private String name;
    private String lastIP;
    private UUID userId;
    private Timestamp registeredAt;
    private Timestamp lastSeenAt;

    public Device() {
    }

    public Device(UUID id, String name, String lastIP, UUID userId, Timestamp registeredAt, Timestamp lastSeenAt) {
        this.id = id;
        this.name = name;
        this.lastIP = lastIP;
        this.userId = userId;
        this.registeredAt = registeredAt;
        this.lastSeenAt = lastSeenAt;
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getLastIP() {return lastIP;}
    public UUID getUserId() {return userId;}
    public Timestamp getRegisteredAt() {return registeredAt;}
    public Timestamp getLastSeenAt() {return lastSeenAt;}

    public void setId(UUID id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLastIP(String lastIP) {this.lastIP = lastIP;}
    public void setUserId(UUID userId) {this.userId = userId;}
    public void setRegisteredAt(Timestamp registeredAt) {this.registeredAt = registeredAt;}
    public void setLastSeenAt(Timestamp lastSeenAt) {this.lastSeenAt = lastSeenAt;}
}
