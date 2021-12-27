package com.company;


class enemy extends character {

    char id;
    board b;
    hero player;

    public enemy(board selBoard, hero h){
        id = 'Z';
        b = selBoard;
        player = h;
        hp = 10;
        def = 2;
        atkBonus = 4;
        dmgBonus = 3;
        aP = 0;
        currentPos = b.findPosOfChar(id);

    }
    point nearestPosToTarget(int range){
        point targetPos = player.currentPos;
        point nearestPos = new point(currentPos.getX(), currentPos.getY());
        int nearestDistance = currentPos.distanceTo(targetPos);
        for(int i=0;i<b.getLength();i++){
            for(int j=0;j<b.getLength();j++) {
                point pos = new point(i,j);
                if (currentPos.distanceTo(pos) <= 3 && b.getAt(pos) == '.'
                        && nearestDistance>pos.distanceTo(targetPos)) {
                    nearestPos = pos;
                    nearestDistance = nearestPos.distanceTo(targetPos);
                }
            }
        }
        return nearestPos;
    }
    public void enemyTurn(){
        if(hp>0) {
            aP = 2;
            actions();
        }
        else
            b.setAt(currentPos, '.');
    }
    void actions(){
        point heroPos = b.findPosOfChar('P');
        currentPos = b.findPosOfChar('Z');
        if(currentPos.distanceTo(heroPos)>1){
            b.setAt(currentPos, '.');
            currentPos = nearestPosToTarget(3);
            b.setAt(currentPos, 'Z');
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