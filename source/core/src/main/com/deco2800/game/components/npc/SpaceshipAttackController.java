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

public class SpaceshipAttackController extends Component {
    private static final Logger logger = LoggerFactory.getLogger(SpaceshipAttackController.class);
    protected Entity entity;
    protected boolean enabled = true;

    public enum spaceshipAttack {
        Off,
        Start, // Used once in render
        On,
        Finish;
    }

    public static spaceshipAttack spaceshipState = spaceshipAttack.Off;
    public static Vector2 positionHitSpaceship;
    public static float spaceshipTime = 10f;
    private int counterSmallMissile = 0;

    private Entity player;


    @Override
    public void create() {
        super.create();
        spaceshipState = spaceshipAttack.Off;
        positionHitSpaceship = null;
        spaceshipTime = 10f;
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
        spaceshipState = spaceshipAttack.Start;
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

    @Override
    public String toString() {
        return super.toString();
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
        spaceshipState = spaceshipAttack.On;
    }

    private void spaceshipAttackScene() {
        // set player position
        player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);

        // update remain time
        spaceshipTime -= ServiceLocator.getTimeSource().getDeltaTime();

        // attack or stop
        if (spaceshipTime <= 0) {
            spaceshipState = spaceshipAttack.Finish;
            this.getEntity().getEvents().trigger("spaceshipDispose");
            this.getEntity().getEvents().trigger("spawnPortalEntrance", new Vector2(85, 8), ObstacleDisappear.ObstacleType.PortalEntrance);

            AchievementsHelper.getInstance().trackSpaceshipAvoidSuccess();

        } else if (spaceshipTime <= 5 && counterSmallMissile % 100 == 0) {
            // 难
            switch (counterSmallMissile) {
                case 300:
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 5));
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 11));
                    break;
                case 400:
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 8));
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 11));
                    break;
                case 500:
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 5));
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 8));
            }

        } else if (spaceshipTime <= 10 && counterSmallMissile % 100 == 0) {
            // 简单
            switch (counterSmallMissile) {
                case 0:
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 5f));
                    break;
                case 100:
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 8f));
                    break;
                case 200:
                    this.getEntity().getEvents().trigger("spawnSmallMissile", new Vector2(85, 11f));
            }
        }

        counterSmallMissile++;
    }

}
