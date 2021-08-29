package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to an obstacle entity's state and plays the animation
 * when disappear events is triggered.
 */
public class ObstacleAnimationController2 extends Component {
    AnimationRenderComponent animator;

    /**
     * Start listening to ObstacleDisappearStart event on obstacles.
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
       // entity.getEvents().addListener("ObstacleDisappearStart", this::obstacleDisappear);
        entity.getEvents().addListener("ObstacleDisappearStart", this::obstacle_2_Disappear);
    }

    /**
     * When the monitored event is triggered, play the obstacle explosion animation, and disable the
     * obstacle (let it disappear).
     */


    void obstacle_2_Disappear() {
        animator.getEntity().setRemoveTexture();
        animator.startAnimation("obstacle2");
        animator.getEntity().setDisappear();
    }
}
