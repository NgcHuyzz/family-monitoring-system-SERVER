package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.family.server.config.JDBC;
import com.family.server.model.Alert;

public class AlertDAO {
	Connection con;
	public AlertDAO()
	{
		con = JDBC.getConnection();
	}
	
	public void addAlert(Alert model)
	{
		String sql = "INSERT INTO Alerts(id, deviceID, policyID, ts, type, payload, acknowledged, createAt)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getDeviceId().toString());
			ps.setString(3, model.getPolicyId().toString());
			ps.setTimestamp(4, model.getTs());
			ps.setString(5, model.getType());
			
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
	// dong ket noi
	public void close()
	{
		JDBC.closeConnection(con);
	}
}
