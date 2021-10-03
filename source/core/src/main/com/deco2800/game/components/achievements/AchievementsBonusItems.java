package com.deco2800.game.components.achievements;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.ItemFactory;
import com.deco2800.game.services.ServiceLocator;

import java.util.Random;

/**
 * The AchievementsBonusItems class is used to spawn bonus items at the game world
 * when there is a achievement unlocked by the player character
 */
public class AchievementsBonusItems {
    Entity target;
    Entity bonusItem;

    public AchievementsBonusItems(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return this.target;
    }

    /**
     * adding a "spawnBonusItem" event for the AchievementsHelper and it could triggered
     * when the player character unlocked one achievement
     */
    public void setBonusItem() {
        // Track the event when an achievement is being displayed on the screen
        AchievementsHelper.getInstance().getEvents().addListener(
                AchievementsHelper.EVENT_UNLOCKED_ACHIEVEMENT_DISPLAYED,
                (BaseAchievementConfig achievement) -> spawnBonusItem()
        );
    }

    /**
     * This func is used to spawn bonus items at the game world
     * when an achievement is triggered. The type of bonus items
     * spawned is random.
     */
    public void spawnBonusItem() {
        // Randomly generate an integer in the inclusive range (1, 4)
        int randomInt = new Random().nextInt(4);
        // Randomly select a bonus item to spawn based on above number
        switch (randomInt) {
            case 1:
                bonusItem = ItemFactory.createWater(this.getTarget());
                break;
            case 2:
                bonusItem = ItemFactory.createMagicPotion(this.getTarget());
                break;
            case 3:
                bonusItem = ItemFactory.createSyringe(this.getTarget());
                break;
            default:
                bonusItem = ItemFactory.createBandage(this.getTarget());
        }
        // Spawn the bonus item into the world
        Vector2 vector2 = new Vector2(this.target.getPosition());
        vector2.add(5, 0);
        bonusItem.setPosition(vector2);
        ServiceLocator.getEntityService().register(bonusItem);
    }

}
