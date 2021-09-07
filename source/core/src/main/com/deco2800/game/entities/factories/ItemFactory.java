package com.deco2800.game.entities.factories;

import com.deco2800.game.components.items.FirstAidComponent;
import com.deco2800.game.components.items.GoldComponent;
import com.deco2800.game.components.items.TestBuffForItem;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsUtils;
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

    public static Entity createWater(Entity target){
        /**
         * creates an entity for a water
         * @param target The entity which is passed on to the first Aid component
         * @return entity
         */
        Entity water = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/water.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidComponent(target));

        return water;
    }

    public static Entity createMagicPotion(Entity target){
        /**
         * creates an entity for a magic potion
         * @param target The entity which is passed on to the first Aid component
         * @return entity
         */
        Entity magicPotion = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/magic_potion.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidComponent(target));

        return magicPotion;
    }

    public static Entity createSyringe(Entity target){
        /**
         * creates an entity for a syringe
         * @param target The entity which is passed on to the first Aid component
         * @return entity
         */
        Entity syringe = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/syringe.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidComponent(target));

        return syringe;
    }

    public static Entity createBandage(Entity target){
        /**
         * creates an entity for a bandage
         * @param target The entity which is passed on to the first Aid component
         * @return entity
         */
        Entity bandage = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/bandage.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new FirstAidComponent(target));

        return bandage;
    }

    public static Entity createGold(Entity target){
        /**
         * creates an entity for a gold
         * @param target The entity which is passed on to the gold component
         * @return entity
         */
        Entity gold = new Entity()
                .addComponent(new TextureRenderComponent("images/Items/goldCoin.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new GoldComponent(target));
        gold.getComponent(TextureRenderComponent.class).scaleEntity();
        gold.scaleHeight(1.5f);
        PhysicsUtils.setScaledCollider(gold, 0.5f, 0.2f);

        return gold;
    }
}
