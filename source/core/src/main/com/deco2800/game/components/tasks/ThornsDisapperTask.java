package com.deco2800.game.components.tasks;

import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.services.ServiceLocator;

/**
 * When the character is a certain distance away from obstacles, trigger
 * the "ObstacleDisappearStart" event.
 */
public class ThornsDisapperTask extends DefaultTask implements PriorityTask {
    private final Entity target;
    private final int priority;
    private final float viewDistance;
    private final PhysicsEngine physics;
    private final DebugRenderer debugRenderer;


    /**
     * The ThornsDisappearStart event will be triggered when the character is a certain distance from Thorns.
     * @param target       The entity to chase.
     * @param priority     Task priority when chasing (0 when not chasing).
     * @param viewDistance Maximum distance from the entity at which chasing can start.
     */
    public ThornsDisapperTask(Entity target, int priority, float viewDistance) {
        this.target = target;
        this.priority = priority;
        this.viewDistance = viewDistance;
        physics = ServiceLocator.getPhysicsService().getPhysics();
        debugRenderer = ServiceLocator.getRenderService().getDebug();
    }

    @Override
    public void start() {
        super.start();
        this.owner.getEntity().getEvents().trigger("ThornsDisappearStart");
    }

    /**
     * The priority is determined based on the height of the person and the distance from the obstacle.
     * If the height of the character is greater than 5, the event will be triggered when the
     * distance to the obstacle is viewDistance+1.5. Otherwise, the event is triggered when the
     * distance to the obstacle is viewDistance.
     * @return priority
     */
    @Override
    public int getPriority() {
        float dst = getDistanceToTarget();
        float targetY = target.getPosition().y;
        if (targetY > 5) {
            if (dst < (viewDistance + 1.5)) {
                return priority;
            }
        } else {
            if (dst < viewDistance) {
                return priority;
            }
        }

        return -1;
    }

    private float getDistanceToTarget() {
        return owner.getEntity().getPosition().dst(target.getPosition());
    }
}
