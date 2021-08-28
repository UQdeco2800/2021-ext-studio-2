package com.deco2800.game.components.achievements;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ServiceLocator;

import java.util.List;

public class AchievementsStatsComponent extends Component {
    private final List<BaseAchievementConfig> achievements;

    private int health;
    private long time;
    private int itemCount;

    public AchievementsStatsComponent() {
        itemCount = -1;
        health = 100;
        time = -1;

        achievements = AchievementFactory.getAchievements();
    }

    public AchievementsStatsComponent(int health) {
        this.health = health;

        time = -1;
        itemCount = -1;
        achievements = AchievementFactory.getAchievements();
    }


    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("updateHealth", this::setHealth);
    }

    public void setHealth(int health) {
        this.health = health;
        checkForValidAchievements();
    }

    public void setTime(long time) {
        this.time = time;
        checkForValidAchievements();
    }

    @Override
    public void update() {
        long currentTime = ServiceLocator.getTimeSource().getTime();
        setTime(currentTime);
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        checkForValidAchievements();
    }


    public List<BaseAchievementConfig> getAchievements() {
        return achievements;
    }

    private void checkForValidAchievements() {
        achievements.forEach(this::isValid);
    }

    private boolean isValid(BaseAchievementConfig achievement) {
        boolean valid = false;

        if (achievement.unlocked) {
            return true;
        }

        if (achievement.condition.time != -1) {
            valid = achievement.condition.time * 1000L <= time;
            if (!valid) {
                return false;
            }
        }
        if (achievement.condition.health != -1) {
            valid = achievement.condition.health <= health;
            if (!valid) {
                return false;
            }
        }
        if (achievement.condition.itemCount != -1) {
            valid = achievement.condition.itemCount <= itemCount;
            if (!valid) {
                return false;
            }
        }

        if (valid) {
            achievement.unlocked = true;
            entity.getEvents().trigger("updateAchievement", achievement);
        }

        return true;
    }


}
