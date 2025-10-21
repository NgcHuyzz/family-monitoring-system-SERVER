package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.family.server.config.JDBC;
import com.family.server.model.Screenshot;

public class ScreenshotDAO {
	private Connection con;
	public ScreenshotDAO()
	{
		this.con = JDBC.getConnection();
	}
	
	// chen du lieu
	public void addScreenshot(Screenshot model)
	{
		String sql = "INSERT INTO Screenshots(id, deviceID, ts, imgData, width, height, bytes, createAt) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getDeviceId().toString());
			ps.setTimestamp(3, model.getTs());
			ps.setBytes(4, model.getImgData());
			ps.setInt(5, model.getWidth());
			ps.setInt(6, model.getHeight());
			ps.setInt(7, model.getBytes());
			ps.setTimestamp(8, model.getCreateAt());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// thay doi du lieu
	public void updateScreenshot(Screenshot model)
	{
		String sql = "UPDATE Screenshots SET ts=?, imgData=?, width=?, height=?, bytes=?, createAt=? where id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setTimestamp(1, model.getTs());
			ps.setBytes(2, model.getImgData());
			ps.setInt(3, model.getWidth());
			ps.setInt(4, model.getHeight());
			ps.setInt(5, model.getBytes());
			ps.setTimestamp(6, model.getCreateAt());
			ps.setString(7, model.getId().toString());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// xoa du lieu
	public void DeleteScreenshot(String ID)
	{
		String sql = "DELETE FROM Screenshots where id = ?";
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
	public Screenshot getByID(String ID)
	{
		String sql = "SELECT * FROM Screenshots where id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Screenshot model = new Screenshot();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setTs(rs.getTimestamp("ts"));
				model.setImgData(rs.getBytes("imgData"));
				model.setWidth(rs.getInt("width"));
				model.setHeight(rs.getInt("height"));
				model.setBytes(rs.getInt("bytes"));
				model.setCreateAt(rs.getTimestamp("createAt"));

				return model;
			}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	// lay limit phan tu
	public List<Screenshot> getLatest(int limit)
	{
		List<Screenshot> li = new ArrayList<>();
		String sql = "SELECT * FROM screenshots ORDER BY ts DESC LIMIT ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, limit);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Screenshot model = new Screenshot();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setTs(rs.getTimestamp("ts"));
				model.setImgData(rs.getBytes("imgData"));
				model.setWidth(rs.getInt("width"));
				model.setHeight(rs.getInt("height"));
				model.setBytes(rs.getInt("bytes"));
				model.setCreateAt(rs.getTimestamp("createAt"));
				
				li.add(model);
			}
		}
		catch(Exception e)
		{
			
		}
		
		return li;
	}
	
	// clear moi thiet bi chi ton tai max anh
	public void clearScreenshotAllDevice(int max)
	{
		String sql = "SELECT DISTINCT deviceID FROM Screenshots";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String deviedID = rs.getString("deviceID");
				clearScreenshot(deviedID, max);
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	// clear 1 thiet bi chi ton tai max anh
	private void clearScreenshot(String deviceID,int max)
	{
		String sql = "SELECT COUNT(*) FROM Screenshots where deviceID = ?";
		int totalDelete = 0;
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, deviceID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				int total = rs.getInt(1);
				totalDelete = (total - max) > 0 ? (total-max) : 0;
			}
		}
		catch(Exception e)
		{
			
		}
		
		if(totalDelete == 0)
			return;
		
		String sql2 = "DELETE FROM Screenshots "
				+ "where id IN ( "
				+ "SELECT id FROM ( "
				+ "SELECT id FROM Screenshots where deviceID = ? "
				+ "ORDER BY ts ASC "
				+ "LIMIT ? "
				+ ") as tmp"
				+ ")";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql2);
			ps.setString(1, deviceID);
			ps.setInt(2, totalDelete);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// dong ket noi
	public void close() 
	{
        JDBC.closeConnection(con);
    }
}
