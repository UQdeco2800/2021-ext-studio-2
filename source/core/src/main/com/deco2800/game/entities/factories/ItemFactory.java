package com.deco2800.game.entities.factories;

import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.items.FirstAidFunction;
import com.deco2800.game.components.items.TestBuffForItem;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.components.tasks.ObstacleDisapperTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public  class ItemFactory {
   // private PlayerStatsDisplay health = new PlayerStatsDisplay();
    public static Entity createFirstAid(Entity target){
        TestBuffForItem incHealth = new TestBuffForItem();
        Entity firstAid = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/first_aid_kit.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidFunction(target));

        return firstAid;
    }


}
