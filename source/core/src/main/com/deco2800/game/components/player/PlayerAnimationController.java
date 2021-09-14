package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * This class listens to events relevant to a Player entity's state and
 * plays animations when player action events are triggered
 */

public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;
    private boolean texturePresent = true;

    /**
     * The function create animation listeners and
     * adds them to the events of the entity,
     * so that they can be triggered
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkRight", this::animateRight);
        entity.getEvents().addListener("crouch", this::animateCrouch);
        entity.getEvents().addListener("stopCrouch", this::animateWalk);
        entity.getEvents().addListener("attack", this::animateAttack);
        entity.getEvents().addListener("stopAttack", this::animateWalk);
        entity.getEvents().addListener("jump", this::animateJump);
        entity.getEvents().addListener("stopJump", this::animateWalk);
        entity.getEvents().addListener("itemPickUp", this::animatePickUp);
        entity.getEvents().addListener("stopPickUp", this::animateWalk);
        entity.getEvents().addListener("startMPCAnimation", this::animateWalk);
        entity.getEvents().addListener("stopMPCAnimation", this::preAnimationCleanUp);
    }

    /**
     *  Makes the player pickup items
     */
    private void animatePickUp() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_pickup");
    }
    /**
     * Makes the player crouch.
     */

    private void animateCrouch() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_crouch");
        
    }

    /**
     * Makes the player run to the right.
     */

    private void animateRight() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_run");
    }

    /**
     * Makes the player attack.
     */

    private void animateAttack() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_attack");
    }

    /**
     * Makes the player jump.
     */

    private void animateJump() {
         preAnimationCleanUp();
         animator.startAnimation("main_player_jump");
    }

    /**
     * Helper function to stop all animations and trigger walking animation
     */

    private void animateWalk() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_walk");
    }

    /**
     * Helper function to dispose texture (if it exists) and stop all prior running animations.
     */

    private void preAnimationCleanUp() {
        if(texturePresent) {
            animator.getEntity().getComponent(TextureRenderComponent.class).dispose();
            texturePresent = false;
        }
        animator.stopAnimation();
    }


}
