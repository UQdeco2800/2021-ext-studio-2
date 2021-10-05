package com.deco2800.game.components.npc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.SoundComponent;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputService;
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
class SpaceshipAttackControllerTest {

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        SpaceshipAttackController.setSpaceshipState(SpaceshipAttackController.SpaceshipAttack.Off);
    }

    @Test
    void shouldUpdate() {
        Entity player = createEntity1();
        Entity spaceship = createSpaceShip(player);

        player.getComponent(HitboxComponent.class).setLayer(PhysicsLayer.PLAYER);

        SpaceshipAttackController spaceshipAttackController = spy(SpaceshipAttackController.class);
        spaceship.addComponent(spaceshipAttackController);

        player.create();
        spaceship.create();

        spaceshipAttackController.triggerUpdate();
        verify(spaceshipAttackController).update();
    }

    @Test
    void ShouldBeginSpaceshipSceneAndThrowError() {
        Entity player = createEntity1();
        Entity spaceship = createSpaceShip(player);

        SpaceshipAttackController spaceshipAttackController = spy(SpaceshipAttackController.class);
        spaceship.addComponent(spaceshipAttackController);

        player.create();
        spaceship.create();

        SpaceshipAttackController.setSpaceshipAttack();

        try{
            spaceshipAttackController.triggerUpdate();
            verify(spaceshipAttackController).spaceshipSceneBegins();
            fail("Should throw NullPointerException for not setPlayer()");
        }
        catch(NullPointerException e){

        }
    }

    @Test
    void ShouldOnSpaceshipScene() {
        Entity player = createEntity1();
        Entity spaceship = createSpaceShip(player);

        SpaceshipAttackController spaceshipAttackController = spy(SpaceshipAttackController.class);
        spaceship.addComponent(spaceshipAttackController);

        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForPlayer();
        spaceship.addComponent(inputComponent);


        player.create();
        spaceship.create();

        SpaceshipAttackController.setSpaceshipState(SpaceshipAttackController.SpaceshipAttack.On);

        try{
            spaceshipAttackController.triggerUpdate();
            verify(spaceshipAttackController).spaceshipAttackScene();
        }
        catch(NullPointerException e){

        }
    }


    Entity createEntity1() {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.PLAYERCOLLIDER))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                        .addComponent(new PlayerActions());
        return entity;
    }

    Entity createSpaceShip(Entity target) {
        Entity entity =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.Spaceship))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC));

        return entity;
    }
}