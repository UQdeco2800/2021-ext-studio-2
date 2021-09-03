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
        //entity.getEvents().addListener("jump", this::animateJump);
        //entity.getEvents().addListener("left_side", this::animateLeft);
    }



    private void animateRight() {
        if(texturePresent) {
            animator.getEntity().setRemoveTexture();
            texturePresent = false;
        }
        animator.stopAnimation();
        animator.startAnimation("main_player_run");
    }

    /**
     *  private void animateJump() {
     *         if(texturePresent) {
     *             animator.getEntity().setRemoveTexture();
     *             texturePresent = false;
     *         }
     *         animator.stopAnimation();
     *         animator.startAnimation("main_player_jump");
     *     }
     */


    private void stopAnimateRight() {
        animator.stopAnimation();
        animator.startAnimation("main_player_walk");
    }

    /**
    * void animateLeft() {
    *    animator.startAnimation("turn_left");
    * }
    */
}
