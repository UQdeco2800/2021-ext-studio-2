
package com.deco2800.game.services;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DistanceService handles the distance value in the game.
 */
public class DistanceService {
    private double distance=0;
    //to solve the distance decrease bug.
    private static double preDistance=0;
    private Entity player;

    /**
     * Constructor of DistanceService
     * @param player the player entity
     */
    public DistanceService(Entity player){
        this.player=player;
    }

    /**
     * Send logger messages to the console.
     */
    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);

    /**
     * Set the pre distance, which is the distance between the tunnel and the left hand side screen.
     * @param pre the distance between
     */
    public static void setPreDistance(double pre){
        preDistance+=pre;
    }

    /**
     * get the player's distance
     * @return return the player distance in the map.
     */
    public double getDistance() {
        Vector2 pos=player.getPosition();
        //distance=Math.sqrt(pos.x*pos.x+pos.y*pos.y);
        distance=pos.x+preDistance;
        return distance;
    }
}
