package com.family.server.service;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AgentChannel {
	private static final Map<String, DataOutputStream> agentOuts = new HashMap<>();

    public static void addAgent(String deviceID, DataOutputStream out) {
        agentOuts.put(deviceID, out);
    }
    
    public static void removeAgent(String deviceID, DataOutputStream out) {
        agentOuts.remove(deviceID);
    }

    public static DataOutputStream getAgentOut(String DeviceID) {
        return agentOuts.get(DeviceID);
    }
    
    public static void sendCommand(String deviceId, String commandJson) {
        DataOutputStream dos = agentOuts.get(deviceId);
        if (dos == null) {
            System.out.println("[AgentChannel] Agent " + deviceId + " is offline or not registered");
            return;
        }
        try {
            synchronized (dos) {       
                dos.writeUTF(commandJson);
                dos.flush();
            }
        } catch (Exception e) {
            System.out.println("[AgentChannel] Error sending to " + deviceId + ": " + e.getMessage());
        }
    }

    public static boolean isConnected(String deviceID) {
        return agentOuts.get(deviceID) != null;
    }
}
