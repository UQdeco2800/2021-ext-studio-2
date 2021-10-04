package com.deco2800.game.components.obstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.SoundComponent;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class ObstacleEventHandlerTest {

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
        assertEquals(false, meteorite.getComponent(ObstacleEventHandler.class).isLocked3());
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
        assertEquals(false, thorns.getComponent(ObstacleEventHandler.class).isLocked2());
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
        assertEquals(false, plants.getComponent(ObstacleEventHandler.class).isLocked());
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

    @Test
    void shouldBeginSpaceshipAttack() {
        Entity player = createEntity1();
        Entity spaceship = createSpaceShip(player);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        player.create();
        spaceship.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture spaceshipFixture = spaceship.getComponent(HitboxComponent.class).getFixture();

        spaceship.getComponent(ObstacleEventHandler.class).setSpaceshipAttack(false);
        spaceship.getEvents().trigger("collisionStart", spaceshipFixture, playerFixture);

        SpaceshipAttackController.SpaceshipAttack spaceshipAttack = spaceship.getComponent(SpaceshipAttackController.class).getSpaceshipState();
        assertEquals(SpaceshipAttackController.SpaceshipAttack.Start, spaceshipAttack);

        assertEquals(false, spaceship.getComponent(ObstacleEventHandler.class).isLocked_ufo());
        assertEquals(true, spaceship.getComponent(ObstacleEventHandler.class).isSpaceshipAttack());
    }

    @Test
    void shouldDisposeSpaceship() {
        Entity player = createEntity1();
        Entity spaceship = createSpaceShip(player);

        player.create();
        spaceship.create();

        spaceship.getEvents().trigger("spaceshipDispose");

        assertEquals(true, spaceship.isDispose());
    }

    @Test
    void shouldPlayAnimationAndDisappearForMissile() {
        Entity player = createEntity1();
        Entity missile = createSmallMissile();

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        missile.addComponent(animator);
        when(animator.getEntity()).thenReturn(missile);

        player.create();
        missile.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture missileFixture = missile.getComponent(HitboxComponent.class).getFixture();
        missile.getEvents().trigger("collisionStart", missileFixture, obstacleFixture);

        verify(animator).startAnimation("bomb");
        assertEquals(true, missile.isDisappear());

    }

    @Test
    void shouldStartNewMap() {
        Entity player = createEntity1();
        Entity portal = createPortal(player, ObstacleEventHandler.ObstacleType.PortalEntrance);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        player.create();
        portal.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture portalFixture = portal.getComponent(ColliderComponent.class).getFixture();

        portal.getEvents().trigger("collisionStart", portalFixture, playerFixture);

        assertEquals(MainGameScreen.NewMap.Start, MainGameScreen.getNewMapStatus());
    }

    @Test
    void shouldFinishNewMap() {
        Entity player = createEntity1();
        Entity portal = createPortal(player, ObstacleEventHandler.ObstacleType.PortalExport);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        player.create();
        portal.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture portalFixture = portal.getComponent(ColliderComponent.class).getFixture();

        portal.getEvents().trigger("collisionStart", portalFixture, playerFixture);

        assertEquals(MainGameScreen.NewMap.Finish, MainGameScreen.getNewMapStatus());
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
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.Meteorite))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.METEORITE));

        return entity;
    }

    Entity createThorn() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.ThornsObstacle))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }

    Entity createPlant() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.PlantsObstacle))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }

    Entity createFaceWorm() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.FaceWorm))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }

    Entity createSpaceShip(Entity target) {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.Spaceship))
                        .addComponent(new SpaceshipAttackController().setPlayer(target))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }

    Entity createSmallMissile() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.SmallMissile))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }
    Entity createPortal(Entity target, ObstacleEventHandler.ObstacleType type) {
        Entity portal =
                new Entity("Portal")
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                        .addComponent(new ObstacleEventHandler(type));

        return portal;
    }


}