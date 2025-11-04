package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.family.server.controller.KeyStrokeController;
import com.family.server.model.KeyStroke;
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
		
		KeyStrokeController kc = new KeyStrokeController();
		List<KeyStroke> li =  kc.getAllOfDevice(DeviceID);
		for(KeyStroke k : li)
		{
			String str = DecipherAES.Decipher(k.getTextEnc(), k.getIv());		
			area.append(k.getCreateAt() + " : " + str + "\n");
		}
		grid.add(area);
		
		grid.revalidate();
	    grid.repaint();	
	}
}
