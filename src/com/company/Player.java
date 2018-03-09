package com.company;

public class Player {
    public static String name;
    public int countShip1;
    public int countShip2;
    public int countShip3;
    public int countShip4;
    public static String ipAddress;
    public static Ship  placeOfBattleEnemy[][];
    public static Ship  placeOfBattleUser[][];

    public  Player(String name, int countShip1, int countShip2, int countShip3, int countShip4){
        this.name = name;
    }
    public Player(){
        this.name = "Player1";
        this.countShip1 = 4;
        this.countShip2 = 3;
        this.countShip3 = 2;
        this.countShip4 = 1;
    }
}
