package com.family.server.demoDashboard;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private final CardLayout card = new CardLayout();
	private final JPanel container = new JPanel(card);
	
	public MainFrame()
	{
		setTitle("Demo Dashboard");
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container.add(new Welcome(this),"welcome");
		container.add(new Login(this),"login");
		container.add(new Register(this),"register");
		add(container);
		
		card.show(container, "welcome");
		setVisible(true);
	}
	
	public void showPanel(String name)
	{
		card.show(container, name);
	}
	
	public JPanel getContainer()
	{
		return container;
	}
}
