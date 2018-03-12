package com.company;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PVPLocal extends Thread implements TCPConnectionListener {

    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private static boolean isReceiveShips = false;

    public PVPLocal() {
        System.out.println("Server is running...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(8189)) {
                    // while (true) {
                    new TCPConnection(PVPLocal.this, serverSocket.accept());
                    //}
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
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
    public synchronized void onReceive(TCPConnection tcpConnection, String user, int enemyOrUser) {
        enemyOrUser -= 48;
        Gson gson = new Gson();

        if (user != "") {
            if (enemyOrUser == 1) {
                if (isReceiveShips) {
                    Game.placeOfBattleEnemy = gson.fromJson("[" + user, Ship[][].class);
                } else {
                    isReceiveShips = true;
                    Game.isReceiveShips = true;
                    Game.placeOfBattleEnemy = gson.fromJson(user, Ship[][].class);
                }
            } else if (enemyOrUser == 0) {
                Game.placeOfBattleUser = gson.fromJson("[" + user, Ship[][].class);
                Game.jPanel.repaint();
            }
        }
        //if(Game.placeOfBattleUser != null) {
        SendMsgAllClient(gson.toJson(Game.placeOfBattleUser), 1);
        SendMsgAllClient(gson.toJson(Game.placeOfBattleEnemy), 0);
        //}
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }

    public static void SendMsgAllClient(String enemy, int enemyOrUser) {
        final int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(i).SendData(enemy, enemyOrUser);
        }
    }
}
