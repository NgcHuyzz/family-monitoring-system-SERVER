package com.family.server.service;

import org.json.JSONObject;
import java.io.PrintWriter;

public class ServerCommand {

    private PrintWriter getChannel() {
        return AgentChannel.getAgentOut();
    }

    private boolean ensureConnected() {
        if (!AgentChannel.isConnected()) {
            System.out.println("No agent connected to command channel!");
            return false;
        }
        return true;
    }

    // giờ chỉ còn action
    public void sendCommand(String action) {
        if (!ensureConnected()) return;

        try {
            JSONObject json = new JSONObject();
            json.put("action", action);   // không còn target

            PrintWriter out = getChannel();
            out.println(json.toString());
            System.out.println("Sent command: " + json);

        } catch (Exception e) {
            System.out.println("Error while sending command: " + e.getMessage());
        }
    }
}
