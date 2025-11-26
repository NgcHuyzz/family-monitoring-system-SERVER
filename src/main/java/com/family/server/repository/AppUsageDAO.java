package com.family.server.repository;

import com.family.server.config.JDBC;
import com.family.server.model.AppUsage;
import com.family.server.model.KeyStroke;
import com.family.server.model.Screenshot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppUsageDAO {
    private Connection con;
    public AppUsageDAO() {this.con = JDBC.getConnection();
    }

    public void addAppUsage(AppUsage model)
    {
        String sql = "INSERT INTO appusage(id, deviceID, appName, startAt, durationSec, createAt) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, model.getId().toString());
            ps.setString(2, model.getDeviceId().toString());
            ps.setString(3, model.getAppName());
            ps.setTimestamp(4, model.getStartAt());
            ps.setInt(5, model.getDurationSec());
            ps.setTimestamp(6, model.getCreateAt());
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateAppUsage(AppUsage model)
    {
        String sql = "UPDATE appusage SET appName=?, startAt=?, durationSec=?, createAt=? where id = ?";
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, model.getAppName());
            ps.setTimestamp(3, model.getStartAt());
            ps.setInt(4, model.getDurationSec());
            ps.setTimestamp(5, model.getCreateAt());
            ps.setString(6, model.getId().toString());
            ps.executeUpdate();
        }
        catch(Exception e)
        {

        }
    }

    public void deletaAppUsage(String ID)
    {
        String sql = "DELETE FROM appusage where id = ?";
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
    public AppUsage getByID(String ID)
    {
        String sql = "SELECT * FROM appusage where id = ?";
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                AppUsage model = new AppUsage();
                model.setId(UUID.fromString(rs.getString("id")));
                model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
                model.setAppName(rs.getString("appName"));
                model.setStartAt(rs.getTimestamp("startAt"));
                model.setDurationSec(rs.getInt("durationSec"));
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
    public List<AppUsage> getLatest(int limit)
    {
        List<AppUsage> list = new ArrayList<>();
        String sql = "SELECT * FROM appusage ORDER BY ts DESC LIMIT ?";
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                AppUsage model = new AppUsage();
                model.setId(UUID.fromString(rs.getString("id")));
                model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
                model.setAppName(rs.getString("appName"));
                model.setStartAt(rs.getTimestamp("startAt"));
                model.setDurationSec(rs.getInt("durationSec"));
                model.setCreateAt(rs.getTimestamp("createAt"));

                list.add(model);
            }
        }
        catch(Exception e)
        {

        }

        return list;
    }
    public List<AppUsage> GetAllOfDevice(String deviceID)
    {
        List<AppUsage> li = new ArrayList<AppUsage>();
        String sql = "SELECT * FROM appusage where deviceID = ?";
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, deviceID);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                AppUsage model = new AppUsage();
                model.setId(UUID.fromString(rs.getString("id")));
                model.setDeviceId(UUID.fromString(rs.getString("deviceID")));
                model.setAppName(rs.getString("appName"));
                model.setStartAt(rs.getTimestamp("startAt"));
                model.setDurationSec(rs.getInt("durationSec"));
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
