package com.deco2800.game.components.items;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.events.EventHandler;
import com.deco2800.game.files.PropStoreRecord;

public class PropShopHelper {
    private static final String EVENT_PROP_HEALTH = "propHealth";
    private static final String EVENT_PROP_FOOD = "propFood";

        public static void useProps(Entity target){
            //TODO: check local storage for already bought props , use buff through properties defined in json and delete the prop from local storage
            PropStoreRecord.getBoughtProps().items.values().forEach(item -> {
                if(item.property.health != -1){
                    usePropHealth(target, item.property.health);
                }

                PropStoreRecord.removeItem(item);
            });

        }
    public static void usePropHealth(Entity target, int health){
            target.getComponent(CombatStatsComponent.class).setHealthMax(health);
        target.getComponent(CombatStatsComponent.class).setHealth(health);
    }
}
