package com.deco2800.game.components.obstacle;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.buff.DeBuff;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.ParticleRenderComponent;
import com.deco2800.game.screens.MainGameScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to handle events of obstacles and enemies
 */
public class ObstacleEventHandler extends Component {

    /* Plant lock status for monster manual */
    public static boolean locked = true;

    /* Thorns lock status for monster manual */
    public static boolean locked2 = true;

    /* Meteorite lock status for monster manual */
    public static boolean locked3 = true;

    /* Spaceship lock status for monster manual */
    public static boolean locked_ufo = true;

    /**
     * The types of obstacles and enemies are used to determine the type of entity that triggers the event.
     */
    public enum ObstacleType {
        PLANTS_OBSTACLE, THORNS_OBSTACLE, METEORITE, FLYING_MONKEY, FACE_WORM, SPACESHIP, SMALL_MISSILE, PORTAL_ENTRANCE, PORTAL_EXPORT, WEAPON, MAGMA;
    }

    private static final Logger logger = LoggerFactory.getLogger(ObstacleEventHandler.class);
    AnimationRenderComponent animator;
    ParticleRenderComponent particle;
    HitboxComponent hitboxComponent;
    ObstacleType obstacleType;
    private static boolean spaceshipAttack;
    private int count;
    private static final String COLLISION_START = "collisionStart";
    private static final String COLLISION_LOGGER_INFO = "collisionStart event for {} was triggered.";

    /**
     * Construct an ObstacleEventHandler and register the corresponding event according to the obstacleType.
     *
     * @param obstacleType The types of obstacles.
     */
    public ObstacleEventHandler(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
        this.count = 0;
    }

    @Override
    public void create() {
        hitboxComponent = this.entity.getComponent(HitboxComponent.class);
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        particle = this.entity.getComponent(ParticleRenderComponent.class);

        switch (obstacleType) {
            case PLANTS_OBSTACLE:
                entity.getEvents().addListener(COLLISION_START, this::plantsDisappear);
                entity.getEvents().trigger("poison");
                break;
            case THORNS_OBSTACLE:
                entity.getEvents().addListener(COLLISION_START, this::thornsDisappear);
                break;
            case METEORITE:
                entity.getEvents().addListener(COLLISION_START, this::meteoriteDisappear);
                break;
            case FACE_WORM:
                entity.getEvents().addListener(COLLISION_START, this::faceWormDisappear);
                break;
            case SPACESHIP:
                entity.getEvents().addListener(COLLISION_START, this::spaceShipAttack);
                entity.getEvents().addListener("spaceshipDispose", this::spaceshipDispose);
                spaceshipAttack = false;
                break;
            case SMALL_MISSILE:
                entity.getEvents().addListener(COLLISION_START, this::smallMissileAttack);
                break;
            case PORTAL_ENTRANCE:
                entity.getEvents().addListener(COLLISION_START, this::portalEntrance);
                break;
            case PORTAL_EXPORT:
                entity.getEvents().addListener(COLLISION_START, this::portalExport);
                break;
            case WEAPON:
                entity.getEvents().addListener(COLLISION_START, this::weaponDisappear);
                break;
            case MAGMA:
                entity.getEvents().addListener(COLLISION_START, this::magmaCollision);
                break;
            default:
                throw new TypeNotPresentException("No corresponding event.", null);
        }
    }

    /**
     * Setter for spaceshipAttack, used for test.
     *
     * @param spaceshipAttack: space ship attack or not
     */
    public static void setSpaceshipAttack(boolean spaceshipAttack) {
        ObstacleEventHandler.spaceshipAttack = spaceshipAttack;
    }

