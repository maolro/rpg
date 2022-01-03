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
    void move(){
        Scanner s = new Scanner(System.in);
        System.out.println("Choose position to move");
        String movedPos = s.next();
        point pos = new point(Integer.parseInt(movedPos.substring(1))-1,
                (Character.toUpperCase(movedPos.charAt(0)) - 'A'));
        point currentPos = b.findPosOfItem(this);

        if((currentPos.distanceTo(pos)/3) <= aP){
            if(b.getAt(pos)==null){
                b.setAt(pos,this);
                b.setAt(currentPos, null);
                if(currentPos.distanceTo(pos)>3)
                    aP = aP - currentPos.distanceTo(pos)/3;
                currentPos = pos;
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
            b.printBoard();

        }
        else if(chosenAction==2){
            if(aP>=2){
                System.out.println("Choose your target");
                char targetID = s.next().charAt(0);
                boardItem target = b.findNearestTarget(b.findPosOfItem(this), targetID);
                if(target instanceof character)
                    attack((character) target);
                else
                    System.out.println("The chosen target is invalid");
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
    public void turn(){
        defPos = false;
        aim = false;
        aP = 3;
        b.printBoard();
        actions(0);
    }
}
