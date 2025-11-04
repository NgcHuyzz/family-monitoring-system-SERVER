package com.family.server.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.family.server.model.Device;
import com.family.server.repository.DeviceDAO;

public class deviceController {
	public deviceController()
	{
		
	}
	
	public List<Device> getListDeviceOfUser(String UserID)
	{		
		DeviceDAO ddao = new DeviceDAO();
		List<Device> li = ddao.getDeviceByUserID(UserID);
		ddao.close();
		return li;
	}
	
	public boolean checkName(String name)
	{
		DeviceDAO ddao = new DeviceDAO();
		boolean check = ddao.checkName(name);
		ddao.close();
		return check;
	}
	
	public String addDevice(String name, String UserID)
	{
		Device model = new Device();
		UUID id = UUID.randomUUID();
		model.setId(id);
		model.setName(name);
		model.setUserId(UUID.fromString(UserID));
		model.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
		DeviceDAO ddao = new DeviceDAO();
		ddao.addDevice(model);
		ddao.close();
		return id.toString();
	}
}
