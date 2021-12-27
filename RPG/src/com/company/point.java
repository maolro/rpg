package com.company;

public class point {
    private int x;
    private int y;

    public point(int _x, int _y){
        x = _x;
        y = _y;
    }
    public boolean equals(point pointB){
        return getX() == pointB.getX() && getY() == pointB.getY();
    }
    public int distanceTo(point pointB){
        return (int) Math.sqrt((getX() - pointB.getX())*(getX() - pointB.getX()) + (getY() - pointB.getY())*(getY() - pointB.getY()));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
