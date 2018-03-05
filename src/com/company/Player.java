package com.company;

public class Player {
    public static String name;
    public int countShip1;
    public int countShip2;
    public int countShip3;
    public int countShip4;
    public static String ipAddress;

    public  Player(String name, int countShip1, int countShip2, int countShip3, int countShip4){
        this.name = name;
    }
    public Player(){
        this.name = "Player1";
        this.countShip1 = 4;
        this.countShip2 = 9;
        this.countShip3 = 2;
        this.countShip4 = 1;
    }
}
