package com.family.server.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.UUID;

import com.family.server.controller.AlertController;
import com.family.server.model.Alert;

public class AlertBussiness extends Thread {
	Socket soc;
	public AlertBussiness(Socket soc)
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
					String type = dis.readUTF();
					String message = dis.readUTF();
					
					Alert model = new Alert();
					model.setId(UUID.randomUUID());
					model.setDeviceId(UUID.fromString(deviceID));
					model.setAcknowledged(false);
					model.setType(type);
					model.setPayload(message);
					model.setTs(new Timestamp(dis.readLong()));
					model.setCreateAt(new java.sql.Timestamp(System.currentTimeMillis()));
					
					AlertController ac = new AlertController();
					ac.addAlert(model);
					System.out.println(message);
				}
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
