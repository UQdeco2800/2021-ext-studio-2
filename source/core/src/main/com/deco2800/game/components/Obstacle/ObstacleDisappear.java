package com.deco2800.game.components.Obstacle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
//import com.sun.tools.javac.Main;


public class ObstacleDisappear extends Component {

    public enum ObstacleType {
        PlantsObstacle, ThornsObstacle, Meteorite,Ghost;
    };

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
            case Ghost:
                entity.getEvents().addListener("collisionStart",  this::GhostDisappear);
            default:
                logger.error("No corresponding event.");
        }
    }


    /**
     * When the monitored event is triggered, play the obstacle explosion animation, and disable the
     * obstacle (let it disappear).
     */
    void plantsDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            logger.info("PlantsDisappearStart was triggered.");
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("obstacles");
            animator.getEntity().setDisappearAfterAnimation(1f);
        }
    }

    /**
     * When the monitored event is triggered, play the obstacle explosion animation, and disable the
     * obstacle (let it disappear).
     */
    void thornsDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            logger.info("ThornsDisappearStart was triggered.");
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("obstacle2");
            animator.getEntity().setDisappearAfterAnimation(1f);
        }

    }

    private void meteoriteDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
            animator.getEntity().setRemoveTexture();
            animator.startAnimation("stone1");
            animator.getEntity().setDisappearAfterAnimation(0.32f);
        }

    }

    void GhostDisappear(Fixture me, Fixture other) {
        if (other.getFilterData().categoryBits != PhysicsLayer.METEORITE) {
//            animator.startAnimation("enemy2");
//            animator.getEntity().setRemoveTexture();
            animator.getEntity().setDisappearAfterAnimation(1.5f);
        }

    }

}
