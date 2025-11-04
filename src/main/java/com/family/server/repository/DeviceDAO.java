package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.family.server.config.JDBC;
import com.family.server.model.Device;

public class DeviceDAO {
	private Connection con;
	public DeviceDAO()
	{
		con = JDBC.getConnection();
	}
	
	// chen du lieu
	public void addDevice(Device model)
	{
		String sql = "INSERT INTO Devices(id, name, userID, registeredAt) "
				+ "VALUES(?,?,?,?)";		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getName());
			ps.setString(3, model.getUserId().toString());
			ps.setTimestamp(4, model.getRegisteredAt());
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// thay doi du lieu
	public void updateDevice(Device model)
	{
		String sql = "UPDATE Devices SET name = ?, os = ?, lastIP = ?, lastSeenAt = ? WHERE id = ?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getName());
			ps.setString(2, model.getOs());
			ps.setString(3, model.getLastIP());
			ps.setTimestamp(4, model.getLastSeenAt());
			ps.setString(5, model.getId().toString());
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// xoa du lieu
	public void deleteDevice(String ID)
	{
		String sql = "DELETE FROM Devices where id = ?";
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
	public Device getByID(String ID)
	{
		String sql = "SELECT * FROM Devices where id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Device model = new Device();
				model.setId(UUID.fromString(rs.getString("id ")));
				model.setName(rs.getString("name"));
				model.setOs(rs.getString("os"));
				model.setLastIP(rs.getString("lastIP"));
				model.setUserId(UUID.fromString(rs.getString("userID")));
				model.setRegisteredAt(rs.getTimestamp("registeredAt"));
				model.setLastSeenAt(rs.getTimestamp("lastSeenAt"));
				
				return model;
			}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	public boolean checkName(String name)
	{
		String sql = "SELECT * FROM Devices where name = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
		}
		catch(Exception e)
		{
			
		}
		return false;
	}
	
	// lay tat ca phan tu
	public List<Device> getALL()
	{
		List<Device> li = new ArrayList<Device>();
		String sql = "SELECT * FROM Devices";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Device model = new Device();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setName(rs.getString("name"));
				model.setOs(rs.getString("os"));
				model.setLastIP(rs.getString("lastIP"));
				model.setUserId(UUID.fromString(rs.getString("userID")));
				model.setRegisteredAt(rs.getTimestamp("registeredAt"));
				model.setLastSeenAt(rs.getTimestamp("lastSeenAt"));
				
				li.add(model);
			}
		}
		catch(Exception e)
		{
			
		}
		return li;
	}
	
	public List<Device> getDeviceByUserID(String userID)
	{
		List<Device> li = new ArrayList<Device>();
		String sql = "SELECT * FROM Devices where userID = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Device model = new Device();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setName(rs.getString("name"));
				model.setOs(rs.getString("os"));
				model.setLastIP(rs.getString("lastIP"));
				model.setUserId(UUID.fromString(rs.getString("userID")));
				model.setRegisteredAt(rs.getTimestamp("registeredAt"));
				model.setLastSeenAt(rs.getTimestamp("lastSeenAt"));
				
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
