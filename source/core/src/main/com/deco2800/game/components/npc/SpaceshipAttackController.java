package com.deco2800.game.components.npc;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class deals with the scene of the spaceship attack. Control character position and provide global variables to
 * MainGameScreen.java to control camera movement.
 */
public class SpaceshipAttackController extends Component {
    private static final Logger logger = LoggerFactory.getLogger(SpaceshipAttackController.class);

    /**
     * Spaceship attack state
     */
    public enum SpaceshipAttack {
        OFF,
        START, // Used once in render
        ON,
        FINISH;
    }

    /**
     * Spaceship attack type
     */
    public enum AttackType {
        LOW,
        MIDDLE,
        HIGH,
        PLAYER;

        private static final List<AttackType> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static AttackType randomType() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    /* Spaceship attack state */
    public static SpaceshipAttack spaceshipState;

    /* The position where the character just hit the spaceship */
    public static Vector2 positionHitSpaceship;

    /* Spaceship attack time */
    private float spaceshipTime;

    private int counterSmallMissile;
    private Entity player;
    private final String SPAWN_MISSILE_EVENT = "spawnSmallMissile";

    @Override
    public void create() {
        super.create();

        // reset variables
        spaceshipState = SpaceshipAttack.OFF;
        spaceshipTime = 22f; // the last 2s for spaceship disappear animation
        counterSmallMissile = 0;
    }

    /**
     * Set player entity, used with addComponent.
     *
     * @param player Player entity.
     * @return self
     */
    public SpaceshipAttackController setPlayer(Entity player) {
        this.player = player;
        return this;
    }


    /**
     * Generate monsters based on the position of the enemy monkey, which is called by render().
     */
    public static void setSpaceshipAttack() {
        spaceshipState = SpaceshipAttack.START;
    }

    /**
     * Get spaceshipState, used for test.
     *
     * @return the current state of spaceship attack.
     */
    public static SpaceshipAttack getSpaceshipState() {
        return spaceshipState;
    }

    /**
     * Set the state of the spacecraft, use it when testing
     *
     * @param spaceshipState the state of the spaceship attack
     */
    public static void setSpaceshipState(SpaceshipAttack spaceshipState) {
        SpaceshipAttackController.spaceshipState = spaceshipState;
    }

    @Override
    public void update() {
        switch (spaceshipState) {
            case START:
                spaceshipSceneBegins();
                break;
            case ON:
                spaceshipAttackScene();
                break;
            case FINISH:
            case OFF:
                // do nothing
                break;
            default:
                logger.error("Not a valid spaceshipState");
        }
    }


    /**
     * Set the attack start, character position, keyboard key state, character deceleration state.
     * Set attack state to on.
     */
    void spaceshipSceneBegins() {

        try {
            positionHitSpaceship = player.getPosition();
            player.setPosition(positionHitSpaceship.x - 8, player.getPosition().y);
            // offset characters automatically advance
            player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);
        } catch (NullPointerException e) {
            throw new NullPointerException("Need to setPlayer(player) when using SpaceshipAttackController");
        }

        // If the player is walking, stop
        if (!player.getComponent(KeyboardPlayerInputComponent.class).getWalkDirection().epsilonEquals(Vector2.Zero)) {
            player.getComponent(KeyboardPlayerInputComponent.class).keyUp(Input.Keys.D);
        }

        spaceshipState = SpaceshipAttack.ON;

        logger.info("Spaceship attack begins");
    }

    /**
     * The scene when the spacecraft attacked. Set the remaining time of the attack and launch the missile.
     */
    void spaceshipAttackScene() {
        // Remove the slowing effect
        if (MainGameScreen.isSlowPlayer()) {
            MainGameScreen.setSlowPlayer(false);
            MainGameScreen.setSlowPlayerTime(0);
        }

        // set player position
        player.setPosition(positionHitSpaceship.x - 8, player.getPosition().y); // for fix plant bounce bug
        player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);

        // update remain time
        spaceshipTime -= ServiceLocator.getTimeSource().getDeltaTime();

        // get position
        Vector2 spaceshipPosition = this.getEntity().getPosition();
        Vector2 playerPosition = this.player.getPosition();

