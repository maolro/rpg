package com.company;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println();
        for (int i = 0; i < b.length; i++){
            System.out.print((i+1)+" ");

            for (int j = 0; j < b.length; j++){
                if(b[i][j] == null)
                    System.out.print(". ");
                else
                    System.out.print(b[i][j].id + " ");
            }
            System.out.println();
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
                if(b[i][j] != null && b[i][j].id == c)
                    res = true;
            }
        }
        return res;
    }
    boardItem findNearestTarget(point pos, char id){
        boardItem nearestTarget = null;
        if(containsChar(id)){
            int nearestDistance = 100000;
            for(int i=0;i<getLength();i++){
                for(int j=0;j<getLength();j++) {
                    point foundPos = new point(i,j);
                    if (((getAt(foundPos) == null && id == '.') ||
                            (getAt(foundPos) != null && getAt(foundPos).id  == id))
                            && pos.distanceTo(foundPos)<nearestDistance){

                        nearestTarget = getAt(foundPos);
                        nearestDistance = findPosOfItem(nearestTarget).distanceTo(pos);
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
                    if (currentPos.distanceTo(foundPos)<=range && getAt(foundPos) == null
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
    List<boardItem> findItemsByID(char id){
        List<boardItem> itemList = new ArrayList<>();
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b.length;j++){
                if(b[i][j] != null && b[i][j].id == id)
                    itemList.add(b[i][j]);
            }
        }
        return itemList;
    }
    class pathVertex{
        pathVertex previous;
        double distance;
        boolean visited;
        boolean active;
        int x;
        int y;
        public pathVertex(int _x, int _y){
            distance = 10000;
            x = _x;
            y = _y;
        }
    }
    point pathfinder(boardItem[][] board, point start, point goal, int range){
        pathVertex[][] distanceBoard = new pathVertex[board.length][board[0].length];
        for(int i=0;i<distanceBoard.length;i++) {
            for(int j=0;j<distanceBoard[0].length;j++) {
                distanceBoard[i][j] = new pathVertex(i, j);
            }
        }
        distanceBoard[goal.getX()][goal.getY()].active = true;
        distanceBoard[goal.getX()][goal.getY()].distance = 0;
        while(true){
            pathVertex minFound = null;
            for(int i=0;i<distanceBoard.length;i++){
                for(int j=0;j<distanceBoard[0].length;j++) {
                    if(distanceBoard[i][j].active && (minFound == null ||
                            distanceBoard[i][j].distance < minFound.distance)){
                        minFound = distanceBoard[i][j];
                    }
                }
            }
            if(minFound==null)
                break;
            minFound.visited = true;
            for(int i=minFound.x-1;i<minFound.x+1;i++){
                for(int j= minFound.y-1;j< minFound.y+1;j++){
                    if(i>=0 && j>=0 && i< board.length && j< board[0].length && !distanceBoard[i][j].visited){
                        double distanceTo = Math.sqrt(Math.abs(i- minFound.x)+Math.abs(j- minFound.y));
                        if(distanceTo < distanceBoard[i][j].distance) {
                            distanceBoard[i][j].distance = distanceTo;
                            distanceBoard[i][j].previous = minFound;
                            distanceBoard[i][j].active = true;
                        }
                    }
                }
            }
        }
        pathVertex current = distanceBoard[start.getX()][start.getY()];
        while(range >0 && (current.x != goal.getX() || current.y != goal.getY()) && current.previous!=null){
            range--;
            current = current.previous;
        }
        return new point(current.x, current.y);
    }
}