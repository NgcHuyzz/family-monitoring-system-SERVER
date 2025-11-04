package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DeviceHome extends JPanel {
	private final CardLayout contentLayout = new CardLayout();
	private final JPanel contentPanel = new JPanel(contentLayout);
	MainFrame main;
	String DeviceID;
	String name;
	public DeviceHome(MainFrame main, String DeviceID, String name)
	{
		this.main = main;
		this.DeviceID = DeviceID;
		this.name = name;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(new Color(66, 165, 245));
		
		JLabel title = new JLabel("Giám sát "+name);
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setForeground(new Color(25, 118, 210));
		
		JButton btnD = new JButton("Thoát");
		btnD.setFocusPainted(false);
		btnD.setBackground(Color.WHITE);
        btnD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        header.add(title, BorderLayout.WEST);
        header.add(btnD, BorderLayout.EAST);
        
        btnD.addActionListener(e -> main.showPanel("user"));
        
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        JButton btnScreenshot = new JButton("Xem màn hình");
        styleButton(btnScreenshot);
        JButton btnAppUsed = new JButton("Xem app sử dụng");
        styleButton(btnAppUsed);
        JButton btnKeyStroke = new JButton("Xem từ khóa vi phạm");
        styleButton(btnKeyStroke);
        JButton btnPolicy = new JButton("Xem chính sách");
        styleButton(btnPolicy);
        
        menu.add(btnScreenshot);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnAppUsed);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnKeyStroke);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnPolicy);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        contentPanel.add(new ScreenshotPanel(DeviceID), "screenshot");
        contentPanel.add(new AppUsedPanel(DeviceID), "appUsed");
        contentPanel.add(new KeyStrokePanel(DeviceID), "keystroke");
        contentPanel.add(new PolicyPanel(DeviceID), "policy");
        
        btnScreenshot.addActionListener(e -> contentLayout.show(contentPanel, "screenshot"));
        btnAppUsed.addActionListener(e -> contentLayout.show(contentPanel, "appUsed"));
        btnKeyStroke.addActionListener(e -> contentLayout.show(contentPanel, "keystroke"));
        btnPolicy.addActionListener(e -> contentLayout.show(contentPanel, "policy"));
        
        add(header, BorderLayout.NORTH);
        add(menu, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
            
	}
	
	private void styleButton(JButton btn)
	{
		btn.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn.setMaximumSize(new Dimension(150, 40));
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setBackground(Color.WHITE);
		btn.setBorder(BorderFactory.createLineBorder(new Color(100, 181, 246)));
	}
}
