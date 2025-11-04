package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.family.server.config.JDBC;
import com.family.server.model.User;

public class UserDAO {
	private Connection con;
	public UserDAO()
	{
		con = JDBC.getConnection();
	}
	
	// chen du lieu
	public void addUser(User model)
	{
		String sql = "INSERT INTO Users(id, email, password, createAt)"
				+ "VALUES(?,?,?,?)";		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getEmail());
			ps.setString(3, model.getPassword());
			ps.setTimestamp(4, model.getCreateAt());
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// thay doi du lieu
	public void updateUser(User model)
	{
		String sql = "UPDATE Users SET email = ?, password = ?, createAt = ? WHERE id = ?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(2, model.getEmail());
			ps.setString(3, model.getPassword());
			ps.setTimestamp(4, model.getCreateAt());
			ps.setString(5, model.getId().toString());
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// xoa du lieu
	public void deleteUser(String ID)
	{
		String sql = "DELETE FROM Users where id = ?";
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
	public User getByID(String ID)
	{
		String sql = "SELECT * FROM Users where id = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				User model = new User();
				model.setId(UUID.fromString(rs.getString("id ")));
				model.setEmail(rs.getString("email"));
				model.setPassword(rs.getString("password"));
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
	public List<User> getALL()
	{
		List<User> li = new ArrayList<User>();
		String sql = "SELECT * FROM Users";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				User model = new User();
				model.setId(UUID.fromString(rs.getString("id ")));
				model.setEmail(rs.getString("email"));
				model.setPassword(rs.getString("password"));
				model.setCreateAt(rs.getTimestamp("createAt"));
				
				li.add(model);
			}
		}
		catch(Exception e)
		{
			
		}
		return li;
	}
	
	public Boolean checkExistUsername(String username)
	{
		String sql = "SELECT * FROM Users where name = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return false;
			else
				return true;
		}
		catch(Exception e)
		{
			
		}		
		return false;
	}
	
	public String getIDByUsername(String username)
	{
		String sql = "SELECT * FROM Users where email = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getString("id");
			}
		}
		catch(Exception e)
		{
			
		}	
		return "";
	}
	
	public Boolean checkUserPassword(String username, String password)
	{
		String sql = "SELECT * FROM Users where email = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getString("password").equals(password);
			}
			else
				return false;
		}
		catch(Exception e)
		{
			
		}		
		return false;
	}
	
	// dong ket noi
	public void close()
	{
		JDBC.closeConnection(con);
	}
}
