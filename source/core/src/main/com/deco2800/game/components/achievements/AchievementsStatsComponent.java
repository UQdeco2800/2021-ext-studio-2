package com.deco2800.game.components.achievements;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ServiceLocator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class listens to game statistics given the forest area lifecycle
 * and emits new achievement events
 */
public class AchievementsStatsComponent extends Component {
    private static final List<BaseAchievementConfig> achievements = AchievementFactory.getAchievements();
    private int health;
    private long time;
    private int itemCount;
    private boolean bonusItemSign;
    private int score;
    private int firstAids;
    private int gold;
    private boolean spaceshipAvoidSuccess;
    private double distance;

    /* TO//DO: setup achievement stats as Hashmap <property,value> */

    public AchievementsStatsComponent() {
        initStats();
    }

    /**
     * Returns a list of all achievements
     *
     * @return achievements
     */
    public static List<BaseAchievementConfig> getAchievements() {
        return achievements;
    }

    /**
     * Returns a list of unlocked achievements
     *
     * @return unlockedAchievements
     */
    public static List<BaseAchievementConfig> getUnlockedAchievements() {
        return achievements
                .stream().filter(achievement -> achievement.unlocked)
                .collect(Collectors.toList());

    }

    /**
     * Lock all the achievements again
     */
    public static void resetAchievements() {
        achievements.forEach(achievement -> achievement.unlocked = false);
    }

    private void initStats() {
        itemCount = 0;
        health = 100;
        time = -1;

        bonusItemSign = false;

        score = 0;
        firstAids = 0;
        gold = 0;
        spaceshipAvoidSuccess = false;
        distance = 0;
    }

    @Override
    public void create() {
        super.create();

        initStats();

        resetAchievements();

        AchievementsHelper.getInstance().getEvents()
                .addListener(AchievementsHelper.EVENT_HEALTH, this::setHealth);
        AchievementsHelper.getInstance().getEvents()
                .addListener(AchievementsHelper.EVENT_ITEM_PICKED_UP, this::handleItemPickup);
        AchievementsHelper.getInstance().getEvents()
                .addListener(AchievementsHelper.EVENT_SPACESHIP_AVOIDED, this::setSpaceshipAvoidSuccess);
    }

    /**
     * Maintains the spaceshipAvoidSuccess status of the player
     */
    public void setSpaceshipAvoidSuccess() {
        this.spaceshipAvoidSuccess = true;
        checkForValidAchievements();
    }

    /**
     * Maintains the distance traveled by the main player character in meters
     */
    public void setDistance(double distance) {
        this.distance = distance;
        checkForValidAchievements();
    }

    /**
     * Maintains the current health of player
     *
     * @param health player's changed health
     */
    public void setHealth(int health) {
        this.health = health;
        checkForValidAchievements();
    }

    /**
     * Maintains the in game time
     *
     * @param time current game time
     */
    public void setTime(long time) {
        this.time = time;
        checkForValidAchievements();
    }

    /**
     * Maintains the current score
     *
     * @param score the current game score
     */
    public void setScore(int score) {
        this.score = score;
        checkForValidAchievements();
    }

    @Override
    public void update() {
        long currentTime = ServiceLocator.getTimeSource().getTime();
        setTime(currentTime);


        if (bonusItemSign) {
            AchievementsHelper.getInstance().getEvents().trigger("spawnBonusItem");
            bonusItemSign = false;
        }

        setScore(ServiceLocator.getScoreService().getScore());
        setDistance(ServiceLocator.getDistanceService().getDistance());

    }

    public void handleItemPickup(String itemName) {
        setItemCount();
        if (itemName == null || itemName.isEmpty()) {
            return;
        }

        switch (itemName) {
            case AchievementsHelper.ITEM_FIRST_AID:
                setFirstAid();
                break;
            case AchievementsHelper.ITEM_GOLD_COIN:
                setGold();
                break;
            default:
        }

    }

    /**
     * Maintains the count of the number of firstAids picked up
     */
    private void setFirstAid() {
        ++firstAids;
        checkForValidAchievements();
    }

    public void setFirstAidByVal(int firstAids) {
        this.firstAids = firstAids;
        checkForValidAchievements();
    }

    /**
     * Maintains the count of the number of gold coins picked up
     */
    private void setGold() {
        ++gold;
        checkForValidAchievements();
    }

    public void setGoldByVal(int gold) {
        this.gold = gold;
        checkForValidAchievements();
    }

    /**
     * Maintains the count of the number of items picked up
     */
    public void setItemCount() {
        ++itemCount;
        checkForValidAchievements();
    }

    public void setItemCountByVal(int itemCount) {
        this.itemCount = itemCount;
        checkForValidAchievements();
    }

    /**
     * Loop through achievements and check if each achievement is valid
     */
    private void checkForValidAchievements() {
        achievements.forEach(this::isValid);
    }

    /**
     * Checks if an achievement is valid
     * Unlocks the achievement if it is valid and triggers an event
     * pertaining to a new unlocked achievement
     *
     * @param achievement achievement to be validated
     * @return valid returns true if valid, false otherwise
     */
    private boolean isValid(BaseAchievementConfig achievement) {

        if (achievement.unlocked) {
            return true;
        }

        boolean valid = false;
        if (achievement.condition.time != -1) {
            valid = achievement.condition.time * 60000L <= time;
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
            valid = achievement.condition.itemCount == itemCount;
            if (!valid) {
                return false;
            }
        }

        if (achievement.condition.score != -1) {
            valid = achievement.condition.score <= score;
            if (!valid) {
                return false;
            }
        }

        if (achievement.condition.firstAids != -1) {
            valid = achievement.condition.firstAids == firstAids;
            if (!valid) {
                return false;
            }
        }

        if (achievement.condition.gold != -1) {
            valid = achievement.condition.gold == gold;
            if (!valid) {
                return false;
            }
        }

        if (achievement.condition.spaceshipAvoidSuccess) {
            valid = spaceshipAvoidSuccess;
            if (!valid) {
                return false;
            }
        }

        if (achievement.condition.distance != -1) {
            valid = achievement.condition.distance <= distance;
            if (!valid) {
                return false;
            }
        }
        if (valid) {
            achievement.unlocked = true;
            entity.getEvents().trigger("updateAchievement", achievement);
            bonusItemSign = true;
        }

        return true;
    }


}
