package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.family.server.controller.deviceController;
import com.family.server.model.Device;

public class UserHome extends JPanel {
	MainFrame main;
	String UserID;
	JPanel grid;
	public UserHome(MainFrame main, String UserID)
	{
		this.main = main;
		this.UserID = UserID;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(new Color(66, 165, 245));
		
		JLabel title = new JLabel("Xin chào");
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setForeground(new Color(25, 118, 210));
		
		JButton btnDX = new JButton("Đăng xuất");
		btnDX.setFocusPainted(false);
		btnDX.setBackground(Color.WHITE);
        btnDX.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        header.add(title, BorderLayout.WEST);
        header.add(btnDX, BorderLayout.EAST);
        
        btnDX.addActionListener(e -> main.showPanel("welcome"));
		
        JPanel body = new JPanel(new BorderLayout());
        JLabel text = new JLabel("Danh sách các máy con", JLabel.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 26));
		text.setForeground(new Color(25, 118, 210));
        
		grid = new JPanel(new GridBagLayout());
        loadDevices();
        body.add(text, BorderLayout.NORTH);
        body.add(grid, BorderLayout.CENTER);
        
        JButton btnCreate = new JButton("Tạo mới");
        styleButton(btnCreate);
        btnCreate.addActionListener(e -> createUser(btnCreate));
        
        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(btnCreate, BorderLayout.SOUTH);
		
	}
	
	private void loadDevices()
	{
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 20;  
		gbc.ipady = 8; 
		
		deviceController dc = new deviceController();
		List<Device> li = dc.getListDeviceOfUser(UserID);
		int i = 0;
		for(Device d : li)
		{
			if(i%2 == 0)
			{
				gbc.gridx = 0;
				gbc.gridy = i/2;
			}
			else
			{
				gbc.gridx = 1;
				gbc.gridy = i/2;
			}
			JPanel oneDevice = new JPanel(new FlowLayout());
			JLabel lbDevice = new JLabel();
			lbDevice.setText(d.getName());
	        lbDevice.setFont(new Font("Segoe UI", Font.BOLD, 26));
			JButton btnDevice = new JButton("Giám sát");
			styleButton(btnDevice);
			oneDevice.add(lbDevice);
			oneDevice.add(btnDevice);
			btnDevice.addActionListener(e -> {
				main.getContainer().add(new DeviceHome(main, d.getId().toString(), d.getName()),"device");
				main.showPanel("device");
			});
			grid.add(oneDevice,gbc);
			i++;
		}
		
		grid.revalidate();
        grid.repaint();
	}
	
	private void createUser(JButton btnCreate)
	{
		Window parent = SwingUtilities.getWindowAncestor(btnCreate);
    	JDialog dialog = new JDialog(parent, "New User", JDialog.ModalityType.APPLICATION_MODAL);
    	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);
 		gbc.fill = GridBagConstraints.HORIZONTAL;
 		gbc.ipadx = 20;  
 		gbc.ipady = 8; 
 		
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	JLabel lbname = new JLabel("tên máy: ");
    	lbname.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lbname.setForeground(new Color(25, 118, 210));
		panel.add(lbname, gbc);
			
		gbc.gridx = 1;
		JTextField txtName = new JTextField();
		txtName.setPreferredSize(new Dimension(80, 45));
		panel.add(txtName,gbc);
		
		gbc.gridx = 1;
    	gbc.gridy = 1;
    	gbc.gridwidth = 2;
    	JLabel lbWarn = new JLabel();
		panel.add(lbWarn,gbc);
		
		gbc.gridy = 2;
		JButton btnOK = new JButton("OK");
		styleButton(btnOK);
		panel.add(btnOK, gbc);
		
		btnOK.addActionListener(e -> {
			String name = txtName.getText().trim();
			deviceController dc = new deviceController();
			if(dc.checkName(name))
				lbWarn.setText("Tên đã tồn tại");
			else if(name.isEmpty())
				lbWarn.setText("Tên trống");
			else
			{
				String id = dc.addDevice(name, UserID);
				dialog.dispose();
				CopyID(parent, id);
				loadDevices();
			}
			
		});
		
		dialog.setContentPane(panel);
	    dialog.pack();                         
	    dialog.setLocationRelativeTo(parent);  
	    dialog.setVisible(true);
	}
	
	private void CopyID(Window parent, String id)
	{
		JDialog dialog = new JDialog(parent, "Tạo thành công thiết bị mới", JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);
 		gbc.fill = GridBagConstraints.HORIZONTAL;
 		gbc.ipadx = 20;  
 		gbc.ipady = 8; 
 		
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	JTextField txtID = new JTextField(id);
    	txtID.setEditable(false);
    	txtID.setCaretPosition(0);
    	panel.add(txtID, gbc);
    	
    	gbc.gridx = 1;
    	JButton btnCopy = new JButton("Copy");
    	panel.add(btnCopy, gbc);
    	
    	btnCopy.addActionListener(e -> {
    		String text = txtID.getText();
    		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    		StringSelection ss = new StringSelection(text);
    		clip.setContents(ss, ss);
    	});
		
    	dialog.setContentPane(panel);
    	dialog.pack();
    	dialog.setLocationRelativeTo(parent);
    	dialog.setVisible(true);
	}
	
	private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(66, 165, 245));
        button.setForeground(Color.WHITE);
    }
}
