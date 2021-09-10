package com.deco2800.game.components.items;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.entities.Entity;

public class TestBuffForItem {
    private int health;
    private Entity target;


    public void increaseHealth(Entity target){
        /**
         * test buff effect for the first aid kit increases health of the target by 10
         * @param target entity of which the health needs to be updated
         */

        //add a image when player pick up a Blood Pack
        if(target!=null){
            if(FoodDisplay.ChickenImage.size()<4){
                FoodDisplay.addOrRemoveImage(1);
            }
        }

        health = target.getComponent(CombatStatsComponent.class).getHealth();
        if(health < 100) {
            target.getComponent(CombatStatsComponent.class).addHealth(10);



        }
    }
}
