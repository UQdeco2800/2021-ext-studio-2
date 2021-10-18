package com.deco2800.game.entities.factories;

import com.deco2800.game.components.items.*;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public class ItemFactory {
    private static TestBuffForItem inchealth = new TestBuffForItem();

    /**
     * creates an entity for a firstAidKit
     * @param target The entity which is passed on to the first Aid component
     * @return entity
     */
    public static Entity createFirstAid(Entity target,InventorySystem inv){

        return new Entity("FirstAid")
                .addComponent(new TextureRenderComponent("images/Items/first_aid_kit.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new ItemBar(target, inv))
                .addComponent(new ItemComponent(target, (player) -> inchealth.increaseHealth(player)));
    }

    /**
     * creates an entity for a apple
     * @param target The entity which is passed on to the first Aid component
     * @return entity
     */
    public static Entity createApple(Entity target){

        return new Entity("Apple")
                .addComponent(new TextureRenderComponent("images/Items/food.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new ItemComponent(target,(player) -> inchealth.increaseHealth(player)));
    }

    /**
     * creates an entity for a water
     * @param target The entity which is passed on to the first Aid component
     * @return entity
     */
    public static Entity createWater(Entity target){

        return new Entity("Water")
                .addComponent(new TextureRenderComponent("images/Items/water.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new ItemComponent(target,(player) -> inchealth.increaseHealth(player)));
    }

    /**
     * creates an entity for a magic potion
     * @param target The entity which is passed on to the first Aid component
     * @return entity
     */
    public static Entity createMagicPotion(Entity target){

        return new Entity("MagicPotion")
                .addComponent(new TextureRenderComponent("images/Items/magic_potion.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new ItemComponent(target,(player) -> inchealth.increaseHealth(player)));
    }

    /**
     * creates an entity for a syringe
     * @param target The entity which is passed on to the first Aid component
     * @return entity
     */
    public static Entity createSyringe(Entity target){

        return new Entity("Syringe")
                .addComponent(new TextureRenderComponent("images/Items/syringe.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new ItemComponent(target,(player) -> inchealth.increaseHealth(player)));
    }

    /**
     * creates an entity for a bandage
     * @param target The entity which is passed on to the first Aid component
     * @return entity
     */
    public static Entity createBandage(Entity target){

        return new Entity("Bandage")
                .addComponent(new TextureRenderComponent("images/Items/bandage.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new ItemComponent(target,(player) -> inchealth.increaseHealth(player)));
    }

    /**
     * creates an entity for a gold
     * @param target The entity which is passed on to the gold component
     * @return entity
     */
    public static Entity createGold(Entity target){
        Entity gold = new Entity("Gold")
                .addComponent(new TextureRenderComponent("images/Items/goldCoin.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new GoldComponent(target));
        gold.getComponent(TextureRenderComponent.class).scaleEntity();
        gold.scaleHeight(0.8f);
        PhysicsUtils.setScaledCollider(gold, 0.5f, 0.2f);

        return gold;
    }
}
