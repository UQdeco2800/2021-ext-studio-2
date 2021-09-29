package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Obstacle.ObstacleDisappear;
import com.deco2800.game.components.npc.EnemyAnimationController;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.ObstacleAttackTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.entities.configs.GhostKingConfig;
import com.deco2800.game.entities.configs.NPCConfigs;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to create non-playable character (NPC) entities with predefined components.
 *
 * <p>Each NPC entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "NPCConfigs".
 *
 * <p>If needed, this factory can be separated into more specific factories for entities with
 * similar characteristics.
 */
public class NPCFactory {
  private static final NPCConfigs configs =
      FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");
  private static final Logger logger = LoggerFactory.getLogger(NPCConfigs.class);
  /**
   * Creates a Face Worm.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createFaceWorm(Entity target) {
    Entity FaceWorm = createBaseNPC(target);
    BaseEntityConfig config = configs.faceWorm;

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset("images/Facehugger.atlas", TextureAtlas.class));
    animator.addAnimation("baolian1", 0.1f, Animation.PlayMode.LOOP);

    FaceWorm
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
        .addComponent(animator)
        .addComponent(new EnemyAnimationController())
        .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.FaceWorm));

    FaceWorm.setScale(2.4f,2.4f);
    logger.debug("Create a Face Worm");
    return FaceWorm;
  }

  /**
   * Create Flying Monkey
   */
  public static Entity createFlyingMonkey(Entity target) {

    Entity Monkey = new Entity();

    AITaskComponent aiComponent =
            new AITaskComponent()
                    .addTask(new ObstacleAttackTask(target,10,6f));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/monkey.atlas", TextureAtlas.class));

    animator.addAnimation("1m", 0.2f, Animation.PlayMode.LOOP);

    Monkey
            .addComponent(animator)
            .addComponent(aiComponent);

    animator.startAnimation("1m");
    Monkey.setScale(2.3f, 2.3f);
    logger.debug("Create a Flying Monkey");
    return Monkey;
  }

  /**
   * Create Spaceship
   */
  public static Entity createSpaceShip(Entity target) {

    Entity spaceship = new Entity();
//
//    AITaskComponent aiComponent =
//            new AITaskComponent()
//                    .addTask(new ObstacleAttackTask(target,10,6f));
//
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/spaceship.atlas", TextureAtlas.class));

    animator.addAnimation("spaceship1", 0.2f, Animation.PlayMode.LOOP);

    spaceship
//            .addComponent(new TextureRenderComponent("images/ufo.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.Spaceship))
            .addComponent(animator);

    spaceship.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
    animator.startAnimation("spaceship1");
    spaceship.setScale(10f, 10f);
    return spaceship;
  }

  /**
   * Create Space Ship
   */
  public static Entity createSmallMissile(Entity target) {
    BaseEntityConfig config = configs.smallMissile;
    Entity missile = new Entity();
//
//    AITaskComponent aiComponent =
//            new AITaskComponent()
//                    .addTask(new ObstacleAttackTask(target,10,6f));
//
//    AnimationRenderComponent animator =
//            new AnimationRenderComponent(
//                    ServiceLocator.getResourceService()
//                            .getAsset("images/monkey.atlas", TextureAtlas.class));

//    animator.addAnimation("1m", 0.2f, Animation.PlayMode.LOOP);

    missile
            .addComponent(new TextureRenderComponent("images/rocket-ship-launch.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
//            .addComponent(new ColliderComponent())
            .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
            .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.SmallMissile));

    missile.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.DynamicBody);
//    animator.startAnimation("1m");
    missile.setScale(1.5f, 0.75f);
//    logger.debug("Create a Flying Monkey");
    return missile;
  }


  /**
   * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
   *
   * @return entity
   */
  private static Entity createBaseNPC(Entity target) {
    AITaskComponent aiComponent =
        new AITaskComponent()
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 4f, 4f));
    Entity npc =
        new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
            .addComponent(aiComponent);

    PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc;
  }



  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
