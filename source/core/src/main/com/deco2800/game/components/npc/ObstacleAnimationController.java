package com.deco2800.game.components.npc;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to an obstacle entity's state and plays the animation
 * when disappear events is triggered.
 */
public class ObstacleAnimationController extends Component {
	AnimationRenderComponent animator;

	/**
	 * Start listening to ObstacleDisappearStart event on obstacles.
	 */
	@Override
	public void create() {
		super.create();
		animator = this.entity.getComponent(AnimationRenderComponent.class);
		entity.getEvents().addListener("PlantsDisappearStart", this::plantsDisappear);
		entity.getEvents().addListener("ThornsDisappearStart", this::thornsDisappear);
	}

	/**
	 * When the monitored event is triggered, play the obstacle explosion animation, and disable the
	 * obstacle (let it disappear).
	 */
	void plantsDisappear() {
		animator.getEntity().setRemoveTexture();
		animator.startAnimation("obstacles");
		animator.getEntity().setDisappear();
	}

	/**
	 * When the monitored event is triggered, play the obstacle explosion animation, and disable the
	 * obstacle (let it disappear).
	 */
	void thornsDisappear() {
		animator.getEntity().setRemoveTexture();
		animator.startAnimation("obstacle2");
		animator.getEntity().setDisappear();
	}

}
