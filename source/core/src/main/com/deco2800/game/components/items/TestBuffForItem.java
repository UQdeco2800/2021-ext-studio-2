package com.deco2800.game.components.items;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.ItemBar.newItembar;
import com.deco2800.game.entities.Entity;

public class TestBuffForItem {
    public static double countNumber = 0;
    private int health;
    private Entity target;

    /**
     * test buff effect for the first aid kit increases health of the target by 10
     * @param target entity of which the health needs to be updated
     */

    public void increaseHealth(Entity target){
        health = target.getComponent(CombatStatsComponent.class).getHealth();
        if(health < 100) {
            target.getComponent(CombatStatsComponent.class).addHealth(0);
        }
        newItembar.addkit();
        if(countNumber < 3){
            countNumber+=0.5;
        }
    }
}