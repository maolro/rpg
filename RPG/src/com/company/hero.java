package com.company;

import java.util.Random;
import java.util.Scanner;

public class hero extends character{
    board b;
    enemy z;
    boolean defPos;
    boolean aim;

    public hero(board selBoard){
        b = selBoard;
        currentPos = b.findPosOfChar('P');
        hp = 10;
        defPos = false;
        aim = false;
        aP = 0;
        atkBonus = 4;
        dmgBonus = 3;
    }
    void move(){
        Scanner s = new Scanner(System.in);
        System.out.println("Choose position to move");
        String movedPos = s.next();
        point pos = new point(Integer.parseInt(movedPos.substring(1))-1,
                (Character.toUpperCase(movedPos.charAt(0)) - 'A'));
        if((int) (currentPos.distanceTo(pos)/3) <= aP){
            if(b.getAt(pos)=='.'){
                b.setAt(pos,'P');
                b.setAt(currentPos, '.');
            }
            else{
                System.out.println("position unavailable. Choose again");
                move();
            }
        }
        else{
            System.out.println("You do not have enough action points");
            move();
        }
    }
    int damageCalc(int roll){
        Random r = new Random();
        int damageTotal = r.nextInt(8)+4;
        if(roll==20)
            damageTotal = damageTotal+b.diceRoll(8);
        if(aim)
            damageTotal = damageTotal+b.diceRoll(6);
        return damageTotal;
    }
    void attack(char target){
        if(b.containsChar(target)){
            if(currentPos.distanceTo(z.currentPos)==1){
                aP = aP-2;
                int roll = b.diceRoll(20);
                if(roll+atkBonus>b.diceRoll(20)+z.atkBonus) {
                    int damage = damageCalc(roll);
                    if(damage > z.def)
                        damage = damage - z.def;
                    else
                        damage = 0;
                    z.hp = z.hp - damage;
                    System.out.println("Your attack hits and deals " + damage + " damage");
                }
                else
                    System.out.println("Your attack misses your target");
            }
            else{
                System.out.println("There are no enemies in range");
            }
        }
        else
            System.out.println("That enemy does not exist");
    }
    void actions(int moveCount){
        Scanner s = new Scanner(System.in);
        System.out.println("Choose an action, You currently have "+aP+ " AP and "+hp+" hit points");
        System.out.println("1. Move (1 AP per 3 squares from initial position)");
        System.out.println("2. Attack (2 AP)");
        System.out.println("3. Aim (1 AP)");
        System.out.println("4. Defensive position (1 AP)");

        int chosenAction = s.nextInt();
        if(chosenAction==1){
            move();
            point newPos = b.findPosOfChar('P');
            moveCount = moveCount + currentPos.distanceTo(newPos);
            currentPos = b.findPosOfChar('P');
            b.printBoard();
            if(moveCount>3){
                aP = aP - moveCount/3;
                moveCount = 0;
            }
        }
        else if(chosenAction==2){
            if(aP>=2){
                System.out.println("Choose your target");
                char target = s.next().charAt(0);
                attack(target);
            }
            else
                System.out.println("You do not have enough action points");
        }
        else if(chosenAction==3){
            if(!aim){
                aim = true;
                System.out.print("You aim your attack against your foe, gaining increased damage against it. ");
                System.out.println("Aim will last until the beginning of your next turn");
                aP--;
            }
            else
                System.out.println("You have already aimed your attack");
        }
        else{
            if(!defPos){
                defPos = true;
                System.out.println("You enter defensive position until the beginning of your next turn");
                aP--;
            }
            else
                System.out.println("You are already in defensive position");
        }
        if(aP<=0)
            System.out.println("End of turn");
        else
            actions(moveCount);
    }
    public int defRoll(){
        int defRoll = b.diceRoll(20) + atkBonus;
        if(defPos)
            defRoll = defRoll + b.diceRoll(6);
        return defRoll;
    }
    public void turn(enemy e){
        z = e;
        defPos = false;
        aim = false;
        aP = 3;
        b.printBoard();
        actions(0);
    }
}
