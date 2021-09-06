package com.deco2800.game.components.achievements;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.ItemFactory;
import com.deco2800.game.services.ServiceLocator;

import java.util.Random;

public class AchievementsBonusItems {
    Entity target;
    Entity bonusItem;
    private Random r;
    private int RandomSeed, result, ran1;

    private static final int RENDER_DURATION = 5000;

    public AchievementsBonusItems(Entity target) {
        this.target = target;
        RandomSeed = 1;
        result = 100;
        r = new Random(RandomSeed);
        ran1 = r.nextInt(result);
    }

    public Entity getTarget() {
        return this.target;
    }
    public void setBonusItem() {
        AchievementsHelper.getInstance().getEvents().addListener("spawnBonusItem", this::spawnBonusItem);
    }

    public void spawnBonusItem() {
        new Thread(() -> {
            try {
                r = new Random(++RandomSeed);
                ran1 = r.nextInt(++result);
                if (ran1 % 4 == 1) {
                bonusItem = ItemFactory.createWater(this.getTarget());
                } else if (ran1 % 4 == 2) {
                    bonusItem = ItemFactory.createMagicPotion(this.getTarget());
                } else if (ran1 % 4 == 3) {
                    bonusItem = ItemFactory.createSyringe(this.getTarget());
                } else {
                    bonusItem = ItemFactory.createBandage(this.getTarget());
                }
                Vector2 vector2 = new Vector2(this.target.getPosition());
                vector2.add(5,0);
                bonusItem.setPosition(vector2);
                ServiceLocator.getEntityService().register(bonusItem);
                Thread.sleep(RENDER_DURATION);
            }
            catch (InterruptedException ignored){
            }
        }).start();

    }
}
