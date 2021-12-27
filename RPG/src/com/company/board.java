package com.company;

import java.util.Random;

public class board{
    char[][] b;

    public board(char[][] selBoard){
        b = selBoard;
    }
    public char getAt(point pointA){
        return b[pointA.getX()][pointA.getY()];
    }
    public void setAt(point pointA, char c) {
        b[pointA.getX()][pointA.getY()] = c;
    }
    public int getLength(){
        return b.length;
    }
    public void printBoard(){
        System.out.print("@ ");
        for(int i=0;i<b.length;i++){
          System.out.print((char) ('A'+i)+" ");
        }
        System.out.println("");
        for (int i = 0; i < b.length; i++){
            System.out.print((i+1)+" ");
            for (int j = 0; j < b.length; j++){
                System.out.print(b[i][j] + " ");
            }
            System.out.println("");
        }
    }
    int diceRoll(int faces){
        Random r = new Random();
        return r.nextInt(faces)+1;
    }
    boolean containsChar(char c){
        boolean res = false;
        for(int i=0;i<b.length && !res; i++){
            for(int j=0;j<b.length && !res;j++){
                if(b[i][j] == c)
                    res = true;
            }
        }
        return res;
    }
    point findPosOfChar(char c){
        for(int i=0;i<b.length; i++){
            for(int j=0;j<b.length;j++){
                if(b[i][j] == c){
                    return new point(i, j);
                }
            }
        }
        throw new RuntimeException("The chosen character does not exist in the board");
    }
}