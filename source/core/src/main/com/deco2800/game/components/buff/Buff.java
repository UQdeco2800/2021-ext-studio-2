package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;

public class Buff  {
    /**
     * player
     */
    private Entity player;
    /**
     * player's status
     */
    private CombatStatsComponent component;
    public Buff(Entity player){
        this.player=player;
        component = this.player.getComponent(CombatStatsComponent.class);
    }

    public void addHealth(){
        component.addHealth(10);
        this.player.getEvents().trigger(("buffStart"));

    }

    public void increaseHealthLimit(){
        component.setHealthMax(component.getHealthMax()+ 20);
    }

}
