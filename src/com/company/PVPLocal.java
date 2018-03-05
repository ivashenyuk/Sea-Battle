package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PVPLocal implements TCPConnectionListener{

    private final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();

    public PVPLocal(){
        System.out.println("Server is running...");
        try(ServerSocket serverSocket = new ServerSocket(8189)){
            while (true){
                try{
                    new TCPConnection(this, serverSocket.accept());
                } catch(IOException ex) {
                    System.out.println("TCPConnection exeption: " + ex);
                }

            }

        } catch (IOException ex){
            throw new RuntimeException(ex);
        }

    }


    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String value) {
        SendMsgAllCliant(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }

    private void SendMsgAllCliant(String msg){
        System.out.println(msg);
        final int size = connections.size();
        for(int i = 0; i < size; i++){
            connections.get(i).SendData(msg);
        }
    }
}
