package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.family.server.config.JDBC;
import com.family.server.model.Alert;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlertDAO {
	Connection con;
	public AlertDAO()
	{
		con = JDBC.getConnection();
	}
	
	// chen du lieu
	public void addAlert(Alert model)
	{
		String sql = "INSERT INTO Alerts(id, deviceID, ts, type, payload, acknowledged, createAt)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getDeviceId().toString());
			ps.setTimestamp(3, model.getTs());
			ps.setString(4, model.getType());
			ps.setString(5, model.getPayload());
			ps.setBoolean(6, model.getAcknowledged());
			ps.setTimestamp(7, model.getCreateAt());
			
			ps.executeUpdate();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// thay doi du lieu
	public void updateAlert(Alert model)
	{
		String sql = "UPDATE Alerts SET ts = ?, type = ?, payload = ?, acknowledged = ?, createAt = ? WHERE id = ?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setTimestamp(1, model.getTs());
			ps.setString(2, model.getType());
			ps.setString(3, model.getPayload());
			ps.setBoolean(4, model.getAcknowledged());
			ps.setTimestamp(5, model.getCreateAt());
			ps.setString(6, model.getId().toString());
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// xoa du lieu
	public void deleteAlert(String ID)
	{
		String sql = "DELETE FROM Alerts WHERE id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// lay 1 phan tu bang id
	public Alert getByID(String ID)
	{
		String sql = "SELECT * FROM Alerts WHERE id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Alert model = new Alert();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setTs(rs.getTimestamp("ts"));
				model.setType(rs.getString("type"));
				model.setPayload(rs.getString("payload"));
				model.setAcknowledged(rs.getBoolean("acknowledged"));
				model.setCreateAt(rs.getTimestamp("createAt"));
				
				return model;
			}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	// lay tat ca phan tu
	public List<Alert> getALLByDevice(String deviceID)
	{
		List<Alert> li = new ArrayList<Alert>();
		String sql = "SELECT * FROM Alerts where deviceID = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, deviceID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Alert model = new Alert();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setTs(rs.getTimestamp("ts"));
				model.setType(rs.getString("type"));
				model.setPayload(rs.getString("payload"));
				model.setAcknowledged(rs.getBoolean("acknowledged"));
				model.setCreateAt(rs.getTimestamp("createAt"));
				
				li.add(model);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return li;
	}
	
	
	// dong ket noi
	public void close()
	{
		JDBC.closeConnection(con);
	}
}
