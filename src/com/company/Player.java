package com.company;

public class Player {
    public String name;
    public int countShip1;
    public int countShip2;
    public int countShip3;
    public int countShip4;
    public String ipAddress;
    public int allShips;

    public Player(String _name, int countShip1, int countShip2, int countShip3, int countShip4) {
        name = _name;
    }

    public Player() {
        name = "Player1";
        ipAddress = "127.0.0.1:8189";
        this.countShip1 = 4;
        this.countShip2 = 3;
        this.countShip3 = 2;
        this.countShip4 = 1;
        this.allShips = 20;
    }
}
