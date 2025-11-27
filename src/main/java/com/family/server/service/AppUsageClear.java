package com.family.server.service;

import com.family.server.repository.AppUsageDAO;

public class AppUsageClear extends Thread {
	private int maxAppUsage;
	private long timeClear;
	
	public AppUsageClear(int max, long time)
	{
		this.maxAppUsage = max;
		this.timeClear = time;
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				long t0 = System.nanoTime();
				AppUsageDAO auDAO = new AppUsageDAO();
				auDAO.clearAppUsageAllDevice(maxAppUsage);
				auDAO.close();
				long time = (System.nanoTime() - t0) / 1_000_000L;
				long sleep = timeClear - time;
				Thread.sleep(sleep);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
}