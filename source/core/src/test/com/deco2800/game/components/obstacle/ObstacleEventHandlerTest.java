package com.deco2800.game.components.obstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.ParticleRenderComponent;
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

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        meteorite.addComponent(particleRenderComponent);

        obstacle.create();
        meteorite.create();

        Fixture obstacleFixture = obstacle.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = meteorite.getComponent(HitboxComponent.class).getFixture();
        meteorite.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true,meteorite.isRemoveTexture());
        verify(animator).startAnimation("stone1");
        verify(particleRenderComponent).startEffect();
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

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        thorns.addComponent(particleRenderComponent);

        player.create();
        thorns.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = thorns.getComponent(HitboxComponent.class).getFixture();
        thorns.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true,thorns.isRemoveTexture());
        verify(animator).startAnimation("obstacle2");
        verify(particleRenderComponent).startEffect();
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

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        plants.addComponent(particleRenderComponent);

        player.create();
        plants.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture meteoriteFixture = plants.getComponent(HitboxComponent.class).getFixture();
        plants.getEvents().trigger("collisionStart", meteoriteFixture, obstacleFixture);

        assertEquals(true,plants.isRemoveTexture());
        verify(animator).startAnimation("obstacles");
        verify(particleRenderComponent).startEffect();
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
        assertEquals(SpaceshipAttackController.SpaceshipAttack.START, spaceshipAttack);

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

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        missile.addComponent(particleRenderComponent);

        player.create();
        missile.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture missileFixture = missile.getComponent(HitboxComponent.class).getFixture();
        missile.getEvents().trigger("collisionStart", missileFixture, obstacleFixture);

        verify(animator).startAnimation("bomb");
        verify(particleRenderComponent).startEffect();
        assertEquals(true, missile.isDisappear());
    }

    @Test
    void shouldPlayAnimationAndDisappearRemoveCollisionForMissile() {
        Entity player = createEntity1();
        Entity missile = createSmallMissile();

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.WEAPON);

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        missile.addComponent(animator);
        when(animator.getEntity()).thenReturn(missile);

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        missile.addComponent(particleRenderComponent);

        player.create();
        missile.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture missileFixture = missile.getComponent(HitboxComponent.class).getFixture();
        missile.getEvents().trigger("collisionStart", missileFixture, obstacleFixture);

        verify(animator).startAnimation("bomb");
        verify(particleRenderComponent).startEffect();
        assertEquals(true, missile.isDisappear());
        assertTrue(missile.isRemoveCollision());
    }

    @Test
    void shouldNotPlayAnimationAndDisappearForMissileHitObstacles() {
        Entity player = createEntity1();
        Entity missile = createSmallMissile();

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.OBSTACLE);

        AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        missile.addComponent(animator);
        when(animator.getEntity()).thenReturn(missile);

        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        missile.addComponent(particleRenderComponent);

        player.create();
        missile.create();

        Fixture obstacleFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture missileFixture = missile.getComponent(HitboxComponent.class).getFixture();
        missile.getEvents().trigger("collisionStart", missileFixture, obstacleFixture);

        verify(animator, never()).startAnimation("bomb");
        verify(particleRenderComponent, never()).startEffect();
        assertEquals(false, missile.isDisappear());

    }
    

    @Test
    void shouldStartNewMap() {
        Entity player = createEntity1();
        Entity portal = createPortal(player, ObstacleEventHandler.ObstacleType.PORTAL_ENTRANCE);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        player.create();
        portal.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture portalFixture = portal.getComponent(ColliderComponent.class).getFixture();

        portal.getEvents().trigger("collisionStart", portalFixture, playerFixture);

        assertEquals(MainGameScreen.NewMap.START, MainGameScreen.getNewMapStatus());
    }

    @Test
    void shouldNotStartNewMap() {
        Entity player = createEntity1();
        Entity portal = createPortal(player, ObstacleEventHandler.ObstacleType.PORTAL_ENTRANCE);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.OBSTACLE);

        player.create();
        portal.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture portalFixture = portal.getComponent(ColliderComponent.class).getFixture();

        portal.getEvents().trigger("collisionStart", portalFixture, playerFixture);

        assertNotEquals(MainGameScreen.NewMap.START, MainGameScreen.getNewMapStatus());
    }
    @Test
    void shouldFinishNewMap() {
        Entity player = createEntity1();
        Entity portal = createPortal(player, ObstacleEventHandler.ObstacleType.PORTAL_EXPORT);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        player.create();
        portal.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture portalFixture = portal.getComponent(ColliderComponent.class).getFixture();

        portal.getEvents().trigger("collisionStart", portalFixture, playerFixture);

        assertEquals(MainGameScreen.NewMap.FINISH, MainGameScreen.getNewMapStatus());
    }

    @Test
    void shouldNotFinishNewMap() {
        Entity player = createEntity1();
        Entity portal = createPortal(player, ObstacleEventHandler.ObstacleType.PORTAL_EXPORT);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.OBSTACLE);

        player.create();
        portal.create();

        Fixture playerFixture = player.getComponent(HitboxComponent.class).getFixture();
        Fixture portalFixture = portal.getComponent(ColliderComponent.class).getFixture();

        portal.getEvents().trigger("collisionStart", portalFixture, playerFixture);

        assertNotEquals(MainGameScreen.NewMap.FINISH, MainGameScreen.getNewMapStatus());
    }

    @Test
    void shouldDisposeForWeaponHitMissile() {

        Entity obstacle = createEntity1();
        obstacle.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.NPC);

        Entity weapon = createWeapon();

        obstacle.create();
        weapon.create();

        Fixture obstacleFixture = obstacle.getComponent(HitboxComponent.class).getFixture();
        Fixture weaponFixture = weapon.getComponent(HitboxComponent.class).getFixture();

        weapon.getEvents().trigger("collisionStart", weaponFixture, obstacleFixture);

        assertEquals(true, weapon.isDispose());
    }

    @Test
    void shouldDisposeForWeaponHitObstacles() {

        Entity obstacle = createEntity1();
        obstacle.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.OBSTACLE);

        Entity weapon = createWeapon();

        obstacle.create();
        weapon.create();

        Fixture obstacleFixture = obstacle.getComponent(HitboxComponent.class).getFixture();
        Fixture weaponFixture = weapon.getComponent(HitboxComponent.class).getFixture();

        weapon.getEvents().trigger("collisionStart", weaponFixture, obstacleFixture);

        assertEquals(true, weapon.isDispose());
    }

    @Test
    void shouldDisposeForWeaponHitGround() {

        Entity obstacle = createEntity1();
        obstacle.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.WALL);

        Entity weapon = createWeapon();

        obstacle.create();
        weapon.create();

        Fixture obstacleFixture = obstacle.getComponent(HitboxComponent.class).getFixture();
        Fixture weaponFixture = weapon.getComponent(HitboxComponent.class).getFixture();

        weapon.getEvents().trigger("collisionStart", weaponFixture, obstacleFixture);

        assertEquals(true, weapon.isDispose());
    }

    @Test
    void shouldNotDisposeForWeaponHitPlayer() {

        Entity obstacle = createEntity1();
        obstacle.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        Entity weapon = createWeapon();

        obstacle.create();
        weapon.create();

        Fixture obstacleFixture = obstacle.getComponent(HitboxComponent.class).getFixture();
        Fixture weaponFixture = weapon.getComponent(HitboxComponent.class).getFixture();

        weapon.getEvents().trigger("collisionStart", weaponFixture, obstacleFixture);

        assertEquals(false, weapon.isDispose());
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
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.METEORITE))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.METEORITE));

        return entity;
    }

    Entity createThorn() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.THORNS_OBSTACLE))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }

    Entity createPlant() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.PLANTS_OBSTACLE))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));

        return entity;
    }

    Entity createFaceWorm() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.FACE_WORM))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }

    Entity createSpaceShip(Entity target) {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.SPACESHIP))
                        .addComponent(new SpaceshipAttackController().setPlayer(target))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }

    Entity createSmallMissile() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.SMALL_MISSILE))
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

    Entity createWeapon() {
        ParticleRenderComponent particleRenderComponent = mock(ParticleRenderComponent.class);
        Entity weapon = new Entity("weapon")
                .addComponent(new PhysicsComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
                .addComponent(particleRenderComponent)
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.WEAPON));

        return weapon;
    }

}