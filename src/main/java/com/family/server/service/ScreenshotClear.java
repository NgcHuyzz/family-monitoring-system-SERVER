package com.family.server.service;

import com.family.server.model.Screenshot;
import com.family.server.repository.ScreenshotDAO;

public class ScreenshotClear extends Thread {
	
	
	private int maxImgDevice;
	private long timeClear;
	
	public ScreenshotClear(int maxImgDevice, long timeClear)
	{
		this.maxImgDevice = maxImgDevice;
		this.timeClear = timeClear;
	}
	
	public void run()
	{
		while(true)
		{		
			try
			{
				System.out.println("hehe");
				long t0 = System.nanoTime();
				ScreenshotDAO sDAO = new ScreenshotDAO();
				sDAO.clearScreenshotAllDevice(maxImgDevice);
				sDAO.close();
				long time = (System.nanoTime() - t0) / 1_000_000L;
				long sleep = timeClear - time;
				Thread.sleep(sleep);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
