package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.family.server.controller.KeyStrokeController;
import com.family.server.controller.PolicyController;
import com.family.server.model.KeyStroke;
import com.family.server.model.Policy;
import com.family.server.service.DecipherAES;

public class KeyStrokePanel extends JPanel {
	String DeviceID;
	JPanel grid;
	public KeyStrokePanel(String DeviceID)
	{
		this.DeviceID = DeviceID;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JLabel lbScreenshot = new JLabel("Danh sách ký tự vi phạm theo câu", JLabel.CENTER);
		
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
                            loadKeyStroke();
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
	
	private void loadKeyStroke()
	{
		grid.removeAll();
		JTextArea area = new JTextArea();
		area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		area.setWrapStyleWord(true);
		
		KeyStrokeController kc = new KeyStrokeController();
		List<KeyStroke> li =  kc.getAllOfDevice(DeviceID);
		for(KeyStroke k : li)
		{
			String str = DecipherAES.Decipher(k.getTextEnc(), k.getIv());	
			PolicyController pc = new PolicyController();
			Policy p = pc.getPolicyByDeviceID(DeviceID);
			List<String> listKeyword = p.getKeywordBlackList();
			
			
			area.append(k.getCreateAt() + " : " + str + "\n");
		}
		grid.add(area);
		
		grid.revalidate();
	    grid.repaint();	
	}
	
	private static String norm(String s)
	{
		if(s == null)
			return "";
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return s.toLowerCase();
	}
	
	private static List<String> findMatchedKeywords(String text, List<String> keywords)
	{
		List<String> matched = new java.util.ArrayList<>();

	    String normText = norm(text);

	    for (String kw : keywords) 
	        if (normText.contains(norm(kw))) 
	            matched.add(kw);

	    return matched;
	}
}
