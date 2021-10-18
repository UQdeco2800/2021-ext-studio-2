package com.deco2800.game.components;

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
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
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

import static org.junit.jupiter.api.Assertions.*;

class SoundComponentTest {

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }

    @Test
    void shouldPlaySpaceshipSound() {
        Entity player = createEntity1();
        Entity spaceship = createSpaceShip(player);

        Sound sound = mock(Sound.class);

        SoundComponent soundComponent = new SoundComponent(ObstacleEventHandler.ObstacleType.SPACESHIP, sound);
        spaceship.addComponent(soundComponent);

        player.create();
        spaceship.create();

        spaceship.getEvents().trigger("spaceshipSound");

        verify(sound).play(1f, 0.6f, 0);
    }

    @Test
    void shouldPlayMissileSound() {
        Entity player = createEntity1();
        Entity missile = createMissile(player);

        Sound sound = mock(Sound.class);

        SoundComponent soundComponent = new SoundComponent(ObstacleEventHandler.ObstacleType.SMALL_MISSILE, sound);
        missile.addComponent(soundComponent);

        player.create();
        missile.create();

        missile.getEvents().trigger("missileSound");

        verify(sound).play(0.3f, 1f, 0);
    }

    @Test
    void shouldPlayRoarSound() {
        Entity player = createEntity1();
        Entity monkey = new Entity();

        Sound sound = mock(Sound.class);

        SoundComponent soundComponent = new SoundComponent(ObstacleEventHandler.ObstacleType.FLYING_MONKEY, sound);
        monkey.addComponent(soundComponent);

        player.create();
        monkey.create();

        monkey.getEvents().trigger("roarSound");

        verify(sound).play(0.3f, 1f, 0);
    }

    Entity createEntity1() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent());
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

    Entity createMissile(Entity target) {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.SMALL_MISSILE))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }

}