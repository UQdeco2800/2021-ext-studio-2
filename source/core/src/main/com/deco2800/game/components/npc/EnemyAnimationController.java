package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a enemy entity's state and plays the animation when one
 * of the events is triggered.
 */
public class EnemyAnimationController extends Component {
  AnimationRenderComponent animator;

  @Override
  public void create() {
    super.create();
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    entity.getEvents().addListener("chaseStart", this::animateChase);
  }

  void animateChase() {
    animator.startAnimation("baolian1");
  }
}