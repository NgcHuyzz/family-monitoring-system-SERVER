package com.family.server.controller;

import com.family.server.model.AppUsage;
import com.family.server.repository.AppUsageDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppUsageController {

    public AppUsageController()
    {
    	
    }
    
    public List<AppUsage> getAllOfDevice(String DeviceID)
    {
    	 AppUsageDAO auDAO = new AppUsageDAO();
         List<AppUsage> lau = auDAO.GetAllOfDevice(DeviceID);
         auDAO.close();
         return lau;
    }
}
