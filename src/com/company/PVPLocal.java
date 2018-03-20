package com.company;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PVPLocal extends Thread implements TCPConnectionListener {

    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    public static boolean isReceiveShips = false;

    public PVPLocal() {
        System.out.println("Server is running...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(8189)) {
                    System.out.println(serverSocket.getInetAddress());
                    //while (true) {
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
        if (Settings.IndexCurrentTab == 0) {
            Gson gson = new Gson();
            if (enemyOrUser == 1) {
                if (isReceiveShips) {
                    Game.placeOfBattleEnemy = gson.fromJson("[" + user, Ship[][].class);
                } else {
                    isReceiveShips = true;
                    Game.isReceiveShips = true;
                    Game.placeOfBattleEnemy = gson.fromJson(user, Ship[][].class);
                    if (Game.playerUser == null && Game.playerEnemy == null) {
                        Game.playerUser = new Player();
                        Game.playerEnemy = new Player();
                    }
                }
            } else if (enemyOrUser == 0) {
                Game.placeOfBattleUser = gson.fromJson("[" + user, Ship[][].class);
                Game.jPanel.repaint();
            }
        } else if (Settings.IndexCurrentTab == 1) {
            if (enemyOrUser == 1) {
                if (isReceiveShips) {
                    Game.placeOfBattleEnemy = new Gson().fromJson(user, Ship[][].class);
                } else {
                    isReceiveShips = true;
                    Game.isReceiveShips = true;
                    Game.placeOfBattleEnemy = new Gson().fromJson(user, Ship[][].class);
                    if (Game.playerUser == null && Game.playerEnemy == null) {
                        Game.playerUser = Game.playe1;
                        Game.playerEnemy = new Player();
                    }
                }
            } else if (enemyOrUser == 0) {
                if (Settings.IndexCurrentTab == 0)
                    Game.placeOfBattleUser = new Gson().fromJson("[" + user, Ship[][].class);
                else if (Settings.IndexCurrentTab == 1)
                    Game.placeOfBattleUser = new Gson().fromJson(user, Ship[][].class);
                Game.jPanel.repaint();
            }
        }

        SendMsgAllClient(new Gson().toJson(Game.placeOfBattleUser), 1);
        SendMsgAllClient(new Gson().toJson(Game.placeOfBattleEnemy), 0);

        if (!Game.stepIsTrue && isReceiveShips)
            SendYourStep(4);
    }

    @Override
    public synchronized void onReceive1(TCPConnection tcpConnection, int step) {
        if (step != 0)
            Game.stepIsTrue = true;
        else
            Game.stepIsTrue = false;
        if (Game.stepIsTrue)
            SendYourStep(0);
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

    public static void SendYourStep(int enemyOrUser) {
        final int size = connections.size();
        for (int i = 0; i < size; i++) {
            connections.get(i).YourStep(enemyOrUser);
        }
    }
}
