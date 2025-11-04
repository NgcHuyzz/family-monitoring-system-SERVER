package com.family.server.service;

import com.family.server.model.AppUsage;
import com.family.server.repository.AppUsageDAO;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class AppUsageBusiness extends Thread {
    Socket soc;
    public AppUsageBusiness(Socket soc) {this.soc = soc;}

    public void run()
    {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
            String deviceID = dis.readUTF();

            AppUsageDAO dao = new AppUsageDAO();

            while (!soc.isClosed()){
                String appName = dis.readUTF();
                long startMilis = dis.readLong();
                long endMilis = dis.readLong();

                int durationSec = (int) ((endMilis - startMilis) / 1000);

                AppUsage model = new AppUsage();
                model.setId(UUID.randomUUID());
                model.setDeviceId(UUID.fromString(deviceID));
                model.setAppName(appName);
                model.setStartAt(new Timestamp(startMilis));
                model.setDurationSec(durationSec);
                model.setCreateAt(new Timestamp(System.currentTimeMillis()));

                dao.addAppUsage(model);

                System.out.printf("[Server] Received log: %s | %d sec\n", appName, durationSec);

            }
            dao.close();
        }
        catch (Exception e)
        {

        }
    }
}
