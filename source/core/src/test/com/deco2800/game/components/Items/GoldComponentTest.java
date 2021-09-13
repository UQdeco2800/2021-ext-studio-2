package com.deco2800.game.components.Items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.items.GoldComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
public class GoldComponentTest {
    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ResourceService resourceService = new ResourceService();
        final String[] Textures = {"images/Items/goldCoin.png"};
        resourceService.loadTextures(Textures);
        resourceService.loadAll();
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerEntityService(new EntityService());
    }

    @Test
    void shouldIncreaseGoldGot(){

        Entity target = createPlayer();
        Entity entity = createGold(target);
        Fixture entityFixture = entity.getComponent(HitboxComponent.class).getFixture();
        Fixture targetFixture = target.getComponent(HitboxComponent.class).getFixture();
        entity.getEvents().trigger("collisionStart", entityFixture, targetFixture);
        assertEquals(51, target.getComponent(InventoryComponent.class).getGold());

    }


    Entity createGold(Entity target){
        Entity entity = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent())
                .addComponent(new GoldComponent(target))
                .addComponent(new TextureRenderComponent("images/Items/goldCoin.png"));
        entity.create();
        return entity;
    }

    Entity createPlayer() {
        final PlayerConfig stats =
                FileLoader.readClass(PlayerConfig.class, "configs/player.json");
        Entity target =
                new Entity()
                        .addComponent(new CombatStatsComponent(stats.health, stats.baseAttack))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                        .addComponent(new InventoryComponent(stats.gold));

        target.create();
        return target;
    }

}

