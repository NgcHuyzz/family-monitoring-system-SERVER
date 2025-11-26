package com.family.server.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;

import com.family.server.controller.PolicyController;
import com.family.server.model.Policy;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PolicyBusiness extends Thread {
	Socket soc;
	public PolicyBusiness(Socket soc)
	{
		this.soc = soc;
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				DataInputStream dis = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
				String deviceID = dis.readUTF();
				
				if(deviceID != null)
				{
					DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));
					PolicyController pc = new PolicyController();
					Policy p = pc.getPolicyByDeviceID(deviceID);
					ObjectMapper om = new ObjectMapper();

					String quietHour = om.writeValueAsString(p.getQuietHour());
					String domainBlackList = om.writeValueAsString(p.getDomainBlackList());
					String keywordBlackList = om.writeValueAsString(p.getKeywordBlackList());
					String appWhiteList = om.writeValueAsString(p.getAppWhiteList());
					
					dos.writeInt(p.getDailyTimeQuote());
					dos.writeUTF(quietHour);
					dos.writeUTF(domainBlackList);
					dos.writeUTF(keywordBlackList);
					dos.writeUTF(appWhiteList);
					dos.flush();
				}
				
			}		
		}
		catch(Exception e)
		{
			
		}
	}
}
