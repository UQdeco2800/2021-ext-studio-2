package com.deco2800.game.components.Obstacle;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.tasks.ThornsDisappearTask;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to an obstacle entity's state and plays the animation
 * when disappear events is triggered.
 */
public class ObstacleAnimationController extends Component {
	AnimationRenderComponent animator;
	private static final Logger logger = LoggerFactory.getLogger(ObstacleAnimationController.class);
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
		logger.info("PlantsDisappearStart was triggered.");
		animator.getEntity().setRemoveTexture();
		animator.startAnimation("obstacles");
		animator.getEntity().setDisappear();
	}

	/**
	 * When the monitored event is triggered, play the obstacle explosion animation, and disable the
	 * obstacle (let it disappear).
	 */
	void thornsDisappear() {
		logger.info("ThornsDisappearStart was triggered.");
		animator.getEntity().setRemoveTexture();
		animator.startAnimation("obstacle2");
		animator.getEntity().setDisappear();

	}

}
