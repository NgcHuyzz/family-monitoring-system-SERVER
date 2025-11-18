package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.family.server.controller.PolicyController;
import com.family.server.model.Policy;

public class PolicyPanel extends JPanel {
	private final CardLayout contentPolicy = new CardLayout();
	private final JPanel contentPolicyPanel = new JPanel(contentPolicy);
	String DeviceID;
	
	JTextField txtQuote;
	JTextArea txtHour, txtDomain, txtKeyword, txtApp;
	
	Policy model;
	PolicyController pc = new PolicyController();
	public PolicyPanel(String DeviceID)
	{
		this.DeviceID = DeviceID;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        loadPolicy();
		JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        JButton btnDayTimeQuote = new JButton("Thời gian sử dụng");
        styleButton(btnDayTimeQuote);
        JButton btnQuietHour = new JButton("Thời gian yên lặng");
        styleButton(btnQuietHour);
        JButton btnBlackListDomain = new JButton("Domain cấm");
        styleButton(btnBlackListDomain);
        JButton btnBlackListKeyword = new JButton("Keyword cấm");
        styleButton(btnBlackListKeyword);
        JButton btnWhiteListApp = new JButton("App được dùng");
        styleButton(btnWhiteListApp);
        
        menu.add(btnDayTimeQuote);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnQuietHour);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnBlackListDomain);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnBlackListKeyword);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnWhiteListApp);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        
        contentPolicyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPolicyPanel.setBackground(Color.WHITE);
        
        contentPolicyPanel.add(DayTimeQuote(), "quote");
        contentPolicyPanel.add(QuietHour(), "hour");
        contentPolicyPanel.add(BlackListDomain(), "domain");
        contentPolicyPanel.add(BlackListKeyword(), "keyword");
        contentPolicyPanel.add(WhiteListApp(), "app");
        
        JButton btnAdd = new JButton("Thêm vào Policy");
        
        add(menu, BorderLayout.WEST);
        add(contentPolicyPanel, BorderLayout.CENTER);
        add(btnAdd, BorderLayout.SOUTH);
        
        btnDayTimeQuote.addActionListener(e -> contentPolicy.show(contentPolicyPanel, "quote"));
        btnQuietHour.addActionListener(e -> contentPolicy.show(contentPolicyPanel, "hour"));
        btnBlackListDomain.addActionListener(e -> contentPolicy.show(contentPolicyPanel, "domain"));
        btnBlackListKeyword.addActionListener(e -> contentPolicy.show(contentPolicyPanel, "keyword"));
        btnWhiteListApp.addActionListener(e -> contentPolicy.show(contentPolicyPanel, "app"));
        
        
        btnAdd.addActionListener(e -> {
        	int dailyTimeQuote = Integer.parseInt(txtQuote.getText());
        	Map<String, Object> quietHour = new HashMap<String, Object>();
        	List<String> domainBlackList = new ArrayList<String>();
        	List<String> keywordBlackList = new ArrayList<String>();
        	List<String> appWhiteList = new ArrayList<String>();

        	String[] linesHour = txtHour.getText().split("\\r?\\n");
        	for(String line : linesHour)
        	{
        		if (line.isEmpty()) 
        			continue;
                if (!line.contains("-")) 
                	continue;
        		String[] parts = line.split("-");
        		quietHour.put(parts[0].trim(), parts[1].trim());
        	}
        	
        	String[] linesDomain = txtDomain.getText().split("\\r?\\n");
        	for(String line : linesDomain)
        	{
        		if (line.isEmpty()) 
        			continue;
        		domainBlackList.add(line);
        	}
        	
        	String[] linesKeyword = txtKeyword.getText().split("\\r?\\n");
        	for(String line : linesKeyword)
        	{
        		if (line.isEmpty()) 
        			continue;
        		keywordBlackList.add(line);
        	}
        	
        	String[] linesApp = txtApp.getText().split("\\r?\\n");
        	for(String line : linesApp)
        	{
        		if (line.isEmpty()) 
        			continue;
        		appWhiteList.add(line);
        	}
        	
        	if(model == null)
        	{
            	pc.addPolicy(DeviceID, dailyTimeQuote, quietHour, domainBlackList, keywordBlackList, appWhiteList);
        	}
        	else
        	{
        		model.setDailyTimeQuote(dailyTimeQuote);
        		model.setQuietHour(quietHour);
        		model.setDomainBlackList(domainBlackList);
        		model.setKeywordBlackList(keywordBlackList);
        		model.setAppWhiteList(appWhiteList);
        		
        		pc.updatePolicy(model);
        	}

        	loadPolicy();
        });
	}
	
	private void loadPolicy()
	{
		model = pc.getPolicyByDeviceID(DeviceID);
	}
	
	private JPanel DayTimeQuote()
	{
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel("Quota(Để trống là không giới hạn): "));
		txtQuote = new JTextField(12);
		txtQuote.setPreferredSize(new Dimension(160, 28));
		
		if(model != null)
			if(model.getDailyTimeQuote() != 0)
				txtQuote.setText(Integer.toString(model.getDailyTimeQuote()));
		panel.add(txtQuote);
		
		return panel;
	}
	
	private JPanel QuietHour()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("<html>QuietHour (cú pháp: <b>\"Day - from hour to hour\"</b>)"
                + "<br/>Ví dụ: <code>Monday - from 22:00 to 24:00</code>"
                + "<br/>Mỗi dòng một khung giờ.</html>"), BorderLayout.NORTH);
		txtHour = new JTextArea(10, 50); 
        txtHour.setLineWrap(true);
        txtHour.setWrapStyleWord(true);
        txtHour.setBorder(BorderFactory.createLineBorder(new Color(210,210,210)));
        JScrollPane sp = new JScrollPane(txtHour);
        sp.setPreferredSize(new Dimension(600, 200));
        
        if(model != null)
        {
        	Map<String, Object> map = model.getQuietHour();
        	for (Map.Entry mapElement : map.entrySet()) {
                String key = (String)mapElement.getKey();

                // Finding the value
                String value = (String)mapElement.getValue();

                txtHour.append(key + "-" + value + "\n");
            }
        }
        
        panel.add(sp, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel BlackListDomain()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("BlackListDomain(Mỗi 1 dòng là một từ)"), BorderLayout.NORTH);
		txtDomain = new JTextArea(10, 50); 
		txtDomain.setLineWrap(true);
		txtDomain.setWrapStyleWord(true);
		txtDomain.setBorder(BorderFactory.createLineBorder(new Color(210,210,210)));
        JScrollPane sp = new JScrollPane(txtDomain);
        sp.setPreferredSize(new Dimension(600, 200));
        
        if(model != null)
        {
        	List<String> li = model.getDomainBlackList();
        	for(String s : li)
        	{
        		txtDomain.append(s + "\n");
        	}
        }
        
        panel.add(sp, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel BlackListKeyword()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("BlackListKeyword(Mỗi 1 dòng là một từ)"), BorderLayout.NORTH);
		txtKeyword = new JTextArea(10, 50);
		txtKeyword.setLineWrap(true);
		txtKeyword.setWrapStyleWord(true);
		txtKeyword.setBorder(BorderFactory.createLineBorder(new Color(210,210,210)));
        JScrollPane sp = new JScrollPane(txtKeyword);
        sp.setPreferredSize(new Dimension(600, 200));
        
        if(model != null)
        {
        	List<String> li = model.getKeywordBlackList();
        	for(String s : li)
        	{
        		txtKeyword.append(s + "\n");
        	}
        }
        panel.add(sp, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel WhiteListApp()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("WhiteListApp(Mỗi 1 dòng là một từ)"), BorderLayout.NORTH);
		txtApp = new JTextArea(10, 50);
		txtApp.setLineWrap(true);
		txtApp.setWrapStyleWord(true);
		txtApp.setBorder(BorderFactory.createLineBorder(new Color(210,210,210)));
        JScrollPane sp = new JScrollPane(txtApp);
        sp.setPreferredSize(new Dimension(600, 200));
        
        if(model != null)
        {
        	List<String> li = model.getAppWhiteList();
        	for(String s : li)
        	{
        		txtApp.append(s + "\n");
        	}
        }
        panel.add(sp, BorderLayout.CENTER);
		
		return panel;
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
