package com.deco2800.game.components.tasks;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class ObstacleAttackTask extends DefaultTask implements PriorityTask {
    private final Entity target;
    private final Entity npc;
    private final int priority;
    private final float viewDistance;
    private GameArea gameArea;
    public static Boolean lock_use = true;

    public ObstacleAttackTask(Entity npc, Entity target, int priority, float viewDistance) {
        this.target = target;
        this.priority = priority;
        this.viewDistance = viewDistance;
        this.npc = npc;
        PhysicsEngine physics = ServiceLocator.getPhysicsService().getPhysics();
        DebugRenderer debugRenderer = ServiceLocator.getRenderService().getDebug();
    }


    @Override
    public void start() {
        super.start();

        npc.getEvents().trigger("spawnFaceWorm", enemyCreatePosition());
        npc.getEvents().trigger("roarSound");

    }


    /**
     * @return priority
     */
    @Override
    public int getPriority() {
        float dst = getDistanceToTarget();
        if (dst < viewDistance) {
            lock_use = false;
            return priority;
        }

        return -1;
    }

    public Vector2 enemyCreatePosition(){
        return owner.getEntity().getPosition();
    }

    private float getDistanceToTarget() {
        return owner.getEntity().getPosition().dst(target.getPosition());
    }
}
