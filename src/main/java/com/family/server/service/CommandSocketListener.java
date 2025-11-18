package com.family.server.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandSocketListener extends Thread{
    Socket soc;

    public CommandSocketListener(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
        try {

        	DataInputStream dis = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
        	DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));
            while (true) 
            {
				String deviceID = dis.readUTF();
				
				if(deviceID != null)
				{
					AgentChannel.addAgent(deviceID, dos);
				}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
