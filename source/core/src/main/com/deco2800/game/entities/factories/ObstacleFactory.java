package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.npc.ObstacleAnimationController;
//import com.deco2800.game.components.npc.ObstacleAnimationController2;
import com.deco2800.game.components.tasks.PlantsDisapperTask;
import com.deco2800.game.components.tasks.ThornsDisapperTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
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

	public static Entity createPlantsObstacle(Entity target) {
		Entity obstacle = new Entity();

		AITaskComponent aiComponent =
				new AITaskComponent()
						.addTask(new PlantsDisapperTask(target, 10, 1.5f));

		AnimationRenderComponent animator =
				new AnimationRenderComponent(
						ServiceLocator.getResourceService().getAsset("images/obstacle_1.atlas", TextureAtlas.class));
		animator.addAnimation("obstacles", 0.2f, Animation.PlayMode.LOOP);


		obstacle.addComponent(new TextureRenderComponent("images/obstacle_1_new.png"))

				.addComponent(new PhysicsComponent())
				.addComponent(animator)
				.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
				.addComponent(new CombatStatsComponent(2000, 20))
				.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
				.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 10f))
				.addComponent(aiComponent)
				.addComponent(new ObstacleAnimationController());


		obstacle.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		obstacle.getComponent(TextureRenderComponent.class).scaleEntity();

		obstacle.setScale(2,3);

		PhysicsUtils.setScaledCollider(obstacle, 1f, 0.7f);

		return obstacle;
	}


	/**
	 * second obstacle
	 */
	public static Entity createThornsObstacle(Entity target) {
		//AnimationRenderComponent animator = new AnimationRenderComponent("images/ghost.atlas");
		Entity obstacle = new Entity();

		AITaskComponent aiComponent =
				new AITaskComponent()
						.addTask(new ThornsDisapperTask(target, 10, 1.3f));

		AnimationRenderComponent animator =
				new AnimationRenderComponent(
						ServiceLocator.getResourceService().getAsset("images/obstacle_2.atlas", TextureAtlas.class));
		animator.addAnimation("obstacle2", 0.2f, Animation.PlayMode.LOOP);

		obstacle.addComponent(new TextureRenderComponent("images/obstacle2_vision2.png"))
				.addComponent(new PhysicsComponent())
				//  .addComponent(new ObstacleDispare())
//				.addComponent(new ObstacleDispare())
				.addComponent(animator)
				.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
				.addComponent(new CombatStatsComponent(2000, 10))
				.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
				.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
				.addComponent(aiComponent)
				.addComponent(new ObstacleAnimationController());


		obstacle.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		obstacle.getComponent(TextureRenderComponent.class).scaleEntity();
//		tree.getComponent(ObstacleDispare.class).update();
//    tree.getComponent(AnimationRenderComponent.class).scaleEntity();

		//tree.scaleHeight(1f);
//    tree.setScale(4,2);


		PhysicsUtils.setScaledCollider(obstacle, 0.2f, 0.3f);
		obstacle.setScale(2,2);
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

	/**
	 * Creates a rock.
	 *
	 * @return Rock entity
	 */
	public static Entity createRock() {
		Entity rock = new Entity();

		rock.addComponent(new TextureRenderComponent("images/rock.jpg"))
				.addComponent(new PhysicsComponent())
				.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
				.addComponent(new CombatStatsComponent(2000, 10))
				.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
				.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
				.addComponent(new ObstacleAnimationController());


		rock.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		rock.getComponent(TextureRenderComponent.class).scaleEntity();

		return rock;
	}

	/**
	 * Creates a wood.
	 *
	 * @return Wood entity
	 */
	public static Entity createWood() {
		Entity wood = new Entity();

		wood.addComponent(new TextureRenderComponent("images/wood.jpg"))
				.addComponent(new PhysicsComponent())
				.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
				.addComponent(new CombatStatsComponent(2000, 10))
				.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
				.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
				.addComponent(new ObstacleAnimationController());


		wood.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		wood.getComponent(TextureRenderComponent.class).scaleEntity();

		return wood;
	}

	private ObstacleFactory() {
		throw new IllegalStateException("Instantiating static util class");
	}

}
