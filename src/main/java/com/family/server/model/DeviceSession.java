package com.family.server.model;

import java.sql.Timestamp;
import java.util.UUID;

public class DeviceSession {
    private UUID id;
    private UUID deviceId;
    private String jwtID;
    private Timestamp issuedAt;
    private Boolean revoked;

    public DeviceSession(){}
    public DeviceSession(UUID id, UUID deviceId, String jwtID, Timestamp issuedAt, Boolean revoked)
    {
        this.id = id;
        this.deviceId = deviceId;
        this.jwtID = jwtID;
        this.issuedAt = issuedAt;
        this.revoked = revoked;
    }

    public UUID getId() {return id;}
    public UUID getDeviceId() {return deviceId;}
    public String getJwtID() {return jwtID;}
    public Timestamp getIssuedAt() {return issuedAt;}
    public Boolean getRevoked() {return revoked;}

    public void setId(UUID id) {this.id = id;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setJwtID(String jwtID) {this.jwtID = jwtID;}
    public void setIssuedAt(Timestamp issuedAt) {this.issuedAt = issuedAt;}
    public void setRevoked(Boolean revoked) {this.revoked = revoked;}
}
