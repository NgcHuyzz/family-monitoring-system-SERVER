package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.family.server.controller.AlertController;
import com.family.server.model.Alert;
import com.family.server.service.AgentChannel;
import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

public class AlertPanel extends JPanel {
	String DeviceID;
	private JTable table;
    private DefaultTableModel tableModel;
    private List<Alert> currentAlerts = new ArrayList<Alert>();
	public AlertPanel(String DeviceID)
	{
		this.DeviceID = DeviceID;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JLabel lbScreenshot = new JLabel("Danh sách các thông báo", JLabel.CENTER);
		
		tableModel = new DefaultTableModel(
                new Object[]{"Thời gian", "Loại", "Đã xem",}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
		
		add(lbScreenshot, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // double-click
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) 
                {
                    int row = table.getSelectedRow();

                    if (row >= 0 && row < currentAlerts.size()) {
                        Alert alert = currentAlerts.get(row);
                        showAlertDialog(alert);
                    }
                }
            }
        });
		
		Thread autoload = new Thread(() -> {
			while(true)
			{
				try 
				{
                    SwingUtilities.invokeLater(() -> {
                        try 
                        {
                            loadAlert();
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
	
	private void loadAlert()
	{
		tableModel.setRowCount(0);
		currentAlerts.clear();
		AlertController ac = new AlertController();
		List<Alert> li = ac.getAlertOfDevice(DeviceID);
		
		String txtType;
		for(Alert a : li)
		{
			switch (a.getType()) 
			{
				case "OVER_DAILY_QUOTA":
					txtType = "Quá thời gian sử dụng";
					break;
				case "DOMAIN_BLOCKED":
					txtType = "Vi phạm domain cấm";
					break;
				case "QUIET_HOUR_APP_NOT_WHITELISTED":
					txtType = "Trong giờ im lặng dùng app ngoài cho phép";
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + a.getType());
			}
			 Object[] row = new Object[]{
	                    a.getTs(),                     // thời gian
	                    txtType,                  // nội dung / message
	                    a.getAcknowledged() ? "Đã xem" : "Chưa xem"
	            };
	            tableModel.addRow(row);
	            currentAlerts.add(a);
		}
	}
	
	 private void showAlertDialog(Alert alert)
	 {
		JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chi tiết thông báo", JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20,20,20,20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 20;  
 		gbc.ipady = 8; 
 		
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	JLabel lbtime = new JLabel("Thời gian:");
    	lbtime.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lbtime.setForeground(new Color(25, 118, 210));
		panel.add(lbtime, gbc);
		
		gbc.gridx = 1;
        panel.add(new JLabel(String.valueOf(alert.getTs())), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lbtype = new JLabel("Loại:");
        lbtype.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbtype.setForeground(new Color(25, 118, 210));
		panel.add(lbtype, gbc);
		
		gbc.gridx = 1;
        panel.add(new JLabel(String.valueOf(alert.getType())), gbc);
		
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel lbcontent = new JLabel("Nội dung:");
        lbcontent.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbcontent.setForeground(new Color(25, 118, 210));
		panel.add(lbcontent, gbc);
		
		gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JTextArea txtContent = new JTextArea(String.valueOf(alert.getPayload()));
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        txtContent.setEditable(false);
        JScrollPane spContent = new JScrollPane(txtContent);
        spContent.setPreferredSize(new java.awt.Dimension(350, 120));
        panel.add(txtContent,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        String text;
        switch (alert.getType()) 
		{
			case "OVER_DAILY_QUOTA":
				text = "Tắt máy tính";
				break;
			case "DOMAIN_BLOCKED":
				text = "Tắt ứng dụng";
				break;
			case "QUIET_HOUR_APP_NOT_WHITELISTED":
				text = "Tắt ứng dụng";
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + alert.getType());
		}
        JButton btnSpecial = new JButton(text);
        panel.add(btnSpecial,gbc);
        btnSpecial.addActionListener(e -> handleWindow(alert.getDeviceId().toString(), alert.getType()));
        
        dialog.setContentPane(panel);
	    dialog.pack();                         
	    dialog.setLocationRelativeTo(this);  
	    dialog.setVisible(true);
	    
	    alert.setAcknowledged(true);
	    AlertController ac = new AlertController();
	    ac.updateAlert(alert);
	 }
	 
	 private void handleWindow(String DeviceID, String type)
	 {
		 AgentChannel serverCommand = new AgentChannel();
		 System.out.println(new Timestamp(System.currentTimeMillis()));
		 switch (type) 
			{
				case "OVER_DAILY_QUOTA":
					serverCommand.sendCommand(DeviceID,"SHUTDOWN");
					break;
				case "DOMAIN_BLOCKED":
					serverCommand.sendCommand(DeviceID,"KILL_APP");
					break;
				case "QUIET_HOUR_APP_NOT_WHITELISTED":
					serverCommand.sendCommand(DeviceID,"KILL_APP");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + type);
			}
	 }
}
