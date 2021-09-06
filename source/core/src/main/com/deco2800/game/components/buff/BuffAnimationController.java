package com.deco2800.game.components.buff;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a  player entity's state and plays the animation
 * when eat some buff or duff thing
 */
public class BuffAnimationController extends Component {
  AnimationRenderComponent animator;

  @Override
  public void create() {
    super.create();
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    entity.getEvents().addListener("buffStart", this::animateBuff);
    entity.getEvents().addListener("deBuffStart", this::animateDeBuff);
  }


  /**
   * start buff animation
   */
  void animateBuff() {
    animator.startAnimation("buffIncrease");
    try {
      // continue two seconds
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    animator.stopAnimation();
  }

  /**
   * start debuff animation
   */
  void animateDeBuff() {
    animator.startAnimation("debuffDecrease");
    try {
      // continue two seconds
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    animator.stopAnimation();
  }
}