package com.deco2800.game.components.npc;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.obstacle.ObstacleDisappear;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SpaceshipAttackController extends Component {
    private static final Logger logger = LoggerFactory.getLogger(SpaceshipAttackController.class);
    protected Entity entity;
    protected boolean enabled = true;

    public enum SpaceshipAttack {
        Off,
        Start, // Used once in render
        On,
        Finish;
    }

    public enum AttackType {
        Low,
        Middle,
        High,
        Player;

        private static final List<AttackType> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static AttackType randomType() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public static SpaceshipAttack spaceshipState = SpaceshipAttack.Off;
    public static Vector2 positionHitSpaceship;

    public float spaceshipTime;
    private int counterSmallMissile;

    private Entity player;


    @Override
    public void create() {
        super.create();

        // reset variables
        spaceshipTime = 20f;
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
        spaceshipState = SpaceshipAttack.Start;
    }


    @Override
    public void update() {
        switch (spaceshipState) {
            case Start:
                spaceshipSceneBegins();
                break;
            case On:
                spaceshipAttackScene();
                break;
            case Finish:
            case Off:
                // do nothing
                break;
            default:
                logger.error("Not a valid spaceshipState");
        }
    }


    /**
     * Called when the component is disposed. Dispose of any internal resources here.
     */
    @Override
    public void dispose() {
        super.dispose();
    }


    private void spaceshipSceneBegins() {
        try {
            positionHitSpaceship = player.getPosition();
            player.setPosition(player.getPosition().x - 8, player.getPosition().y);
            player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);
        } catch (NullPointerException e) {
            logger.error("Need to setPlayer(player).");
        }

        // If the player is walking, stop
        if (!player.getComponent(KeyboardPlayerInputComponent.class).getWalkDirection().epsilonEquals(Vector2.Zero)) {
            player.getComponent(KeyboardPlayerInputComponent.class).keyUp(Input.Keys.D);
        }
        spaceshipState = SpaceshipAttack.On;
    }

    private void spaceshipAttackScene() {
        // set player position
        player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);

        // update remain time
        spaceshipTime -= ServiceLocator.getTimeSource().getDeltaTime();

        // get position
        Vector2 spaceshipPosition = this.getEntity().getPosition();
        Vector2 playerPosition = this.player.getPosition();

        // attack or stop
        if (spaceshipTime <= 0) {
            spaceshipState = SpaceshipAttack.Finish;
            this.getEntity().getEvents().trigger("spaceshipDispose");
            this.getEntity().getEvents().trigger("spawnPortalEntrance", spaceshipPosition.cpy().sub(-5, -5), ObstacleDisappear.ObstacleType.PortalEntrance);
            AchievementsHelper.getInstance().trackSpaceshipAvoidSuccess();

        } else if (spaceshipTime <= 10 && (counterSmallMissile % 50 == 0 || counterSmallMissile % 30 == 0)) {
            hardAttack(counterSmallMissile, spaceshipPosition, playerPosition);

        } else if (spaceshipTime <= 20 && (counterSmallMissile % 50 == 0 || counterSmallMissile % 30 == 0)) {
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
                this.getEntity().getEvents().trigger("spawnSmallMissile", getRandomPosition(spaceshipPosition, playerPosition, null, 1));
                break;
            case 330:
            case 360:
            case 390:
            case 420:
            case 450:
            case 480:
            case 510:
            case 540:
                this.getEntity().getEvents().trigger("spawnSmallMissile", getRandomPosition(spaceshipPosition, playerPosition, AttackType.Player, 1));
                break;
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
            case 1170:

                this.getEntity().getEvents().trigger("spawnSmallMissile", getRandomPosition(spaceshipPosition, playerPosition, null, 2));
                this.getEntity().getEvents().trigger("spawnSmallMissile", getRandomPosition(spaceshipPosition, playerPosition, AttackType.Player, 2));
                break;
        }
    }

    private Vector2 getRandomPosition(Vector2 spaceshipPosition, Vector2 playerPosition, AttackType type, int level) {
        AttackType attackType;
        Vector2 randomPosition;
        do {

            if (type == null) {
                do {
                    attackType = AttackType.randomType();
                } while (attackType == AttackType.Player);

            } else {
                attackType = type;
            }

            switch (attackType) {
                case Low:
                    randomPosition = spaceshipPosition.cpy().sub(0, -1.5f);
                    break;
                case Middle:
                    randomPosition = spaceshipPosition.cpy().sub(0, -4.5f);
                    break;
                case High:
                    randomPosition = spaceshipPosition.cpy().sub(0, -7.5f);
                    break;
                case Player:
                    randomPosition = new Vector2(spaceshipPosition.x, playerPosition.y + 1.5f);
                    break;
                default:
                    logger.error("No such type spaceship attack");
                    randomPosition = null;
            }
        } while (level == 2 && type == null && Math.abs(randomPosition.cpy().sub(playerPosition).y) < 2);

        return randomPosition;
    }


}
