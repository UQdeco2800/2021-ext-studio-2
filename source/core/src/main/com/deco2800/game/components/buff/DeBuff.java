package com.deco2800.game.components.buff;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;
import com.deco2800.game.services.ServiceLocator;

import java.time.chrono.IsoEra;

public class DeBuff extends Component {
    /**
     * player
     */
    private final Entity player;
    private Boolean stopFlag = false;
    /**
     * player's status
     */
    private CombatStatsComponent component;
    public DeBuff(Entity player){
        this.player = player;
        component = this.player.getComponent(CombatStatsComponent.class);
    }


    @Override
    public void create() {
        super.create();
        entity.getEvents().addListener("poison", this::poisoning);
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
     */
    public void poisoning() {
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        //triggering player poison status
        player.getEvents().trigger("poisoned");
        long lasthealthdeductiontime = 0;
        int counter=0;
        do {
            long time = ServiceLocator.getTimeSource().getTime();
            if (time - lasthealthdeductiontime >= 500) {
                ++counter;
                lasthealthdeductiontime = time;
                component.addHealth(-5);
            }
        } while (counter != 2);
        removeBuff_Debuff();
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

    //removing buff/debuff after 5s

    public void removeBuff_Debuff() {
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                player.getEvents().trigger("stopBuffDebuff");
                timer.stop();
            }
        },5);
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