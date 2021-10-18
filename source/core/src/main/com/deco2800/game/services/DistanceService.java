
package com.deco2800.game.services;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;

/**
 * DistanceService handles the distance value in the game.
 */
public class DistanceService {

    //to solve the distance decrease bug.
    private static double preDistance=0;

    private final Entity player;

    /**
     * Constructor of DistanceService
     * @param player the player entity
     */
    public DistanceService(Entity player){
        this.player=player;
    }

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
        return pos.x + preDistance;
    }
}
