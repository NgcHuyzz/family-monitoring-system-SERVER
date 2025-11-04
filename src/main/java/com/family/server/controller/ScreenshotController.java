package com.family.server.controller;

import java.util.List;

import com.family.server.model.Screenshot;
import com.family.server.repository.ScreenshotDAO;

public class ScreenshotController {
	public ScreenshotController()
	{
		
	}
	
	public List<Screenshot> getScreenshotDeviceID(String DeviceID)
	{
		ScreenshotDAO sdao = new ScreenshotDAO();
		List<Screenshot> li = sdao.getByDeviceID(DeviceID);
		sdao.close();
		return li;		
	}
	
	public void addScreenshot(Screenshot model)
	{
		ScreenshotDAO sDAO = new ScreenshotDAO();
		sDAO.addScreenshot(model);
		sDAO.close();
	}
}
