package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PVPLocal extends Thread implements TCPConnectionListener {

    private final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();

    public PVPLocal() {
        System.out.println("Server is running...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(8189)) {
                    while (true) {
                        Thread.sleep(500);
                        new TCPConnection(PVPLocal.this, serverSocket.accept());
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
       thread.setDaemon(true);
       thread.start();
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String value) {
        SendMsgAllClient(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }

    private void SendMsgAllClient(String msg) {
        System.out.println(msg);
        final int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(i).SendData(msg);
        }
    }

    private void Run(){

    }
}
