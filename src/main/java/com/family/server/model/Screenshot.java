package com.family.server.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Screenshot {
    private UUID id;
    private UUID deviceId;
    private Timestamp ts;
    private byte[] imgData;
    private int width;
    private int height;
    private int bytes;
    private Timestamp createAt;

    public Screenshot() {}
    public Screenshot(UUID id, UUID deviceId, Timestamp ts, byte[] imgData, int width, int height, int bytes, Timestamp createAt)
    {
        this.id = id;
        this.deviceId = deviceId;
        this.ts = ts;
        this.imgData = imgData;
        this.width = width;
        this.height = height;
        this.bytes = bytes;
        this.createAt = createAt;
    }

    public UUID getId() {return id;}
    public UUID getDeviceId() {return deviceId;}
    public Timestamp getTs() {return ts;}
    public byte[] getImgData() {return imgData;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getBytes() {return bytes;}
    public Timestamp getCreateAt() {return createAt;}

    public void setId(UUID id) {this.id = id;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setTs(Timestamp ts) {this.ts = ts;}
    public void setImgData(byte[] imgData) {this.imgData = imgData;}
    public void setWidth(int width) {this.width = width;}
    public void setHeight(int height) {this.height = height;}
    public void setBytes(int bytes) {this.bytes = bytes;}
    public void setCreateAt(Timestamp createAt) {this.createAt = createAt;}
}
