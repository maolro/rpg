package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
            Scanner s = new Scanner(System.in);
            char[][] r1 = {
                    {'+', '+', '+', '.', '.', '+', '+', '+', '+'},
                    {'+', '+', '.', '.', '.', '.', '+', '+', '+'},
                    {'+', '+', '.', '.', 'Z', '.', '+', '+', '+'},
                    {'+', '+', '.', '.', '.', '.', '+', '+', '+'},
                    {'+', '+', '.', '.', '.', '.', '.', '+', '+'},
                    {'+', '.', '.', '.', '.', '.', '.', '+', '+'},
                    {'.', 'P', '.', '.', '.', '.', '+', '+', '+'},
                    {'.', '.', '+', '+', '+', '.', '.', '+', '+'},
                    {'+', '+', '+', '+', '+', '+', '+', '+', '+'}};

            board b = new board(r1);
            b.printBoard();
            hero h = new hero(b);
            enemy e = new enemy(b);

            while (h.hp>0 && e.hp > 0) {
                h.turn();
                e.enemyTurn();
            }
            if(h.hp>0)
                System.out.println("You are victorious");
            else{
                System.out.println("Game over");
            }

    }
}
