package com.family.server.demoDashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Welcome extends JPanel {
	MainFrame main;
	public Welcome(MainFrame main)
	{
		this.main = main;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10,10,10,10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel title = new JLabel("Chao mung ban den voi demodashboard", JLabel.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setForeground(new Color(25, 118, 210));
		
		add(title, gbc);
		
		gbc.gridy = 1;
		JButton btnDN = new JButton("Đăng nhập");
		styleButton(btnDN);
		add(btnDN, gbc);
		
		gbc.gridy = 2;
		JButton btnDK = new JButton("Đăng ký");
		styleButton(btnDK);
		add(btnDK, gbc);
		
		btnDN.addActionListener(e -> main.showPanel("login"));
		btnDK.addActionListener(e-> main.showPanel("register"));
	}
	
	private void styleButton(JButton btn)
	{
		btn.setPreferredSize(new Dimension(200, 45));
		btn.setFont(new Font("Segoe UI", Font.BOLD, 26));
		btn.setFocusPainted(false);
		btn.setBackground(new Color(66, 165, 245));
		btn.setForeground(Color.WHITE);
	}
}
