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

			ServerSocket server3 = new ServerSocket(1234);
			
			ServerSocket server4 = new ServerSocket(4321);
			
			ServerSocket server5 = new ServerSocket(6969);
			
			ServerSocket server6 = new ServerSocket(8888);
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
					Socket soc3 = server3.accept();
					AppUsageBusiness asb = new AppUsageBusiness(soc3);
					asb.start();
					Socket soc4 = server4.accept();
					PolicyBusiness pb = new PolicyBusiness(soc4);
					pb.start();
					Socket soc5 = server5.accept();
					AlertBussiness ab = new AlertBussiness(soc5);
					ab.start();
					Socket soc6 = server6.accept();
					CommandSocketListener csl = new CommandSocketListener(soc6);
					csl.start();
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
