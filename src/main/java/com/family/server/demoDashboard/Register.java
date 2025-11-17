package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.family.server.controller.LoginController;

public class Register extends JPanel {
	MainFrame main;
	public Register(MainFrame main)
	{
		this.main = main;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JLabel title = new JLabel("Đăng ký",JLabel.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setForeground(new Color(25, 118, 210));
		
		JPanel grid = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 20;  
		gbc.ipady = 8; 
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel lbDN = new JLabel("Tên đăng nhập");
		grid.add(lbDN,gbc);
		
		gbc.gridx = 1;
		JTextField txtDN = new JTextField();
		grid.add(txtDN,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel lbMK = new JLabel("Mật khẩu");
		grid.add(lbMK,gbc);
		
		gbc.gridx = 1;
		JPasswordField txtMK = new JPasswordField();
		grid.add(txtMK,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lbCMK = new JLabel("Nhập lại mật khẩu");
		grid.add(lbCMK,gbc);
		
		gbc.gridx = 1;
		JPasswordField txtCMK = new JPasswordField();
		grid.add(txtCMK,gbc);
		
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		JLabel lbWarn = new JLabel();
		grid.add(lbWarn,gbc);
		
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		JButton btnDN = new JButton("Đăng nhập");
		btnDN.setBorderPainted(false);
		btnDN.setContentAreaFilled(false);
		btnDN.setFocusPainted(false);
		btnDN.setForeground(Color.BLUE);
		grid.add(btnDN,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		JButton btnOK = new JButton("OK");
		styleButton(btnOK);
		grid.add(btnOK,gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		JButton btnReset = new JButton("Reset");
		styleButton(btnReset);
		grid.add(btnReset,gbc);
		
		add(title, BorderLayout.NORTH);
		add(grid, BorderLayout.CENTER);
		
		btnDN.addActionListener(e -> main.showPanel("login"));
		
		btnOK.addActionListener(e -> {
			String username = txtDN.getText().trim();
			String password = new String(txtMK.getPassword());
			String confirmPassword = new String(txtCMK.getPassword());
			
			LoginController lc = new LoginController();
			int check = lc.checkRegister(username, password, confirmPassword);
			
			switch(check)
			{
				case 1:
					lbWarn.setText("Username và Password trống");
					break;
				case 2:
					lbWarn.setText("Username Không phải email");
					break;
				case 3:
					lbWarn.setText("Nhập lại Password khác Password");
					break;
				case 4:
					lbWarn.setText("Password phải ít nhất 8 ký tự và phải tuân theo chuẩn password");
					break;
				case 5:
					lbWarn.setText("Username đã tồn tại");
					break;
				default:
					lbWarn.setText("");
			}
			lbWarn.setForeground(Color.red);
			if(check ==  0)
			{
				lc.addUser(username, password);
				main.showPanel("login");
			}
				
		});
		
	}
	
	private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(80, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(66, 165, 245));
        button.setForeground(Color.WHITE);
    }
}
