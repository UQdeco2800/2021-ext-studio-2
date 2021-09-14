package com.deco2800.game.components.buff;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;

public class DeBuff{
    /**
     * player
     */
    private Entity player;
    private Boolean stopFlag = false;
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
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent!=null){
            playerComponent.addDecreaseHealthImage();
        }
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
                this.player.getEvents().trigger(("deBuffStart"));
                Thread.sleep(500);
            }
        }
        if (playerComponent!=null){
            playerComponent.removePoisoningImage();
        }

    }

    public void removePoisoning()   {
        this.stopFlag = true;
    }
    /**
     * Player's movement will be slow
     * @throws InterruptedException
     */
    public void slowSpeed()  {
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent!=null){
            playerComponent.addDecreaseSpeedImage();
        }

        player.updateSpeed(new Vector2(2,8));

    }

    public void removeSlowSpeed()   {
        player.updateSpeed(new Vector2(4,8));
    }

    /**
     * when player is hunger,player will be slow
     */
    public void Hunger() {
        if(FoodDisplay.isHunger()){
            slowSpeed();
        }
    }

    /**
     * when player is thirst ,player will be lose hp
     */
    public void Thirsty() {
        if(WaterDisplay.isThirst()){
            decreaseHealth();
        }
    }
}