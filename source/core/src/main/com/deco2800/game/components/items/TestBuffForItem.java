package com.deco2800.game.components.items;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;

public class TestBuffForItem {
    private int health;
    private Entity target;


    public void increaseHealth(Entity target){

        health = target.getComponent(CombatStatsComponent.class).getHealth();
        if(health < 100) {
            health = health + 10;
            target.getComponent(CombatStatsComponent.class).setHealth(health);
            target.getEvents().trigger("updateHealth", health);
        }
    }
}
