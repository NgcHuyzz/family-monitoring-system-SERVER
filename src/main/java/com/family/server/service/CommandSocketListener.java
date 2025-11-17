package com.family.server.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandSocketListener extends Thread{
    private final int port;

    public CommandSocketListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("CommandSocketListener listening on port " + port + "...");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Agent connected from: " + client.getInetAddress());

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                // lưu OutputStream của agent để gửi command
                AgentChannel.setAgentOut(out);

                // nếu muốn đọc status từ agent (VD: {"status":"ONLINE"})
                new Thread(() -> {
                    try {
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println("From agent: " + line);
                        }
                    } catch (Exception e) {
                        System.out.println("Agent disconnected: " + e.getMessage());
                        AgentChannel.setAgentOut(null);
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
