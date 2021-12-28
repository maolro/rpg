package com.company;

import java.util.Random;

public class board{
    boardItem[][] b;

    public board(char[][] selBoard){
        b = new boardItem[selBoard.length][selBoard.length];
        for(int i=0;i< b.length;i++){
            for(int j=0;j<b.length;j++){
                switch (selBoard[i][j]){
                    case '+':
                        b[i][j] = new wall();
                        break;
                    case 'P':
                        b[i][j] = new hero(this);
                        break;
                    case 'Z':
                        b[i][j] = new enemy(this);
                        break;
                    default:
                        b[i][j] = null;
                        break;
                }
            }
        }
    }
    public boardItem getAt(point pointA){
        return b[pointA.getX()][pointA.getY()];
    }
    public void setAt(point pointA, boardItem c) {
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
                if(b[i][j] == null)
                    System.out.print(". ");
                else
                    System.out.print(b[i][j].id + " ");
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
                if(b[i][j].id == c)
                    res = true;
            }
        }
        return res;
    }
    boardItem findNearestTarget(point pos, char id){
        boardItem nearestTarget = null;
        if(containsChar(id)){
            point nearestPos = null;
            int nearestDistance = 100000;
            for(int i=0;i<getLength();i++){
                for(int j=0;j<getLength();j++) {
                    point foundPos = new point(i,j);
                    if (((getAt(foundPos) == null && id == '.') ||
                            (getAt(foundPos) != null && getAt(foundPos).id  == id))
                            && pos.distanceTo(foundPos)<nearestDistance){

                        nearestTarget = getAt(foundPos);
                        nearestDistance = nearestPos.distanceTo(foundPos);
                    }
                }
            }
            return nearestTarget;
        }
        else
            throw new RuntimeException("The chosen character does not exist in the board");
    }
    point nearestPosToTarget(point currentPos, int range, char id){
        point targetPos = findPosOfItem(findNearestTarget(currentPos, id));
        if(targetPos != null){
            point nearestPos = null;
            int nearestDistance = 100000;
            for(int i=0;i<getLength();i++){
                for(int j=0;j<getLength();j++) {
                    point foundPos = new point(i,j);
                    if (currentPos.distanceTo(foundPos)<=range && getAt(foundPos).id == '.'
                           && foundPos.distanceTo(targetPos)<nearestDistance){
                        nearestPos = foundPos;
                        nearestDistance = nearestPos.distanceTo(foundPos);
                    }
                }
            }
            return nearestPos;
        }
        else
            return null;
    }
    point findPosOfItem(boardItem c){
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