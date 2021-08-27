package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Obstacle.ObstacleDispare;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.npc.ObstacleAnimationController;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.ObstacleDisapperTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {

	/**
	 * Creates a tree entity.
	 *
	 * @return entity
	 */

	public static Entity createObstacle(Entity target) {
		//AnimationRenderComponent animator = new AnimationRenderComponent("images/ghost.atlas");
		Entity obstacle = new Entity();

		AITaskComponent aiComponent =
				new AITaskComponent()
						.addTask(new ObstacleDisapperTask(target, 10, 1.7f));

		AnimationRenderComponent animator =
				new AnimationRenderComponent(
						ServiceLocator.getResourceService().getAsset("images/airport.atlas", TextureAtlas.class));
		animator.addAnimation("enemy2", 0.2f, Animation.PlayMode.LOOP);

		obstacle.addComponent(new TextureRenderComponent("images/enemy2.png"))
				.addComponent(new PhysicsComponent())
				//  .addComponent(new ObstacleDispare())
//				.addComponent(new ObstacleDispare())
				.addComponent(animator)
				.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
				.addComponent(new CombatStatsComponent(2000, 10))
				.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
				.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 10f))
				.addComponent(aiComponent)
				.addComponent(new ObstacleAnimationController());


		obstacle.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		obstacle.getComponent(TextureRenderComponent.class).scaleEntity();
//		tree.getComponent(ObstacleDispare.class).update();
//    tree.getComponent(AnimationRenderComponent.class).scaleEntity();

		//tree.scaleHeight(1f);
//    tree.setScale(4,2);

		PhysicsUtils.setScaledCollider(obstacle, 1f, 0.2f);
//打开动画
//    animator.startAnimation("enemy2");


		return obstacle;
	}

	/**
	 * Creates an invisible physics wall.
	 *
	 * @param width  Wall width in world units
	 * @param height Wall height in world units
	 * @return Wall entity of given width and height
	 */
	public static Entity createWall(float width, float height) {
		Entity wall = new Entity()
				.addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
				.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
		wall.setScale(width, height);
		return wall;
	}

	private ObstacleFactory() {
		throw new IllegalStateException("Instantiating static util class");
	}
}
