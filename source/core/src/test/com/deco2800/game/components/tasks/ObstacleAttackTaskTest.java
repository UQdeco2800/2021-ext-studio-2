package com.deco2800.game.components.tasks;

import com.deco2800.game.GdxGame;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(GameExtension.class)
class ObstacleAttackTaskTest {

    @BeforeEach
    void beforeEach() {
        // Mock rendering, physics, game time
        RenderService renderService = new RenderService();
        renderService.setDebug(mock(DebugRenderer.class));
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }
    @Test
    void shouldGetVector2Position(){
        Entity target = new Entity();
        Entity npc = new Entity();
        target.setPosition(0f, 6f);
        Entity entity = makePhysicsEntity();
        entity.create();
        entity.setPosition(0f, 4f);
        ObstacleAttackTask obstacleAttackTask = new ObstacleAttackTask(npc, target,10,6);

        obstacleAttackTask.create(() -> entity);

        assertEquals(entity.getPosition(), obstacleAttackTask.enemyCreatePosition());
    }

//    @Test
//    void shouldDrawEntityInDistance() {
//        Entity target = new Entity();
//        target.setPosition(0f, 6f);
//        Entity entity = makePhysicsEntity();
//        entity.create();
//        entity.setPosition(0f, 0f);
//        ObstacleAttackTask obstacleAttackTask = new ObstacleAttackTask(target,10,6);
//        obstacleAttackTask.create(() -> entity);
//
//        // When active, should draw enemy if within view distance
//        obstacleAttackTask.start();
//        target.setPosition(0f, 5f);
//        assertEquals(10, obstacleAttackTask.getPriority());
//
//        //should not draw enemy outside view distance
//        target.setPosition(0f, 12f);
//        assertTrue(obstacleAttackTask.getPriority() < 0);
//
//    }


   

    private Entity makePhysicsEntity() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent());
    }
}