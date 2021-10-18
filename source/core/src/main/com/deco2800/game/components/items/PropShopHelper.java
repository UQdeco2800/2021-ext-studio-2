package com.deco2800.game.components.items;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.files.PropStoreRecord;

public class PropShopHelper {
    private static final String EVENT_PROP_HEALTH = "propHealth";
    private static final String EVENT_PROP_FOOD = "propFood";

        public static void useProps(Entity target){
            PropStoreRecord.getBoughtProps().items.values().forEach(item -> {
                if(item.property.health != -1){
                    usePropHealth(target, item.property.health);
                }
                if(item.property.speed != -1){
                    incSpeed(target);
                }

                PropStoreRecord.removeItem(item);
            });

        }
    public static void usePropHealth(Entity target, int health){
            target.getComponent(CombatStatsComponent.class).setHealthMax(health);
        target.getComponent(CombatStatsComponent.class).setHealth(health);
    }
    public static void incSpeed(Entity target){
            //increase speed of player
       target.updateSpeed(new Vector2(6,8));
    }
}
