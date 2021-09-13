package com.deco2800.game.components.Obstacle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.screens.MainGameScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
//import com.sun.tools.javac.Main;


public class ObstacleDisappear extends Component {

    public enum ObstacleType {
        PlantsObstacle, ThornsObstacle, Meteorite, FaceWorm;
    }

    private static final Logger logger = LoggerFactory.getLogger(ObstacleDisappear.class);
    AnimationRenderComponent animator;
    HitboxComponent hitboxComponent;
    ObstacleType obstacleType;

    public ObstacleDisappear(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }

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
            default:
                logger.error("No corresponding event.");
        }
    }


    /**
     * When the monitored event is triggered, play the obstacle explosion animation, and disable the
     * obstacle (let it disappear).
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

        logger.debug("PlantsDisappearStart was triggered.");
        animator.getEntity().setRemoveTexture();
        animator.startAnimation("obstacles");
        animator.getEntity().setDisappearAfterAnimation(1f);

    }

    /**
     * When the monitored event is triggered, play the obstacle explosion animation, and disable the
     * obstacle (let it disappear).
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

        MainGameScreen.setSlowPlayer(5f);
        logger.debug("ThornsDisappearStart was triggered.");
        animator.getEntity().setRemoveTexture();
        animator.startAnimation("obstacle2");
        animator.getEntity().setDisappearAfterAnimation(1f);
    }

    private void meteoriteDisappear(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }

        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            logger.debug("meteoriteDisappear was triggered.");
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("stone1");
            animator.getEntity().setDisappearAfterAnimation(0.32f);
        }

    }

    void faceWormDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            logger.debug("faceWormDisappear was triggered.");
            animator.getEntity().setDisappearAfterAnimation(1.5f);
        }

    }

}
