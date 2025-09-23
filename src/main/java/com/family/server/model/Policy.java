package com.family.server.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Policy {
    private UUID id;
    private UUID userId;
    private UUID deviceId;
    private int dailyTimeQuote;
    private List<String> quietHour;
    private List<String> domainBlackList;
    private List<String> keywordBlackList;
    private List<String> appWhiteList;
    private Timestamp updateAt;

    public Policy() {}
    public Policy(UUID id, UUID userId, UUID deviceId, int dailyTimeQuote, List<String> quietHour, List<String> domainBlackList, List<String> keywordBlackList, List<String> appWhiteList, Timestamp updateAt)
    {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.dailyTimeQuote = dailyTimeQuote;
        this.quietHour = quietHour;
        this.domainBlackList = domainBlackList;
        this.keywordBlackList = keywordBlackList;
        this.appWhiteList = appWhiteList;
        this.updateAt = updateAt;
    }

    public UUID getId() {return id;}
    public UUID getUserId() {return userId;}
    public UUID getDeviceId() {return deviceId;}
    public int getDailyTimeQuote() {return dailyTimeQuote;}
    public List<String> getQuietHour() {return quietHour;}
    public List<String> getDomainBlackList() {return domainBlackList;}
    public List<String> getKeywordBlackList() {return keywordBlackList;}
    public List<String> getAppWhiteList() {return appWhiteList;}
    public Timestamp getUpdateAt() {return updateAt;}

    public void setId(UUID id) {this.id = id;}
    public void setUserId(UUID userId) {this.userId = userId;}
    public void setDeviceId(UUID deviceId) {this.deviceId = deviceId;}
    public void setDailyTimeQuote(int dailyTimeQuote) {this.dailyTimeQuote = dailyTimeQuote;}
    public void setQuietHour(List<String> quietHour) {this.quietHour = quietHour;}
    public void setDomainBlackList(List<String> domainBlackList) {this.domainBlackList = domainBlackList;}
    public void setKeywordBlackList(List<String> keywordBlackList) {this.keywordBlackList = keywordBlackList;}
    public void setAppWhiteList(List<String> appWhiteList) {this.appWhiteList = appWhiteList;}
    public void setUpdateAt(Timestamp updateAt) {this.updateAt = updateAt;}
}
