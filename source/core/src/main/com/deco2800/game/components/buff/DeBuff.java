package com.deco2800.game.components.buff;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;

public class DeBuff{
    /**
     * player
     */
    private final Entity player;
    private Boolean stopFlag = false;
    /**
     * player's status
     */
    private final CombatStatsComponent component;
    public DeBuff(Entity player){
        this.player = player;
        component = this.player.getComponent(CombatStatsComponent.class);
    }

    /**
     * Decreasing HEALTH
     */
    public void decreaseHealth(){
        component.addHealth(-10);
        player.getEvents().trigger("healthDown");
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent!=null){
            playerComponent.addDecreaseHealthImage();
        }
        removeBuff_Debuff();
    }

    /**
     * A poisoning status of Player
     * @throws InterruptedException
     */
    public void poisoning() throws InterruptedException {
        this.stopFlag = false;
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent!=null){
            playerComponent.addPoisoningImage();
        }
        for (int i = 0; i < 3; i++) {
            if (!stopFlag){
                component.addHealth(-10);
                player.getEvents().trigger("poisoned");
                Thread.sleep(500);
            }
        }
        if (playerComponent!=null){
            playerComponent.removePoisoningImage();
        }
        removeBuff_Debuff();
    }

    public void removePoisoning()   {
        player.getEvents().trigger("stopBuffDebuff");
        this.stopFlag = true;
    }

    /**
     * Player's movement will be slow
     */
    public void slowSpeed()  {
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        player.getEvents().trigger("speedDown");
        if (playerComponent!=null){
            playerComponent.addDecreaseSpeedImage();
        }
        //reducing speed
        player.updateSpeed(new Vector2(2,8));

        removeBuff_Debuff();
    }

    //removing buff/debuff after 1s

    public void removeBuff_Debuff() {
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                player.getEvents().trigger("stopBuffDebuff");
                timer.stop();
            }
        },1);
    }
    public void removeSlowSpeed()   {
        player.updateSpeed(new Vector2(4,8));
    }

    /**
     * when player is hunger,player will be slow
     */
    public void Hunger() {
        player.getEvents().trigger("hungry");
        if(FoodDisplay.isHunger()){
            slowSpeed();
        }
        removeBuff_Debuff();
    }

    /**
     * when player is thirst ,player will be lose hp
     */
    public void Thirsty() {
        player.getEvents().trigger("thirsty");
        if(WaterDisplay.isThirst()){
            decreaseHealth();
        }
        removeBuff_Debuff();
    }
}