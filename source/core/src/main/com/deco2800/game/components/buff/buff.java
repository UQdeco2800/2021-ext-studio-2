package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;

public class buff extends CombatStatsComponent {
    public buff(int health, int baseAttack){
        super(health, baseAttack);
    }
    public void addHealth(){
        addHealth(1);
    }

    public void increaseHealthLimit(){
        setHealth(getHealth()+ 2);
    }
}
