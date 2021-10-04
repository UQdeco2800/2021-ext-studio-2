package com.deco2800.game.components;

import com.badlogic.gdx.audio.Sound;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundComponent extends Component {
    private Sound sound;
    ObstacleEventHandler.ObstacleType obstacleType;
    private static final Logger logger = LoggerFactory.getLogger(SoundComponent.class);
public static int test = 0;
    /**
     * Create a component for sound play
     * @param soundPath file path of specific sound
     */
    public SoundComponent(ObstacleEventHandler.ObstacleType obstacleType, String soundPath) {
        this.sound  = ServiceLocator.getResourceService().getAsset(soundPath, Sound.class);
        this.obstacleType = obstacleType;
    }

    /**
     * for test to mock Sound
     * @param sound test sound
     */
    public SoundComponent(ObstacleEventHandler.ObstacleType obstacleType, Sound sound) {
        this.sound  = sound;
        this.obstacleType = obstacleType;
    }

    public void create() {
        switch (obstacleType) {
            case Spaceship:
                entity.getEvents().addListener("spaceshipSound", this::spaceshipSound);
                break;
            case SmallMissile:
                entity.getEvents().addListener("missileSound", this::missileSound);
                break;
            case FlyingMonkey:
                entity.getEvents().addListener("roarSound", this::roarSound);
                break;
            default:
                logger.debug("No corresponding sound.");
        }


    }


    public void spaceshipSound() {
        sound.play(1f, 0.6f, 0);
        test = 1;
        logger.debug("Floating sound for spaceship start to play");
    }

    public void missileSound() {
        sound.play(0.3f, 1f, 0);
        test = 1;
        logger.debug("Floating sound for spaceship start to play");
    }
    public void roarSound() {
        sound.play(0.3f, 1f, 0);
        logger.debug("Floating sound for spaceship start to play");
    }
}