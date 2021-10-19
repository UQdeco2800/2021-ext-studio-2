package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.SoundComponent;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.npc.EnemyAnimationController;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.ObstacleAttackTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.entities.configs.NPCConfigs;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.ParticleRenderComponent;
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
                        ServiceLocator.getResourceService().getAsset("images/Facehugger.atlas",
                                TextureAtlas.class));
        animator.addAnimation("baolian1", 0.1f, Animation.PlayMode.LOOP);

        FaceWorm
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new EnemyAnimationController())
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.FACE_WORM));

        FaceWorm.setScale(2.4f, 2.4f);
        logger.debug("Create a Face Worm");
        return FaceWorm;
    }

    /**
     * Create Flying Monkey
     */
    public static Entity createFlyingMonkey(Entity target) {

        Entity monkey = new Entity("FlyingMonkey");

        AITaskComponent aiComponent =
                new AITaskComponent()
                        .addTask(new ObstacleAttackTask(monkey, target, 10, 6f));


        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/monkey.atlas", TextureAtlas.class));

        animator.addAnimation("1m", 0.2f, Animation.PlayMode.LOOP);

        ParticleRenderComponent particle =
                new ParticleRenderComponent("images/particle/monkey.party");

        monkey
                .addComponent(animator)
                .addComponent(aiComponent)
                .addComponent(particle)
                .addComponent(new SoundComponent(ObstacleEventHandler.ObstacleType.FLYING_MONKEY,
                        "sounds/monster_roar.mp3"));

        animator.startAnimation("1m");
        particle.startEffect();
        monkey.setScale(2.3f, 2.3f);
        logger.debug("Create a Flying Monkey");
        return monkey;
    }

    /**
     * Create Spaceship
     *
     * @param target the player entity
     * @return this spaceship entity
     */
    public static Entity createSpaceShip(Entity target) {

        Entity spaceship = new Entity("SpaceShip");

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/spaceship.atlas", TextureAtlas.class));

        animator.addAnimation("spaceship", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("spaceship_disappear", 0.13f, Animation.PlayMode.NORMAL);

        spaceship
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(animator)
                .addComponent(new SpaceshipAttackController().setPlayer(target))
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.SPACESHIP))
                .addComponent(new SoundComponent(ObstacleEventHandler.ObstacleType.SPACESHIP,
                        "sounds/spacecraft_floating.mp3"));


        spaceship.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        animator.startAnimation("spaceship");
        spaceship.setScale(10f, 10f);

        logger.debug("Create a spaceship");

        return spaceship;
    }

    /**
     * Create a missile
     * @param target the player entity
     * @return this missile entity
     */
    public static Entity createSmallMissile(Entity target) {
        BaseEntityConfig config = configs.smallMissile;
        Entity missile = new Entity("Missile");

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/missile.atlas", TextureAtlas.class));

        animator.addAnimation("missile1", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("bomb", 0.1f, Animation.PlayMode.LOOP);

        ParticleRenderComponent particle =
                new ParticleRenderComponent("images/particle/missile.party");

        missile
                .addComponent(animator)
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent())
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.SMALL_MISSILE))
                .addComponent(new SoundComponent(ObstacleEventHandler.ObstacleType.SMALL_MISSILE,
                        "sounds/missile_explosion.mp3"))
                .addComponent(particle);

        missile.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.DynamicBody);

        missile.setScale(3.5f, 3.5f);

        Vector2 boundingBox = missile.getScale().cpy().scl(0.5f, 0.2f);
        missile.getComponent(HitboxComponent.class).setAsBoxAligned(
                boundingBox, PhysicsComponent.AlignX.CENTER, PhysicsComponent.AlignY.CENTER);
        missile.setZIndex(1); // Generate missile above spaceship

        animator.startAnimation("missile1");

        logger.debug("Create a missile");
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
                        .addTask(new ChaseTask(target, 10, 10f, 10f));

        Entity npc =
                new Entity("FaceWorm")
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
