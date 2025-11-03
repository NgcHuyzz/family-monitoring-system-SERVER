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
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	// chen du lieu
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
			String json = objectMapper.writeValueAsString(model.getPayload());
			ps.setString(6, json);
			ps.setBoolean(7, model.getAcknowledged());
			ps.setTimestamp(8, model.getCreateAt());
			
			ps.executeUpdate();
			
		}
		catch(Exception e)
		{
			
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
			String json = objectMapper.writeValueAsString(model.getPayload());
			ps.setString(3, json);
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
				model.setPolicyId(UUID.fromString(rs.getString("policyID")));
				model.setTs(rs.getTimestamp("ts"));
				model.setType(rs.getString("type"));
				String json = rs.getString("payload");
				model.setPayload(objectMapper.readValue(json, Map.class));
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
	public List<Alert> getALL()
	{
		List<Alert> li = new ArrayList<Alert>();
		String sql = "SELECT * FROM Alerts";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Alert model = new Alert();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setPolicyId(UUID.fromString(rs.getString("policyID")));
				model.setTs(rs.getTimestamp("ts"));
				model.setType(rs.getString("type"));
				String json = rs.getString("payload");
				model.setPayload(objectMapper.readValue(json, Map.class));
				model.setAcknowledged(rs.getBoolean("acknowledged"));
				model.setCreateAt(rs.getTimestamp("createAt"));
				
				li.add(model);
			}
		}
		catch(Exception e)
		{
			
		}
		return li;
	}
	
	
	// dong ket noi
	public void close()
	{
		JDBC.closeConnection(con);
	}
}
