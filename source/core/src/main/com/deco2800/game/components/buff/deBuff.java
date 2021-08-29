package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;

public class deBuff extends CombatStatsComponent {
    public deBuff(int health, int baseAttack){
        super(health, baseAttack);
    }

    public void decreaseHealth(){
        addHealth(-10);
    }

    public void poisoning() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            addHealth(-10);
            Thread.sleep(1000);
        }
    }

}