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
            List<boardItem> enemyList = b.findItemsByID('Z');
            List<boardItem> heroList = b.findItemsByID('P');
            hero h = (hero) heroList.get(0);

            while (h.hp > 0 && enemyList.size()>0) {
                h.turn();
                for (boardItem enemies : enemyList){
                    enemy e = (enemy) enemies;
                    if(e.hp > 0)
                        e.enemyTurn();
                    else
                        enemyList.remove(enemies);              }
            }
            if(h.hp>0)
                System.out.println("You are victorious");
            else{
                System.out.println("Game over");
            }

    }
}
