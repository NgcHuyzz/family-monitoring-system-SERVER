package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.family.server.controller.ScreenshotController;
import com.family.server.model.Screenshot;

public class ScreenshotPanel extends JPanel {
	String DeviceID;
	JPanel grid;
	public ScreenshotPanel(String DeviceID)
	{
		this.DeviceID = DeviceID;
		this.GUI();
	}
	
	public void GUI()
	{
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JLabel lbScreenshot = new JLabel("10 ảnh gần nhất", JLabel.CENTER);
		
		grid = new JPanel(new GridBagLayout());
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
                            loadScreenshot();
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
	
	private void loadScreenshot()
	{
		grid.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20,20,20,20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 20;  
		gbc.ipady = 8; 	
		
		ScreenshotController sc = new ScreenshotController();
		List<Screenshot> li = sc.getScreenshotDeviceID(DeviceID);
		
		int i = 0;
		for(Screenshot s : li)
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
			try
			{
				JPanel oneScreenshot = new JPanel(new BorderLayout());
				ByteArrayInputStream bais = new ByteArrayInputStream(s.getImgData());
				BufferedImage img = ImageIO.read(bais);
				
				int width = 300;
				int height = (int) ((double) img.getHeight() / img.getWidth() * 300);
				Image Scale = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				
				JLabel lbImg = new JLabel(new ImageIcon(Scale));
				lbImg.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
				
				JLabel lbTime = new JLabel("Thời gian: "+ s.getTs());
				lbTime.setForeground(Color.DARK_GRAY);
				
				oneScreenshot.add(lbImg, BorderLayout.CENTER);
				oneScreenshot.add(lbTime, BorderLayout.SOUTH);
				grid.add(oneScreenshot, gbc);
				lbImg.addMouseListener(new MouseAdapter() {
				    public void mouseClicked(MouseEvent e) 
				    {
				        showFullImage(img);
				    }
				});
				
			}
			catch(Exception e)
			{
				
			}
			i++;
			if(i>=10)
				break;
		}
		
		grid.revalidate();
	    grid.repaint();
	}
	
	private void showFullImage(BufferedImage img)
	{
		Window parent = SwingUtilities.getWindowAncestor(this);
		JDialog dialog = new JDialog(parent, "Ảnh", JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JLabel lb = new JLabel(new ImageIcon(img));
	    dialog.add(new JScrollPane(lb));
	    dialog.pack();
	    dialog.setLocationRelativeTo(this);
	    dialog.setVisible(true);
	}
}
