package com.company;

import java.util.Random;
import java.util.Scanner;

public class hero extends character{
    board b;
    boolean defPos;
    boolean aim;

    public hero(board selBoard){
        b = selBoard;
        hp = 10;
        def = 4;
        defPos = false;
        aim = false;
        aP = 0;
        id = 'P';
        atkBonus = 4;
        dmgBonus = 3;
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
    void attack(character target){
        point currentPos = b.findPosOfItem(this);

        if(b.containsChar(target.id)){
            if(currentPos.distanceTo(b.findPosOfItem(b.findNearestTarget(currentPos, target.id)))==1){
                aP = aP-2;
                int roll = b.diceRoll(20);
                if(roll+atkBonus>b.diceRoll(20)+target.atkBonus) {
                    int damage = damageCalc(roll);
                    if(damage > target.def)
                        damage = damage - target.def;
                    else
                        damage = 0;
                    target.hp = target.hp - damage;
                    System.out.println("Your attack hits and deals " + damage + " damage");
                    if(target.hp <= 0){
                        b.setAt(b.findPosOfItem(target), null);
                        System.out.println("Your target is dead");
                    }
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
    public int defRoll(){
        int defRoll = b.diceRoll(20) + atkBonus;
        if(defPos)
            defRoll = defRoll + b.diceRoll(6);
        return defRoll;
    }
    public void turn(){
        defPos = false;
        aim = false;
        aP = 3;
        b.printBoard();
    }
}
