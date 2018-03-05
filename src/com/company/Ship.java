package com.company;

public class Ship {
    public Coord coord;
    public boolean isHere;
    public boolean slip;
    public short howIsShip;
    public Ship(){
        this.isHere = false;
        this.coord = new Coord(0,0);
        this.slip = false;
    }
}
