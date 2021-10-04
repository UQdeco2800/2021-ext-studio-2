package com.deco2800.game.components.obstacle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.sun.tools.javac.Main;

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
        PlantsObstacle, ThornsObstacle, Meteorite, FlyingMonkey, FaceWorm, Spaceship, SmallMissile, PortalEntrance, PortalExport;
    }

    private static final Logger logger = LoggerFactory.getLogger(ObstacleEventHandler.class);
    AnimationRenderComponent animator;
    HitboxComponent hitboxComponent;
    ObstacleType obstacleType;
    private static boolean spaceshipAttack;
    private int count;

    /**
     * Construct an ObstacleEventHandler and register the corresponding event according to the obstacleType.
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

        switch (obstacleType) {
            case PlantsObstacle:
                entity.getEvents().addListener("collisionStart", this::plantsDisappear);
                break;
            case ThornsObstacle:
                entity.getEvents().addListener("collisionStart", this::thornsDisappear);
                break;
            case Meteorite:
                entity.getEvents().addListener("collisionStart", this::meteoriteDisappear);
                break;
            case FaceWorm:
                entity.getEvents().addListener("collisionStart", this::faceWormDisappear);
                break;
            case Spaceship:
                entity.getEvents().addListener("collisionStart", this::spaceShipAttack);
                entity.getEvents().addListener("spaceshipDispose", this::spaceshipDispose);
                spaceshipAttack = false;
                break;
            case SmallMissile:
                entity.getEvents().addListener("collisionStart", this::smallMissileAttack);
                break;
            case PortalEntrance:
                entity.getEvents().addListener("collisionStart", this::portalEntrance);
                break;
            case PortalExport:
                entity.getEvents().addListener("collisionStart", this::portalExport);
                break;
            default:
                logger.error("No corresponding event.");
        }
    }

    /**
     * Setter for spaceshipAttack, used for test.
     * @param spaceshipAttack
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
        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }

        if (count == 0) { // Avoid an entity from repeatedly triggering an attack
            count++;

            logger.debug("collisionStart event for {} was triggered.", entity.toString());
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("obstacles");
            animator.getEntity().setDisappearAfterAnimation(1f);
            locked = false;
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

        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }

        if (count == 0) { // Avoid an entity from repeatedly triggering an attack
            count++;

            MainGameScreen.setSlowPlayer(5f);
            logger.debug("collisionStart event for {} was triggered.", entity.toString());
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("obstacle2");
            animator.getEntity().setDisappearAfterAnimation(1f);
            locked2 = false;
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

                logger.debug("collisionStart event for {} was triggered.", entity.toString());
                animator.getEntity().setRemoveTexture();
                animator.startAnimation("stone1");
                animator.getEntity().setDisappearAfterAnimation(0.32f);
                locked3 = false;
            }
        }

    }

    /**
     * When the monitored event is triggered, disable the face Worm (let it disappear).
     */
    void faceWormDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            logger.debug("collisionStart event for {} was triggered.", entity.toString());
            animator.getEntity().setDisappearAfterAnimation(1.5f);
        }

    }

    /**
     * This function is triggered when the spacecraft collides. Set spaceship to start attack state. Trigger the
     * floating sound of the spacecraft and unlock the monster manual.
     * @param me self fixture
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

        logger.debug("collisionStart event for {} was triggered.", entity.toString());
        spaceshipAttack = true;
        locked_ufo = false;
    }

    /**
     * When the remaining time of the spaceship attack is 0, the spaceship disappearance event will be triggered.
     */
    void spaceshipDispose() {
        logger.debug("spaceshipDispose event was triggered.");
        this.entity.setDispose();
    }

    /**
     * When the missile encounters a character, it triggers an explosion sound, an animation, and then the
     * missile disappears.
     *
     * @param me self fixture
     * @param other The fixture of the entity that started the collision
     */
    void smallMissileAttack(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }

        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        logger.debug("collisionStart event for {} was triggered.", entity.toString());
        entity.getEvents().trigger("missileSound");
        animator.startAnimation("bomb");
        animator.getEntity().setDisappearAfterAnimation(0.4f);

    }

    /**
     * Triggered when the character touches the entrance of a new map.
     * @param me self fixture
     * @param other The fixture of the entity that started the collision
     */
    void portalEntrance(Fixture me, Fixture other) {
        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        MainGameScreen.setNewMapStatus(MainGameScreen.NewMap.Start);
        logger.debug("collisionStart event for {} was triggered.", entity.toString());
    }

    /**
     * Triggered when the character encounters a new map exit.
     * @param me self fixture
     * @param other The fixture of the entity that started the collision
     */
    void portalExport(Fixture me, Fixture other) {
        if (!PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        MainGameScreen.setNewMapStatus(MainGameScreen.NewMap.Finish);
        logger.debug("collisionStart event for {} was triggered.", entity.toString());
    }

    /**
     * getter method for locked
     * @return if the plant is locked
     */
    public static boolean isLocked() {
        return locked;
    }

    /**
     * getter method for locked2
     * @return if the thorns is locked
     */
    public static boolean isLocked2() {
        return locked2;
    }

    /**
     * getter method for locked3
     * @return if the meteorite is locked
     */
    public static boolean isLocked3() {
        return locked3;
    }

    /**
     * getter method for locked_ufo
     * @return if the spaceship is locked
     */
    public static boolean isLocked_ufo() {
        return locked_ufo;
    }

    /**
     * getter method for spaceshipAttack
     * @return if the spaceshipAttack is attack
     */
    public static boolean isSpaceshipAttack() {
        return spaceshipAttack;
    }

    /**
     * getter method for count
     * @return how many times the event was triggered
     */
    public int getCount() {
        return count;
    }
}
