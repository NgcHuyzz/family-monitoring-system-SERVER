package com.family.server.demoDashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.family.server.controller.KeyStrokeController;
import com.family.server.controller.PolicyController;
import com.family.server.model.KeyStroke;
import com.family.server.model.Policy;
import com.family.server.model.Screenshot;
import com.family.server.service.DecipherAES;

public class KeyStrokePanel extends JPanel {
	String DeviceID;
	JPanel grid;
	
	private JTable table;
    private DefaultTableModel tableModel;
    private List<KeyStroke> currentKeyStrokes = new ArrayList<>();
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
		
		 tableModel = new DefaultTableModel(
		            new Object[]{"Thời gian", "Câu vi phạm", "Từ khóa vi phạm"}, 0
		    ) {
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return false;
		        }
		    };

		    table = new JTable(tableModel);
		    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		    table.setRowHeight(24);

		    // Sự kiện double–click để mở hình
		    table.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
		                int row = table.getSelectedRow();
		                KeyStroke ks = currentKeyStrokes.get(row);
		                KeyStrokeController ksc = new KeyStrokeController();
		                Screenshot s = ksc.getScreetshotByKeyStroke(ks, DeviceID);
		                showScreenshotDialog(s);
		            }
		        }
		    });
		    
	    grid = new JPanel(new BorderLayout());
	    JScrollPane scroll = new JScrollPane(table);
	    scroll.setBorder(BorderFactory.createEmptyBorder());
	    scroll.getVerticalScrollBar().setUnitIncrement(16);

	    grid.add(scroll, BorderLayout.CENTER);

	    add(lbScreenshot, BorderLayout.NORTH);
	    add(grid, BorderLayout.CENTER);
		
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
		currentKeyStrokes.clear();
	    tableModel.setRowCount(0);

	    KeyStrokeController kc = new KeyStrokeController();
	    List<KeyStroke> li = kc.getAllOfDevice(DeviceID);

	    PolicyController pc = new PolicyController();
	    Policy p = pc.getPolicyByDeviceID(DeviceID);
	    List<String> listKeyword = p.getKeywordBlackList();

	    for (KeyStroke k : li) {
	        String str = DecipherAES.Decipher(k.getTextEnc(), k.getIv());
	        List<String> matched = findMatchedKeywords(str, listKeyword);

	        currentKeyStrokes.add(k);

	        String keywordText = matched.isEmpty() ? "" : String.join(", ", matched);

	        tableModel.addRow(new Object[]{
	                k.getCreateAt(),
	                str,
	                keywordText
	        });
	    }

	    grid.revalidate();
	    grid.repaint();
	}
	
	private void showScreenshotDialog(Screenshot s)
	{
		Window parent = SwingUtilities.getWindowAncestor(this);
		JDialog dialog = new JDialog(parent, "Ảnh", JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JLabel lb = new JLabel(new ImageIcon(s.getImgData()));
	    dialog.add(new JScrollPane(lb));
	    dialog.pack();
	    dialog.setLocationRelativeTo(this);
	    dialog.setVisible(true);
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
