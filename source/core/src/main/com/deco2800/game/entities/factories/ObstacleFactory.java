package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.buff.DeBuff;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.entities.configs.ObstaclesConfigs;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.ParticleRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {
    private static final ObstaclesConfigs configs =
            FileLoader.readClass(ObstaclesConfigs.class, "configs/obstacles.json");
    private static final Logger logger = LoggerFactory.getLogger(ObstacleFactory.class);
    ParticleRenderComponent particle;

    /**
     * Type of Meteorite, different type means different size. more detail see spawnMeteorites() in ForestGameArea.java
     */
    public enum MeteoriteType {
        SmallMeteorite, MiddleMeteorite, BigMeteorite
    }

    /**
     * Creates a Plants Obstacle.
     *
     * @param target character.
     * @return the plants obstacle entity
     */
    public static Entity createPlantsObstacle(Entity target) {
        BaseEntityConfig config = configs.plant;
        Entity obstacle = createBaseObstacle(target, BodyType.StaticBody, "Plants");

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/obstacle_1.atlas", TextureAtlas.class));
        animator.addAnimation("obstacles", 0.2f, Animation.PlayMode.LOOP);

        ParticleRenderComponent particle =
                new ParticleRenderComponent("images/particle/plant2.party");

        obstacle
                .addComponent(particle)
                .addComponent(new TextureRenderComponent("images/obstacle_1_new.png"))
                .addComponent(animator)
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 10f))
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.PLANTS_OBSTACLE))
                .addComponent(new DeBuff(target));

        obstacle.getComponent(TextureRenderComponent.class).scaleEntity();
        obstacle.setScale(2, 3);
        PhysicsUtils.setScaledCollider(obstacle, 1f, 0.7f);

        logger.debug("Create a Plants Obstacle");

        return obstacle;
    }

    /**
     * Creates a Thorns Obstacle.
     *
     * @param target character.
     * @return the thorns obstacle entity
     */
    public static Entity createThornsObstacle(Entity target) {
        BaseEntityConfig config = configs.thorn;
        Entity obstacle = createBaseObstacle(target, BodyType.StaticBody, "Thorns");

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/obstacle_2.atlas", TextureAtlas.class));
        animator.addAnimation("obstacle2", 0.2f, Animation.PlayMode.LOOP);

        ParticleRenderComponent particle =
                new ParticleRenderComponent("images/particle/thron2.party");


        obstacle
                .addComponent(new TextureRenderComponent("images/obstacle2_vision2.png"))
                .addComponent(animator)
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
                .addComponent(particle)
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.THORNS_OBSTACLE));

        obstacle.getComponent(TextureRenderComponent.class).scaleEntity();
        PhysicsUtils.setScaledCollider(obstacle, 0.2f, 0.3f);
        obstacle.setScale(2, 2);
        obstacle.setZIndex(1);
        logger.debug("Create a Thorns Obstacle");

        return obstacle;
    }

    /**
     * Creates a Weapon attack effect for player.
     *
     */
    public static Entity createWeapon() {

        ParticleRenderComponent particle =
                new ParticleRenderComponent("images/particle/weapon.party");

        Entity weapon = new Entity("weapon")
                .addComponent(new PhysicsComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
                .addComponent(particle)
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.WEAPON));

        weapon.setScale(0.5f, 0.5f);
        weapon.setZIndex(1);
        particle.startEffect();
        weapon.setDisappearAfterParticle(2f, Entity.DisappearType.PARTICLE);
        logger.debug("Create a Weapon attack");
        return weapon;
    }


    /**
     * Create basic obstacle entity
     *
     * @param target   the character entity
     * @param bodyType body type, default = dynamic
     * @return obstacle entity
     */
    private static Entity createBaseObstacle(Entity target, BodyType bodyType, String type) {
        Entity obstacle =
                new Entity(type)
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE));
        obstacle.getComponent(PhysicsComponent.class).setBodyType(bodyType);
        return obstacle;
    }

    /**
     * Creates a Meteorite
     *
     * @param target character.
     * @return the meteorite obstacle entity
     */
    public static Entity createMeteorite(Entity target, float size, MeteoriteType meteoriteType) {
        BaseEntityConfig config = null;
        ParticleRenderComponent particle = null;
        switch (meteoriteType) {
            case BigMeteorite:
                config = configs.bigMeteorite;
                particle = new ParticleRenderComponent("images/particle/stoneBig.party");
                break;
            case MiddleMeteorite:
                config = configs.middleMeteorite;
                particle = new ParticleRenderComponent("images/particle/stoneMid.party");
                break;
            case SmallMeteorite:
                config = configs.smallMeteorite;
                particle = new ParticleRenderComponent("images/particle/stoneSmall.party");
                break;
            default:
                throw new TypeNotPresentException("Don't have this meteorite type", null);
        }

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/obstacle_Meteorite.atlas", TextureAtlas.class));

        animator.addAnimation("stone1", 0.08f, Animation.PlayMode.LOOP);



        Entity meteorite =
                new Entity("Meteorite")
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.METEORITE))
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.METEORITE))
                        .addComponent(new TextureRenderComponent("images/stone1.png"))
                        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                        .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
                        .addComponent(animator)
                        .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.METEORITE))
                        .addComponent(particle);

        meteorite.getComponent(TextureRenderComponent.class).scaleEntity();
        PhysicsUtils.setScaledCollider(meteorite, 1f, 1f);
        meteorite.setScale(size, size);
        logger.debug("Create a Meteorite");

        return meteorite;
    }

    /**
     * Creates a Thorns Obstacle.
     *
     * @param target character.
     * @return the thorns obstacle entity
     */
    public static Entity createPortal(Entity target, ObstacleEventHandler.ObstacleType type) {

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/portal.atlas", TextureAtlas.class));
        animator.addAnimation("portal_1", 0.2f, Animation.PlayMode.LOOP);

        ParticleRenderComponent particle =
                new ParticleRenderComponent("images/particle/portal.party");

        Entity portal =
                new Entity("Portal")
                        .addComponent(new PhysicsComponent())
                        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                        .addComponent(animator)
                        .addComponent(new ObstacleEventHandler(type))
                        .addComponent(particle);

        PhysicsUtils.setScaledCollider(portal, 2f, 4f);
        portal.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        particle.startEffect();
        animator.startAnimation("portal_1");
        portal.setScale(2, 4);

        logger.debug("Create a {}", type);

        return portal;
    }


    /**
     * Creates an invisible physics wall.
     *
     * @param width  Wall width in world units
     * @param height Wall height in world units
     * @return Wall entity of given width and height
     */
    public static Entity createWall(float width, float height, short layer) {
        Entity wall = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
                .addComponent(new ColliderComponent().setLayer(layer));
        wall.setScale(width, height);
        if (layer == PhysicsLayer.CEILING) {
            wall.getComponent(ColliderComponent.class).setMaskBits(PhysicsLayer.PLAYERCOLLIDER);
        }
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
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));


        rock.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        rock.getComponent(TextureRenderComponent.class).scaleEntity();


        return rock;
    }

    /**
     * Creates a firerock.
     *
     * @return Firrerock entity
     */
    public static Entity createMagma(Entity target) {
        Entity obstacle = createBaseObstacle(target, BodyType.StaticBody, "MAGMA");

        obstacle
                .addComponent(new TextureRenderComponent("images/firerock.jpg"))
                .addComponent(new CombatStatsComponent(2000, 10))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1f))
                .addComponent(new ObstacleEventHandler(ObstacleEventHandler.ObstacleType.MAGMA));

        return obstacle;
    }

    /**
     * Creates a nail.
     *
     * @return nail entity
     */
    public static Entity createNail(Entity target) {
        Entity obstacle = createBaseObstacle(target, BodyType.StaticBody, "Nail");

        obstacle
                .addComponent(new TextureRenderComponent("images/nail.jpg"))
                .addComponent(new CombatStatsComponent(2000, 5))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1f));

        return obstacle;
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
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));


        wood.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        wood.getComponent(TextureRenderComponent.class).scaleEntity();

        return wood;
    }

    private ObstacleFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }

}
