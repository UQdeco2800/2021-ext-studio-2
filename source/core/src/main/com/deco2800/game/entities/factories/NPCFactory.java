package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
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

  /**
   * Creates a ghost entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createFaceWorm(Entity target) {
    Entity FaceWorm = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset("images/Facehugger.atlas", TextureAtlas.class));
    animator.addAnimation("baolian1", 0.1f, Animation.PlayMode.LOOP);
    //animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);

    FaceWorm
            //.addComponent(new TextureRenderComponent("images/Facehugger.png"))
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
        .addComponent(animator)
        .addComponent(new EnemyAnimationController())
        .addComponent(new ObstacleDisappear(ObstacleDisappear.ObstacleType.Ghost));

//    ghost.getComponent(AnimationRenderComponent.class).scaleEntity();
    FaceWorm.setScale(2.4f,2.4f);
    return FaceWorm;
  }
  /**
   * Create range NPC entity
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
            //.addComponent(new TextureRenderComponent("images/monkey_original.png"))
            .addComponent(animator)
            .addComponent(aiComponent);


    animator.startAnimation("1m");


    //ddddobstacle.getComponent(TextureRenderComponent.class).scaleEntity();

    Monkey.setScale(2.3f, 2.3f);



    return Monkey;
  }

  /**
   * Creates a ghost king entity.
   *
   * @param target entity to chase
   * @return entity
   */
//  public static Entity createGhostKing(Entity target) {
//    Entity ghostKing = createBaseNPC(target);
//    GhostKingConfig config = configs.ghostKing;
//
//    AnimationRenderComponent animator =
//        new AnimationRenderComponent(
//            ServiceLocator.getResourceService()
//                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
//    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);
//    animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);
//
//    ghostKing
//        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
//        .addComponent(animator)
//        .addComponent(new GhostAnimationController());
//
//    ghostKing.getComponent(AnimationRenderComponent.class).scaleEntity();
//    return ghostKing;
//  }

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
