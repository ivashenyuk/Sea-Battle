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
        name = "You";
        ipAddress = "127.0.0.1";
        this.countShip1 = 0;
        this.countShip2 = 0;
        this.countShip3 = 0;
        this.countShip4 = 1;
        this.allShips = 4;
    }
}
