package com.deco2800.game.components.buff;

import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerStatsDisplay;
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

        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent!=null){
            playerComponent.addIncreaseHealthImage();
        }
        component.addHealth(10);
    }

    public void increaseHealthLimit(){

        player.getEvents().trigger("health_limit_up");
        component.setHealthMax(component.getHealthMax()+ 20);
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                player.getEvents().trigger("stopBuffDebuff");
                timer.stop();
            }
        },1);
    }

}
