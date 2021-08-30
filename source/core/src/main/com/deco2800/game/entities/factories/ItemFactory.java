package com.deco2800.game.entities.factories;

import com.deco2800.game.components.items.FirstAidComponent;
import com.deco2800.game.components.items.TestBuffForItem;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public  class ItemFactory {

    public static Entity createFirstAid(Entity target){
        /**
         * creates an entity for a firstAidKit
         * @param target The entity which is passed on to the first Aid component
         * @return entity
         */
        Entity firstAid = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/first_aid_kit.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidComponent(target));

        return firstAid;
    }

    public static Entity createApple(Entity target){
        /**
         * creates an entity for a apple
         * @param target The entity which is passed on to the first Aid component
         * @return entity
         */
        Entity apple = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/food.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidComponent(target));

        return apple;
    }



}
