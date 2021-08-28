package com.deco2800.game.entities.factories;

import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.tasks.ObstacleDisapperTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public class ItemFactory {

    public static Entity createFirstAid(Entity target){
        AITaskComponent aiTaskComponent = new AITaskComponent().addTask(new ObstacleDisapperTask(target,10,1f));
        Entity firstAid = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/first_aid_kit.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(aiTaskComponent);
        return firstAid;
    }
}
