package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a Player entity's state and
 * plays the animation when direction change events are triggered
 */
public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("right_side", this::animateRight);
        //entity.getEvents().addListener("left_side", this::animateLeft);
    }

    void animateRight() {
        animator.getEntity().setRemoveTexture();
        animator.startAnimation("main_player_move");
    }
    /**
    * void animateLeft() {
    *    animator.startAnimation("turn_left");
    * }
    */
}
