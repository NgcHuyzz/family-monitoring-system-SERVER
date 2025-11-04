package com.family.server.controller;

import com.family.server.model.AppUsage;
import com.family.server.repository.AppUsageDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppUsageController extends Thread{
    private final JFrame frame = new JFrame("Demo - app su dung duoc gui len Server");
    private final JTextArea area = new JTextArea();

    public AppUsageController()
    {
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        frame.add(new JScrollPane(area));
        frame.setVisible(true);
    }

    public void run()
    {
        while(true)
        {
            try{
                AppUsageDAO auDAO = new AppUsageDAO();
                List<AppUsage> lau = auDAO.GetAll();
                auDAO.close();
                area.setText("");
                for (AppUsage au : lau)
                {
                    area.append(au.getAppName() + " | " + au.getStartAt() + " | " + au.getDurationSec() + "s" + "\n");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
