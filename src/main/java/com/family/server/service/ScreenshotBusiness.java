package com.family.server.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.UUID;

import com.family.server.controller.ScreenshotController;
import com.family.server.model.Screenshot;
import com.family.server.repository.ScreenshotDAO;

public class ScreenshotBusiness extends Thread {
	Socket soc;
	public ScreenshotBusiness(Socket soc)
	{
		this.soc = soc;
	}
	
	public void run()
	{
		try
		{
			DataInputStream dis = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
			String deviceID = dis.readUTF();
			while(true)
			{
				int size = dis.readInt();
				byte[] img = new byte[size];
				dis.readFully(img);
				
				int width = dis.readInt();
				int height = dis.readInt();
				
				long takeAt = dis.readLong();
				
				Screenshot s = new Screenshot();
				// ghi vo model
				s.setId(UUID.randomUUID());
				s.setDeviceId(UUID.fromString(deviceID));
				s.setBytes(size);
				s.setImgData(img);
				s.setWidth(width);
				s.setHeight(height);
				s.setTs(new Timestamp(takeAt));
				s.setCreateAt(new java.sql.Timestamp(System.currentTimeMillis()));
				
				ScreenshotController sc = new ScreenshotController();
				sc.addScreenshot(s);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
