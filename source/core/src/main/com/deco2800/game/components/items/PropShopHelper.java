package com.deco2800.game.components.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.ItemBar.ItemBarDisplay;
import com.deco2800.game.components.ItemBar.newItembar;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.files.PropStoreRecord;
import com.deco2800.game.services.ServiceLocator;

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
                if(item.property.food != -1){
                    incFood();
                }
                if(item.property.water != -1){
                    incWater();
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

    private static void incFood(){
        newItembar bar = new newItembar();
        bar.addfood();

    }
    private static void incWater(){
        newItembar bar = new newItembar();
        bar.addwater();

    }
}
