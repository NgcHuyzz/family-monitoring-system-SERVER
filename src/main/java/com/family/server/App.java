package com.family.server;
import com.family.server.demoDashboard.CommandUI;
import com.family.server.demoDashboard.MainFrame;

import com.family.server.controller.AppUsageController;
import com.family.server.service.ScreenshotClear;
import com.family.server.service.ServerBusiness;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ServerBusiness sb = new ServerBusiness();
        sb.start();
        ScreenshotClear clear = new ScreenshotClear(100, 60000);
        clear.start();
        AppUsageController asc = new AppUsageController();
        asc.start();
        new MainFrame();
        javax.swing.SwingUtilities.invokeLater(() -> {
            CommandUI ui = new CommandUI();
            ui.setVisible(true);
        });
    }
}
