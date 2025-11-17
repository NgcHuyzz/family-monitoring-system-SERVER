package com.family.server.service;

import java.io.PrintWriter;

public class AgentChannel {
    private static volatile PrintWriter agentOut;

    public static void setAgentOut(PrintWriter out) {
        agentOut = out;
    }

    public static PrintWriter getAgentOut() {
        return agentOut;
    }

    public static boolean isConnected() {
        return agentOut != null;
    }
}
