package com.family.server.service;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerBusiness extends Thread {
	public ServerBusiness()
	{
		
	}
	
	public void run()
	{
		try
		{
			ServerSocket server = new ServerSocket(5000);
			while(true)
			{
				try
				{
					Socket soc = server.accept();
					ScreenshotBusiness sb = new ScreenshotBusiness(soc);
					sb.start();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
