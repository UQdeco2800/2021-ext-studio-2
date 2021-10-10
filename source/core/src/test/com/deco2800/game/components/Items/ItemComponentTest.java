package com.deco2800.game.components.Items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.items.ItemComponent;
import com.deco2800.game.components.items.PropShopHelper;
import com.deco2800.game.components.items.TestBuffForItem;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
public class ItemComponentTest {
    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }

    @Test
    void shouldIncreaseHealth(){

        Entity target = createTarget();
        Entity entity = createFirstAid(target);
        Fixture entityFixture = entity.getComponent(HitboxComponent.class).getFixture();
        Fixture targetFixture = target.getComponent(HitboxComponent.class).getFixture();
        entity.getEvents().trigger("collisionStart", entityFixture, targetFixture);
        assertEquals(100, target.getComponent(CombatStatsComponent.class).getHealth());

    }


    Entity createFirstAid(Entity target){
        TestBuffForItem incHealth = new TestBuffForItem();
        Entity entity = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent())
                .addComponent(new ItemComponent(target,(player) -> incHealth.increaseHealth(player)));
        entity.create();
        return entity;
    }

    Entity createTarget() {
        Entity target =
                new Entity()
                        .addComponent(new CombatStatsComponent(90, 0))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER));
        target.create();
        return target;
    }


}
