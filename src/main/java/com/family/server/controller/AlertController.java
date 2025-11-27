package com.family.server.controller;

import java.util.List;

import com.family.server.model.Alert;
import com.family.server.repository.AlertDAO;

public class AlertController {
	public void addAlert(Alert a)
	{
		AlertDAO aDAO = new AlertDAO();
		aDAO.addAlert(a);
		aDAO.close();
	}
	
	public void updateAlert(Alert a)
	{
		AlertDAO aDAO = new AlertDAO();
		aDAO.updateAlert(a);
		aDAO.close();
	}
	
	public List<Alert> getAlertOfDevice(String DeviceID)
	{
		AlertDAO aDAO = new AlertDAO();
		List<Alert> li = aDAO.getALLByDevice(DeviceID);
		aDAO.close();
		return li;
	}
	public Alert getById(String id)
	{
		AlertDAO aDAO = new AlertDAO();
		Alert a = aDAO.getByID(id);
		aDAO.close();
		return a;
	}

}
