package com.family.server.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Alert {
    private UUID id;
    private UUID deviceId;
    private UUID policyId;
    private Timestamp ts;
    private String type;
    private Map<String, Object> payload;
    private Boolean acknowledged;
    private Timestamp createAt;

    public Alert() {}
    public Alert(UUID id, UUID deviceId, UUID policyId, Timestamp ts, String type, Map<String, Object> payload, Boolean acknowledged, Timestamp createAt)
    {
        this.id = id;
        this.deviceId = deviceId;
        this.policyId = policyId;
        this.ts = ts;
        this.type = type;
        this.payload = payload;
        this.acknowledged = acknowledged;
        this.createAt = createAt;
    }

    public UUID getId() {return id;}
    public UUID getDeviceId() {return deviceId;}
    public UUID getPolicyId() {return policyId;}
    public Timestamp getTs() {return ts;}
    public String getType() {return type;}
    public Map<String, Object> getPayload() {return payload;}
    public Boolean getAcknowledged() {return acknowledged;}
    public Timestamp getCreateAt() {return createAt;}

    public void setId(UUID id) {this.id = id;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setPolicyId(UUID policyId) {this.policyId = policyId;}
    public void setTs(Timestamp ts) {this.ts = ts;}
    public void setType(String type) {this.type = type;}
    public void setPayload(Map<String, Object> payload) {this.payload =payload;}
    public void setAcknowledged(Boolean acknowledged) {this.acknowledged = acknowledged;}
    public void setCreateAt(Timestamp createAt) {this.createAt = createAt;}
}
