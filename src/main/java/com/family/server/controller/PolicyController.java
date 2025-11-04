package com.family.server.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.family.server.model.Policy;
import com.family.server.repository.PolicyDAO;

public class PolicyController {
	public PolicyController()
	{
		
	}
	
	public void addPolicy(String DeviceID, int dailyTimeQuote, Map<String, Object> quietHour, List<String> domainBlackList, List<String> keywordBlackList, List<String> appWhiteList)
	{
		Policy model = new Policy();
		model.setId(UUID.randomUUID());
		model.setDeviceId(UUID.fromString(DeviceID));
		model.setDailyTimeQuote(dailyTimeQuote);
		model.setQuietHour(quietHour);
		model.setDomainBlackList(domainBlackList);
		model.setKeywordBlackList(keywordBlackList);
		model.setAppWhiteList(appWhiteList);
		model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
		PolicyDAO pdao = new PolicyDAO();
		pdao.addPolicy(model);
		pdao.close();	
	}
	
	public void updatePolicy(Policy model)
	{
		PolicyDAO pdao = new PolicyDAO();
		model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
		pdao.updatePolicy(model);
		pdao.close();
	}
	
	public Policy getPolicyByDeviceID(String DeviceID)
	{
		PolicyDAO pdao = new PolicyDAO();
		Policy p = pdao.getByDeviceID(DeviceID);
		pdao.close();
		return p;
	}
}
