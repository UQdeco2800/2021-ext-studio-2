package com.deco2800.game.components.obstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Obstacle.ObstacleDisappear;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(GameExtension.class)
class ObstacleDisappearTest {

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }


    @Test
    void shouldRemoveTexturePlayAnimationAndDisappearForMeteorite() {

        Entity obstacle = createEntity1();
        Entity meteorite = createMeteorite();

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        meteorite.addComponent(animator);
        when(animator.getEntity()).thenReturn(meteorite);

        obstacle.create();
        meteorite.create();

        Fixture obstacleFixture = obstacle.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = meteorite.getComponent(HitboxComponent.class).getFixture();
        meteorite.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true,meteorite.isRemoveTexture());
        verify(animator).startAnimation("stone1");
        assertEquals(true, meteorite.isDisappear());
    }

    @Test
    void shouldRemoveTexturePlayAnimationAndDisappearForThorns() {
        Entity player = createEntity1();
        Entity thorns = createThorn();

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        thorns.addComponent(animator);
        when(animator.getEntity()).thenReturn(thorns);

        player.create();
        thorns.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = thorns.getComponent(HitboxComponent.class).getFixture();
        thorns.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true,thorns.isRemoveTexture());
        verify(animator).startAnimation("obstacle2");
        assertEquals(true, thorns.isDisappear());
    }

    @Test
    void shouldRemoveTexturePlayAnimationAndDisappearForPlanes() {
        Entity player = createEntity1();
        Entity plants = createPlant();

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        plants.addComponent(animator);
        when(animator.getEntity()).thenReturn(plants);

        player.create();
        plants.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = plants.getComponent(HitboxComponent.class).getFixture();
        plants.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true,plants.isRemoveTexture());
        verify(animator).startAnimation("obstacles");
        assertEquals(true, plants.isDisappear());
    }


    @Test
    void shouldDisappearForFaceWorm() {
        Entity player = createEntity1();
        Entity faceWorm = createFaceWorm();

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        faceWorm.addComponent(animator);
        when(animator.getEntity()).thenReturn(faceWorm);

        player.create();
        faceWorm.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = faceWorm.getComponent(HitboxComponent.class).getFixture();
        faceWorm.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true, faceWorm.isDisappear());
    }


    Entity createEntity1() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent());
        return entity;
    }

    Entity createMeteorite() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.Meteorite))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.METEORITE));

        return entity;
    }

    Entity createThorn() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.ThornsObstacle))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }

    Entity createPlant() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.PlantsObstacle))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }

    Entity createFaceWorm() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.FaceWorm))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }
}