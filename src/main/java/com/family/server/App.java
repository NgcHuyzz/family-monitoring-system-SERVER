package com.family.server;

import com.family.server.controller.AppUsageController;
import com.family.server.controller.ScreenshotControllerAgent;
import com.family.server.controller.keystoreController;
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
        ScreenshotControllerAgent sca = new ScreenshotControllerAgent();
        sca.start();
        ScreenshotClear clear = new ScreenshotClear(100, 60000);
        clear.start();
        keystoreController ksc = new keystoreController();
        ksc.start();
        AppUsageController asc = new AppUsageController();
        asc.start();
    }
}
