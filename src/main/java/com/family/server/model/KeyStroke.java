package com.family.server.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class KeyStroke {
    private UUID id;
    private UUID deviceId;
    private byte[] textEnc;
    private byte[] iv;
    private Timestamp createAt;

    public KeyStroke() {}
    public KeyStroke(UUID id, UUID deviceId, byte[] textEnc, byte[] iv, Timestamp createAt)
    {
        this.id = id;
        this.deviceId = deviceId;
        this.textEnc = textEnc;
        this.iv = iv;
        this.createAt = createAt;
    }

    public UUID getId() {return id;}
    public UUID getDeviceId() {return deviceId;}
    public byte[] getTextEnc() {return textEnc;}
    public byte[] getIv() {return iv;}
    public Timestamp getCreateAt() {return createAt;}

    public void setId(UUID id) {this.id = id;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setTextEnc(byte[] textEnc) {this.textEnc = textEnc;}
    public void setIv(byte[] iv) {this.iv = iv;}
    public void setCreateAt(Timestamp createAt) {this.createAt = createAt;}
}
