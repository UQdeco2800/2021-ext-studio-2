
package com.deco2800.game.services;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistanceService {
    private double distance=0;
    private Entity player;

    public DistanceService(Entity player){
        this.player=player;
    }

    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);

    public double getDistance() {
        Vector2 pos=player.getPosition();
        distance=Math.sqrt(pos.x*pos.x+pos.y*pos.y);
        return distance;
    }
}