    /**
     * When the monitored event is triggered, play the plants explosion animation, and disable the
     * plants (let it disappear).
     */
    void plantsDisappear(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }
        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits) && !PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }

        if (count == 0) { // Avoid an entity from repeatedly triggering an attack
            count++;

            particle.startEffect();
            logger.debug(COLLISION_LOGGER_INFO, entity.toString());
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("obstacles");
            animator.getEntity().setParticleTime(1.3f);
            animator.getEntity().setDisappearAfterAnimation(1f, Entity.DisappearType.ANIMATION);
            locked = false;
            if (PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
                this.entity.setRemoveCollision();
            }
        }
    }
    /**
     * When the monitored event is triggered, play the mpc burning animation.
     */
  void magmaCollision(Fixture me, Fixture other) {

      if (hitboxComponent.getFixture() != me) {
          // Not triggered by hitbox, ignore
          return;
      }

      if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits) && !PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
          // Doesn't match our target layer, ignore
          return;
      }
        entity.getEvents().trigger("burn");
      if (count == 0) { // Avoid an entity from repeatedly triggering an attack
          count++;
          logger.debug(COLLISION_LOGGER_INFO, entity.toString());
          this.entity.getEvents().trigger("burn");
          if (PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
              this.entity.setRemoveCollision();
          }
      }


    }

    /**
     * When the monitored event is triggered, play the thorns animation, and disable the
     * thorns (let it disappear) and slow the player.
     */
    void thornsDisappear(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }

        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits) && !PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }

        if (count == 0) { // Avoid an entity from repeatedly triggering an attack
            count++;
            if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
                MainGameScreen.setSlowPlayer(5f);
            }
            logger.debug(COLLISION_LOGGER_INFO, entity.toString());
            animator.getEntity().setRemoveTexture();
            particle.startEffect();
            animator.startAnimation("obstacle2");
            animator.getEntity().setParticleTime(2.5f);
            animator.getEntity().setDisappearAfterAnimation(1f, Entity.DisappearType.ANIMATION);
            locked2 = false;
            if (PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
                this.entity.setRemoveCollision();
            }
        }
    }

    /**
     * When the monitored event is triggered, play the meteorite explosion animation, and disable the
     * meteorite (let it disappear).
     */
    private void meteoriteDisappear(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }


        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE && (other.getFilterData().categoryBits != PhysicsLayer.CEILING)) {
            if (count == 0) { // Avoid an entity from repeatedly triggering an attack
                count++;
                particle.startEffect();
                logger.debug(COLLISION_LOGGER_INFO, entity.toString());
                animator.getEntity().setRemoveTexture();
                animator.startAnimation("stone1");
                animator.getEntity().setParticleTime(1f);
                animator.getEntity().setDisappearAfterAnimation(0.32f, Entity.DisappearType.ANIMATION);
                locked3 = false;
            }
        }

    }

    /**
     * When the monitored event is triggered, disable the face Worm (let it disappear).
     */
    void faceWormDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            logger.debug(COLLISION_LOGGER_INFO, entity.toString());
            animator.getEntity().setDisappearAfterAnimation(1.5f, Entity.DisappearType.ANIMATION);
        }

    }

    /**
     * This function is triggered when the spacecraft collides. Set spaceship to start attack state. Trigger the
     * floating sound of the spacecraft and unlock the monster manual.
     *
     * @param me    self fixture
     * @param other The fixture of the entity that started the collision
     */
    void spaceShipAttack(Fixture me, Fixture other) {
        if (spaceshipAttack) {
            return;
        }
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }

        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }


        SpaceshipAttackController.setSpaceshipAttack();
        entity.getEvents().trigger("spaceshipSound");

        logger.debug(COLLISION_LOGGER_INFO, entity.toString());
        spaceshipAttack = true;
        locked_ufo = false;
    }

    /**
     * When the remaining time of the spaceship attack is 0, the spaceship disappearance event will be triggered.
     */
    void spaceshipDispose() {
        logger.debug("spaceshipDispose event was triggered.");
        this.getEntity().setDispose();
    }

    /**
     * When the missile encounters a character, it triggers an explosion sound, an animation, and then the
     * missile disappears.
     *
     * @param me    self fixture
     * @param other The fixture of the entity that started the collision
     */
    void smallMissileAttack(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }

        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits) && !PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }

        logger.debug(COLLISION_LOGGER_INFO, entity.toString());
        entity.getEvents().trigger("missileSound");
        animator.startAnimation("bomb");
        particle.startEffect();
        animator.getEntity().setParticleTime(1f);
        animator.getEntity().setDisappearAfterAnimation(0.4f, Entity.DisappearType.ANIMATION);
        if (PhysicsLayer.contains(PhysicsLayer.WEAPON, other.getFilterData().categoryBits)) {
            this.entity.setRemoveCollision();
        }
    }

    /**
     * Triggered when the character touches the entrance of a new map.
     *
     * @param me    self fixture
     * @param other The fixture of the entity that started the collision
     */
    void portalEntrance(Fixture me, Fixture other) {
        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        MainGameScreen.setNewMapStatus(MainGameScreen.NewMap.START);
        logger.debug(COLLISION_LOGGER_INFO, entity.toString());
    }

    /**
     * Triggered when the character encounters a new map exit.
     *
     * @param me    self fixture
     * @param other The fixture of the entity that started the collision
     */
    void portalExport(Fixture me, Fixture other) {
        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        MainGameScreen.setNewMapStatus(MainGameScreen.NewMap.FINISH);
        logger.debug(COLLISION_LOGGER_INFO, entity.toString());
    }


    /**
     * Triggered when the weapon hit obstacles, ground and missile.
     *
     * @param me    self fixture
     * @param other The fixture of the entity that started the collision
     */
    void weaponDisappear(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }
        if (!PhysicsLayer.contains(PhysicsLayer.OBSTACLE, other.getFilterData().categoryBits) &&
                !PhysicsLayer.contains(PhysicsLayer.WALL, other.getFilterData().categoryBits) &&
                !PhysicsLayer.contains(PhysicsLayer.NPC, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        this.entity.setDispose();
        logger.debug(COLLISION_LOGGER_INFO, entity.toString());
    }


    /**
     * getter method for locked
     *
     * @return if the plant is locked
     */
    public static boolean isLocked() {
        return locked;
    }

    /**
     * getter method for locked2
     *
     * @return if the thorns is locked
     */
    public static boolean isLocked2() {
        return locked2;
    }

    /**
     * getter method for locked3
     *
     * @return if the meteorite is locked
     */
    public static boolean isLocked3() {
        return locked3;
    }

    /**
     * getter method for locked_ufo
     *
     * @return if the spaceship is locked
     */
    public static boolean isLocked_ufo() {
        return locked_ufo;
    }

    /**
     * getter method for spaceshipAttack
     *
     * @return if the spaceshipAttack is attack
     */
    public static boolean isSpaceshipAttack() {
        return spaceshipAttack;
    }


}
