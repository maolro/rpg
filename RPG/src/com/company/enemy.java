package com.company;


class enemy extends character {

    board b;

    public enemy(board selBoard){
        b = selBoard;
        hp = 10;
        def = 2;
        atkBonus = 4;
        dmgBonus = 3;
        aP = 0;
        id = 'Z';
    }

    public void enemyTurn(){
        if(hp>0) {
            aP = 2;
            actions();
        }
        else
            b.setAt(b.findPosOfItem(this), null);
    }
    void actions(){
        boardItem player = b.findNearestTarget(b.findPosOfItem(this), 'P');
        point currentPos = b.findPosOfItem(this);

        if(currentPos.distanceTo(b.findPosOfItem(player))>1){
            b.setAt(currentPos, null);
            currentPos = b.nearestPosToTarget(currentPos, 3, player.id);
            b.setAt(currentPos, this);
            aP--;
        }
        else{
            attack();
            aP--;
        }
        if(aP>0)
            actions();
    }
    void attack(){
        hero player = (hero) b.findNearestTarget(b.findPosOfItem(this), 'P');

        if(b.diceRoll(20)+atkBonus > player.defRoll()) {
            int damage = b.diceRoll(6) + dmgBonus;
            if(damage > player.def)
                damage = damage - player.def;
            else
                damage = 0;

            player.hp = player.hp - damage;
            System.out.println("The zombie hits and deals " + damage + " damage");
        }
        else
            System.out.println("You block the enemy´s attack");
    }
}