package com.family.server.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.UUID;

import com.family.server.model.KeyStroke;
import com.family.server.repository.KeyStrokeDAO;

public class KeyStrokeBusiness extends Thread {
	Socket soc;
	public KeyStrokeBusiness(Socket soc)
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
				int lengthTextEnc = dis.readInt();
				byte[] textEnc = new byte[lengthTextEnc];
				dis.readFully(textEnc);
				int lengthIV = dis.readInt();
				byte[] iv = new byte[lengthIV];
				dis.readFully(iv);
				
				long takeAt = dis.readLong();
				
				KeyStroke model = new KeyStroke();
				model.setId(UUID.randomUUID());
				model.setDeviceId(UUID.fromString(deviceID));
				model.setTextEnc(textEnc);
				model.setIv(iv);
				model.setCreateAt(new Timestamp(takeAt));
				
				KeyStrokeDAO ksDAO = new KeyStrokeDAO();
				ksDAO.addKeyStroke(model);
				ksDAO.close();			
			}
			
		}
		catch(Exception e)
		{
			
		}
	}
}
