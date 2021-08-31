package com.deco2800.game.components.buff;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;

public class DeBuff{
    /**
     * player
     */
    private Entity player;
    /**
     * player's status
     */
    private CombatStatsComponent component;
    public DeBuff(Entity player){
        this.player=player;
        component = this.player.getComponent(CombatStatsComponent.class);
        this.player = player;
    }

    /**
     * Decreasing HEALTH
     */
    public void decreaseHealth(){
        component.addHealth(-10);
        this.player.getEvents().trigger(("deBuffStart"));
    }

    /**
     * A poisoning status of Player
     * @throws InterruptedException
     */
    public void poisoning() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            component.addHealth(-10);
            this.player.getEvents().trigger(("deBuffStart"));
            Thread.sleep(200);
        }
    }

    /**
     * Player's movement will be slow
     * @throws InterruptedException
     */
    public void slowSpeed() throws InterruptedException {
        player.updateSpeed(new Vector2(2,8));
    }

}