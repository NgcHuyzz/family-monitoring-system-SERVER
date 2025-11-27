package com.family.server;
import com.family.server.demoDashboard.MainFrame;

import com.family.server.controller.AppUsageController;
import com.family.server.service.AppUsageClear;
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
        ScreenshotClear clearSceenshot = new ScreenshotClear(100, 60000);
        clearSceenshot.start();
        AppUsageClear clearAppUsage = new AppUsageClear(100, 3600000);
        clearAppUsage.start();
        new MainFrame();
    }
}