        // stop attack
        if (spaceshipTime <= 0) {
            spaceshipState = SpaceshipAttack.FINISH;
            logger.info("Spaceship attack finish");
            this.getEntity().getEvents().trigger("spaceshipDispose");
            this.getEntity().getEvents().trigger("spawnPortalEntrance", spaceshipPosition.cpy().sub(-5, -5), ObstacleEventHandler.ObstacleType.PortalEntrance);
            AchievementsHelper.getInstance().trackSpaceshipAvoidSuccess();
            // hard attack
        } else if (spaceshipTime <= 12 && (counterSmallMissile % 50 == 0 || counterSmallMissile % 30 == 0)) {
            hardAttack(counterSmallMissile, spaceshipPosition, playerPosition);
            // easy attack
        } else if (spaceshipTime <= 22 && (counterSmallMissile % 50 == 0 || counterSmallMissile % 30 == 0)) {
            easyAttack(counterSmallMissile, spaceshipPosition, playerPosition);
        }
        counterSmallMissile++;
    }

    private void easyAttack(int counterSmallMissile, Vector2 spaceshipPosition, Vector2 playerPosition) {
        switch (counterSmallMissile) {
            case 0:
            case 100:
            case 200:
            case 300:
                this.getEntity().getEvents().trigger(SPAWN_MISSILE_EVENT, getRandomPosition(spaceshipPosition,
                        playerPosition, null, 1));
                break;
            case 330:
            case 360:
            case 390:
            case 420:
            case 450:
            case 480:
            case 510:
            case 540:
                this.getEntity().getEvents().trigger(SPAWN_MISSILE_EVENT, getRandomPosition(spaceshipPosition,
                        playerPosition, AttackType.PLAYER, 1));
                break;
            default:
                // do nothing
        }
    }

    private void hardAttack(int counterSmallMissile, Vector2 spaceshipPosition, Vector2 playerPosition) {
        switch (counterSmallMissile) {
            case 600:
            case 700:
            case 800:
            case 840:
            case 870:
            case 930:
            case 960:
            case 990:
            case 1020:
            case 1050:
            case 1080:
            case 1110:
            case 1140:
                this.getEntity().getEvents().trigger(SPAWN_MISSILE_EVENT, getRandomPosition(spaceshipPosition,
                        playerPosition, null, 2));
                this.getEntity().getEvents().trigger(SPAWN_MISSILE_EVENT, getRandomPosition(spaceshipPosition,
                        playerPosition, AttackType.PLAYER, 2));
                break;
            case 1200:
                this.getEntity().getComponent(AnimationRenderComponent.class).startAnimation("spaceship_disappear");
                break;
            default:
                // do nothing

        }
    }

    private Vector2 getRandomPosition(Vector2 spaceshipPosition, Vector2 playerPosition, AttackType type, int level) {
        AttackType attackType;
        Vector2 randomPosition;
        do {

            if (type == null) {
                do {
                    attackType = AttackType.randomType();
                } while (attackType == AttackType.PLAYER);

            } else {
                attackType = type;
            }

            switch (attackType) {
                case LOW:
                    // for small player: randomPosition = spaceshipPosition.cpy().sub(0, -0.5f);
                    // for big player
                    randomPosition = spaceshipPosition.cpy().sub(0, -1.5f);
                    break;
                case MIDDLE:
                    // for small player: randomPosition = spaceshipPosition.cpy().sub(0, -3.5f);
                    // for big player
                    randomPosition = spaceshipPosition.cpy().sub(0, -4.5f);
                    break;
                case HIGH:
                    // for small player: randomPosition = spaceshipPosition.cpy().sub(0, -6.5f);
                    // for big player
                    randomPosition = spaceshipPosition.cpy().sub(0, -7.5f);
                    break;
                case PLAYER:
                    // for small player: randomPosition = new Vector2(spaceshipPosition.x, playerPosition.y + 0.5f);
                    // for big player
                    randomPosition = new Vector2(spaceshipPosition.x, playerPosition.y + 1.5f);
                    break;
                default:
                    throw new TypeNotPresentException("No such type spaceship attack", null);
            }
        } while (level == 2 && type == null && Math.abs(randomPosition.cpy().sub(playerPosition).y) < 2);

        if (level == 1) {
            logger.debug("Easy missile attack: attackType = {}, position = {}", attackType, randomPosition);
        } else if (level == 2) {
            logger.debug("Hard missile attack: attackType = {}, position = {}", attackType, randomPosition);
        }

        return randomPosition;
    }


}
