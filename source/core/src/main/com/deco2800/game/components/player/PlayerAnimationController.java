package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

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
        entity.getEvents().addListener("stopWalkRight", this::stopAnimateRight);
        entity.getEvents().addListener("crouch", this::animateCrouch);
        entity.getEvents().addListener("stopCrouch", this::stopAnimateCrouch);
        entity.getEvents().addListener("attack", this::animateAttack);
        entity.getEvents().addListener("stopAttack", this::stopAnimateAttack);
        // entity.getEvents().addListener("jump", this::animateJump);
    }
    
    private void preAnimationCleanUp() {
        if(texturePresent) {
            animator.getEntity().setRemoveTexture();
            texturePresent = false;
        }
        animator.stopAnimation();
    }
    
    private void animateCrouch() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_crouch");
        
    }
    private void stopAnimateCrouch() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_walk");
    }

    private void animateRight() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_run");
    }

    private void stopAnimateRight() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_walk");
    }

    private void animateAttack() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_attack");
    }

    private void stopAnimateAttack() {
        preAnimationCleanUp();
        animator.startAnimation("main_player_walk");
    }

//    private void animateJump() {
//         preAnimationCleanUp()
//         animator.startAnimation("main_player_jump");
//    }
    


}
