package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
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
            List<boardItem> heroList = b.findItemsByID('P');
            hero h = (hero) heroList.get(0);
            //game(b, h);
            char[][] r2 = {
                {'+', '+', '+', '+', '+', '+', '+', '+', '+', '+'},
                {'+', '+', '.', '+', '+', '.', '.', '+', '+', '+'},
                {'+', '.', '.', '.', '.', '.', '.', '.', '+', '+'},
                {'.', '.', 'Z', '.', '.', 'Z', '.', '.', '.', '.'},
                {'+', '.', '.', '+', '+', '.', '.', '.', '.', '+'},
                {'+', '.', '.', '.', '.', '.', '.', '.', '.', '+'},
                {'+', '+', '.', '.', '.', '.', '.', '.', '.', '+'},
                {'+', '+', '+', '.', '.', '.', '.', '.', '+', '+'},
                {'+', '+', '+', '+', '+', '.', '.', '+', '+', '+'}};
            newStage(r2, 8, 5, h);

    }
    public static void newStage(char[][] selBoard, int x, int y, hero startHero)
    {
        board b = new board(selBoard);
        point p = new point(x, y);
        b.setAt(p, startHero);
        startHero.b = b;
        game(b, startHero);
    }
    public static void game(board b, hero h){
        List<boardItem> enemyList = b.findItemsByID('Z');

        while (h.hp>0 && enemyList.size()>0) {
            h.turn();
            enemyList = b.findItemsByID('Z');
            for (boardItem enemies : enemyList){
                enemy e = (enemy) enemies;
                System.out.println("Enemy "+enemyList.indexOf(enemies)+ "'s turn");
                e.enemyTurn();
            }
        }
        if(h.hp<=0){
            System.out.println("Game over");
            System.exit(0);
        }
    }
}
