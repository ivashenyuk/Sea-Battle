package com.company;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection);
    void onReceive(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onExeption(TCPConnection tcpConnection, Exception ex);
}
