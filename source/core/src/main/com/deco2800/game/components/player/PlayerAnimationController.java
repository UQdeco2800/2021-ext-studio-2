package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("right_side", this::animateRight);
        entity.getEvents().addListener("left_side", this::animateLeft);
    }

    void animateRight() {
        animator.startAnimation("turn_right");
    }

    void animateLeft() {
        animator.startAnimation("turn_left");
    }

}
