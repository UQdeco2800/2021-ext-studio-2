package com.deco2800.game.components.buff;

import com.badlogic.gdx.utils.Timer;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.concurrency.AsyncTaskQueue;
import com.deco2800.game.entities.Entity;

public class Buff {
    /**
     * player
     */
    private final Entity player;
    /**
     * player's status
     */
    private final CombatStatsComponent component;

    public Buff(Entity player) {
        this.player = player;
        component = this.player.getComponent(CombatStatsComponent.class);
    }

    // Increasing Player Health
    public void addHealth() {
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent != null) {
            playerComponent.addIncreaseHealthImage();
        }

        player.getEvents().trigger("healthUp");

        CombatStatsComponent combatStatsComponent = this.player.getComponent(CombatStatsComponent.class);
        if (combatStatsComponent.getHealth() + 10 > 100) {
            component.addHealth(100 - combatStatsComponent.getHealth());
        } else {
            component.addHealth(10);
        }

        removeBuff_Debuff();

        AsyncTaskQueue.enqueueTask(() -> {
            try {
                Thread.sleep(500);
                if (playerComponent != null) {
                    playerComponent.removeIncreaseHealthImage();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    //Increasing Player Health Limit
    public void increaseHealthLimit() {
        PlayerStatsDisplay playerComponent = this.player.getComponent(PlayerStatsDisplay.class);
        if (playerComponent != null) {
            playerComponent.addAddMaxHealthImage();
        }
        player.getEvents().trigger("health_limit_up");
        component.setHealthMax(component.getHealthMax() + 20);
        AsyncTaskQueue.enqueueTask(() -> {
            try {
                Thread.sleep(500);
                if (playerComponent != null) {
                    playerComponent.removeAddMaxHealthImage();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

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
}
