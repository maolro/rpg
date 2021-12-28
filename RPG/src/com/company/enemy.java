package com.company;


class enemy extends character {

    char id;
    board b;
    hero player;

    public enemy(board selBoard){
        id = 'Z';
        b = selBoard;
        player = (hero) b.findNearestTarget(b.findPosOfItem(this), 'P');
        hp = 10;
        def = 2;
        atkBonus = 4;
        dmgBonus = 3;
        aP = 0;

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
        point heroPos = b.findPosOfItem(player);
        point currentPos = b.findPosOfItem(this);

        if(currentPos.distanceTo(heroPos)>1){
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