package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.family.server.config.JDBC;
import com.family.server.model.KeyStroke;

public class KeyStrokeDAO {
	private Connection con;
	public KeyStrokeDAO()
	{
		con = JDBC.getConnection();
	}
	
	// chen du lieu
	public void addKeyStroke(KeyStroke model)
	{
		String sql = "INSERT INTO Keystrokes(id, deviceID, textEnc, iv, createAt) "
				+ "VALUES(?, ?, ?, ?, ?)";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getDeviceId().toString());
			ps.setBytes(3, model.getTextEnc());
			ps.setBytes(4, model.getIv());
			ps.setTimestamp(5, model.getCreateAt());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// thay doi du lieu
	public void updateKeyStroke(KeyStroke model)
	{
		String sql = "UPDATE Keystrokes SET textEnc = ?, iv = ?, createAt = ? where id = ?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setBytes(1, model.getTextEnc());
			ps.setBytes(2, model.getIv());
			ps.setTimestamp(3, model.getCreateAt());
			ps.executeUpdate();
		}
		catch(Exception e)
		{		
			e.printStackTrace();
		}
	}
	
	// xoa du lieu
	public void deleteKeyStroke(String ID)
	{
		String sql = "DELETE FROM Keystrokes where id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// lay 1 phan tu bang id
	public KeyStroke getByID(String ID)
	{
		String sql = "SELECT * FORM Keystrokes where id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				KeyStroke model = new KeyStroke();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setTextEnc(rs.getBytes("textEnc"));
				model.setIv(rs.getBytes("iv"));
				model.setCreateAt(rs.getTimestamp("createAt"));
				
				return model;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	// lay tat ca phan tu
	public List<KeyStroke> GetAll()
	{
		List<KeyStroke> li = new ArrayList<KeyStroke>();
		String sql = "SELECT * FROM Keystrokes";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				KeyStroke model = new KeyStroke();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setTextEnc(rs.getBytes("textEnc"));
				model.setIv(rs.getBytes("iv"));
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
