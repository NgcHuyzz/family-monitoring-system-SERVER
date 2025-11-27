// src/main/java/com/family/server/config/StartupListener.java
package com.family.server.config;

import com.family.server.service.ServerBusiness;
import com.family.server.service.ScreenshotClear;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    private ServerBusiness serverBusiness;
    private ScreenshotClear screenshotClear;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[StartupListener] Webapp starting, starting socket servers...");

        serverBusiness = new ServerBusiness();
        serverBusiness.start();

        screenshotClear = new ScreenshotClear(100, 60000);
        screenshotClear.start();

        System.out.println("[StartupListener] ServerBusiness & ScreenshotClear started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[StartupListener] Webapp stopping, stopping background threads...");
        try {
            if (serverBusiness != null) {
                serverBusiness.interrupt();
            }
            if (screenshotClear != null) {
                screenshotClear.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
