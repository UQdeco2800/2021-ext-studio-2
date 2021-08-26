package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;

public class deBuff extends CombatStatsComponent {
    public deBuff(int health, int baseAttack){
        super(health, baseAttack);
    }

    public void decreaseHealth(){
        addHealth(-1);
    }
}
