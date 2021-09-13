package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * This class listens to events relevant to a Player entity's state and
 * plays the animation when direction change events are triggered
 */
public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;
    private boolean texturePresent = true;
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkRight", this::animateRight);
        entity.getEvents().addListener("stopWalkRight", this::stopAnimateAll);
        entity.getEvents().addListener("crouch", this::animateCrouch);
        entity.getEvents().addListener("stopCrouch", this::stopAnimateAll);
        entity.getEvents().addListener("attack", this::animateAttack);
        entity.getEvents().addListener("stopAttack", this::stopAnimateAll);
        entity.getEvents().addListener("jump", this::animateJump);
        entity.getEvents().addListener("stopJump", this::stopAnimateAll);
        entity.getEvents().addListener("pickUpItem", this::itemPickUp);
        entity.getEvents().addListener("stopPickUpItem", this::stopAnimateAll);
    }

    private void itemPickUp() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_pickup");
    }

    private void preAnimationCleanUp() {
        if(texturePresent) {
            animator.getEntity().getComponent(TextureRenderComponent.class).dispose();
            texturePresent = false;
        }
        animator.stopAnimation();
    }
    
    private void animateCrouch() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_crouch");
        
    }

    private void animateRight() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_run");
    }


    private void animateAttack() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_attack");
    }

    private void stopAnimateAll() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_walk");
    }

    private void animateJump() {
         preAnimationCleanUp();
         animator.startAnimation("main_player_jump");
    }
    


}
