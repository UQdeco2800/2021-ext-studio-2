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
public class PlantsDisapperTask extends DefaultTask implements PriorityTask {
	private final Entity target;
	private final int priority;
	private final float viewDistance;
	private final PhysicsEngine physics;
	private final DebugRenderer debugRenderer;


	/**
	 * The PlantsDisappearStart event will be triggered when the character is at a certain distance from the Plants
	 * @param target The entity to chase.
	 * @param priority Task priority when chasing (0 when not chasing).
	 * @param viewDistance Maximum distance from the entity at which chasing can start.
	 */
	public PlantsDisapperTask(Entity target, int priority, float viewDistance) {
		this.target = target;
		this.priority = priority;
		this.viewDistance = viewDistance;
		physics = ServiceLocator.getPhysicsService().getPhysics();
		debugRenderer = ServiceLocator.getRenderService().getDebug();
	}

	@Override
	public void start() {
		super.start();
		this.owner.getEntity().getEvents().trigger("PlantsDisappearStart");
	}

	/**
	 *
	 * @return priority
	 */
	@Override
	public int getPriority() {
		float dst = getDistanceToTarget();
		float targetY = target.getPosition().y;
		if (targetY > 6) {
			if (dst < (viewDistance + 2)) {
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
