package com.deco2800.game.components.player;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;


@ExtendWith(GameExtension.class)
class PlayerAnimationRenderTest {

    private Entity player;
    private AnimationRenderComponent animator;
    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        player = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PlayerAnimationController());

        animator = mock(AnimationRenderComponent.class);
        player.addComponent(animator);

        PlayerAnimationController animationController =
                player.getComponent(PlayerAnimationController.class);
        animationController.setTexturePresent(false);
        player.create();
    }


    @Test
    void shouldTriggerRightMovement() {
        player.getEvents().trigger("walkRight");
        verify(animator).startAnimation("main_player_run");
    }

    @Test
    void shouldTriggerWalkMovement() {
        player.getEvents().trigger("startMPCAnimation");
        verify(animator).startAnimation("main_player_walk");
    }

    @Test
    void shouldTriggerJumpMovement() {
        player.getEvents().trigger("jump");
        verify(animator).startAnimation("main_player_jump");
    }

    @Test
    void shouldTriggerCrouchMovement() {
        player.getEvents().trigger("crouch");
        verify(animator).startAnimation("main_player_crouch");
    }

    @Test
    void shouldTriggerItemPickUpMovement() {
        player.getEvents().trigger("itemPickUp");
        verify(animator).startAnimation("main_player_pickup");
    }

    @Test
    void shouldTriggerAttackMovement() {
        player.getEvents().trigger("attack");
        verify(animator).startAnimation("main_player_attack");
    }

    @Test
    void shouldTriggerBurnMovement() {
        player.getEvents().trigger("burn");
        verify(animator).startAnimation("main_player_burn");
    }

    @Test
    void shouldTriggerHurtMovement() {
        player.getEvents().trigger("hurt");
        verify(animator).startAnimation("main_player_hurt");
    }

    @Test
    void shouldTriggerWalkRightMovement() {
        player.getEvents().trigger("stopAnimations");
        verify(animator).startAnimation("main_player_walk");
    }
}