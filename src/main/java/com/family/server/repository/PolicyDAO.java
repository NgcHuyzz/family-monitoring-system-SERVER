package com.family.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.family.server.config.JDBC;
import com.family.server.model.Policy;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PolicyDAO {
	Connection con;
	public PolicyDAO()
	{
		con = JDBC.getConnection();
	}
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	// chen du lieu
	public void addPolicy(Policy model)
	{
		String sql = "INSERT INTO Policies(id, deviceID, dailyTimeQuote, quietHour, domainBlackList, keywordBlackList, appWhiteList, updateAt)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, model.getId().toString());
			ps.setString(2, model.getDeviceId().toString());
			ps.setInt(3, model.getDailyTimeQuote());
			
			String jsonQuietHour = objectMapper.writeValueAsString(model.getQuietHour());
			ps.setString(4,jsonQuietHour);
			String jsonDomainBlackList = objectMapper.writeValueAsString(model.getDomainBlackList());
			ps.setString(5,jsonDomainBlackList);
			String jsonKeywordBlackList	 = objectMapper.writeValueAsString(model.getKeywordBlackList());
			ps.setString(6,jsonKeywordBlackList);
			String jsonAppWhiteList = objectMapper.writeValueAsString(model.getAppWhiteList());
			ps.setString(7,jsonAppWhiteList);	
			
			ps.setTimestamp(8, model.getUpdateAt());
			
			ps.executeUpdate();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// thay doi du lieu
	public void updatePolicy(Policy model)
	{
		String sql = "UPDATE Policies SET  dailyTimeQuote = ?, quietHour = ?, domainBlackList = ?, keywordBlackList = ?, appWhiteList = ?, updateAt = ? WHERE id = ?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, model.getDailyTimeQuote());
			
			String jsonQuietHour = objectMapper.writeValueAsString(model.getQuietHour());
			ps.setString(2,jsonQuietHour);
			String jsonDomainBlackList = objectMapper.writeValueAsString(model.getDomainBlackList());
			ps.setString(3,jsonDomainBlackList);
			String jsonKeywordBlackList	 = objectMapper.writeValueAsString(model.getKeywordBlackList());
			ps.setString(4,jsonKeywordBlackList);
			String jsonAppWhiteList = objectMapper.writeValueAsString(model.getAppWhiteList());
			ps.setString(5,jsonAppWhiteList);	
			
			ps.setTimestamp(6, model.getUpdateAt());
			ps.setString(7, model.getId().toString());
			
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			
		}
	}
	
	// xoa du lieu
	public void deletePolicy(String ID)
	{
		String sql = "DELETE FROM Policies WHERE id = ?";
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
	public Policy getByDeviceID(String DeviceID)
	{
		String sql = "SELECT * FROM Policies WHERE deviceID = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, DeviceID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Policy model = new Policy();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setDailyTimeQuote(rs.getInt("dailyTimeQuote"));
				
				String jsonQuietHour = rs.getString("quietHour");
				model.setQuietHour(objectMapper.readValue(jsonQuietHour, Map.class));
				String jsonDomainBlackList = rs.getString("domainBlackList");
				model.setDomainBlackList(objectMapper.readValue(jsonDomainBlackList, new TypeReference<List<String>>() {} ));
				String jsonKeywordBlackList = rs.getString("keywordBlackList");
				model.setKeywordBlackList(objectMapper.readValue(jsonKeywordBlackList, new TypeReference<List<String>>() {}));
				String jsonAppWhiteList = rs.getString("appWhiteList");
				model.setAppWhiteList(objectMapper.readValue(jsonAppWhiteList,new TypeReference<List<String>>() {}));
				
				model.setUpdateAt(rs.getTimestamp("updateAt"));
				
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
	public List<Policy> getALL()
	{
		List<Policy> li = new ArrayList<Policy>();
		String sql = "SELECT * FROM Policies";
		try
		{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Policy model = new Policy();
				model.setId(UUID.fromString(rs.getString("id")));
				model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
				model.setDailyTimeQuote(rs.getInt("dailyTimeQuote"));
				
				String jsonQuietHour = rs.getString("quietHour");
				model.setQuietHour(objectMapper.readValue(jsonQuietHour, Map.class));
				String jsonDomainBlackList = rs.getString("domainBlackList");
				model.setDomainBlackList(objectMapper.readValue(jsonDomainBlackList, new TypeReference<List<String>>() {} ));
				String jsonKeywordBlackList = rs.getString("keywordBlackList");
				model.setKeywordBlackList(objectMapper.readValue(jsonKeywordBlackList, new TypeReference<List<String>>() {}));
				String jsonAppWhiteList = rs.getString("appWhiteList");
				model.setAppWhiteList(objectMapper.readValue(jsonAppWhiteList,new TypeReference<List<String>>() {}));
				
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
