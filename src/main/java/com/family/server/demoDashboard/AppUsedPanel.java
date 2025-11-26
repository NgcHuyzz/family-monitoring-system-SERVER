package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.family.server.controller.AppUsageController;
import com.family.server.controller.KeyStrokeController;
import com.family.server.model.AppUsage;

public class AppUsedPanel extends JPanel {
	String DeviceID;
	JPanel grid;
	public AppUsedPanel(String DeviceID)
	{
		this.DeviceID = DeviceID;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JLabel lbScreenshot = new JLabel("Danh sách app đã sử dụng", JLabel.CENTER);
		
		grid = new JPanel(new FlowLayout());
		JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
		
		add(lbScreenshot, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		Thread autoload = new Thread(() -> {
			while(true)
			{
				try 
				{
                    SwingUtilities.invokeLater(() -> {
                        try 
                        {
                            loadUsedPanel();
                        } catch (Exception e) 
                        {
                        	
                        }
                    });
                    Thread.sleep(10_000);
                } 
				catch (InterruptedException e) 
				{
                    break;
                }
			}
		});
		
		autoload.start();
	}
	
	private void loadUsedPanel()
	{
		grid.removeAll();
		JTextArea area = new JTextArea();
		area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		area.setWrapStyleWord(true);
		
		AppUsageController auc = new AppUsageController();
		List<AppUsage> li = new ArrayList<AppUsage>();
		li = auc.getAllOfDevice(DeviceID);
		for(AppUsage au : li)
		{
			area.append(au.getAppName() + " | " + au.getStartAt() + " | " + au.getDurationSec() + "s" + "\n");
		}	
		grid.add(area);
		
		grid.revalidate();
	    grid.repaint();	
	}
	
	
}
