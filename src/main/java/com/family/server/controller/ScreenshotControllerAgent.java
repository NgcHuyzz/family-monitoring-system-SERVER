package com.family.server.controller;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.family.server.model.Screenshot;
import com.family.server.repository.ScreenshotDAO;

public class ScreenshotControllerAgent extends Thread {
	 private final JFrame frame = new JFrame("Demo - 10 ảnh gần nhất");
	    private final JPanel grid = new JPanel(new GridLayout(2, 5, 8, 8));

	    public ScreenshotControllerAgent() 
	    {
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setContentPane(grid);
	        frame.setSize(1200, 600);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }
	
	public void run()
	{
		while(true)
		{
			try
			{
				grid.removeAll();
				ScreenshotDAO sDao = new ScreenshotDAO();
				List<Screenshot> ls = sDao.getLatest(10);
				sDao.close();
				
				for(Screenshot s : ls)
				{
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(s.getImgData()));
					Image thumb = img.getScaledInstance(400, -1, Image.SCALE_SMOOTH);
					JLabel lbl = new JLabel(new ImageIcon(thumb));
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
		            lbl.setVerticalAlignment(SwingConstants.CENTER);
		            lbl.setToolTipText(s.getTs().toString());
		            grid.add(lbl);
				}
				grid.revalidate();
                grid.repaint();
			     Thread.sleep(3000); 
			}
			catch(Exception e)
			{
				
			}
			
		}
		
	}
}
