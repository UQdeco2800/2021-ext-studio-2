package com.deco2800.game.components.achievements;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.PropertyListDefaults;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ServiceLocator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The class listens to game statistics given the forest area lifecycle
 * and emits new achievement events
 */
public class AchievementsStatsComponent extends Component {
    private static final List<BaseAchievementConfig> achievements = AchievementFactory.getAchievements();
    private static final Map<String, PropertyListDefaults> propertyList = AchievementFactory.getPropertyList();

    private Map<String, Double> gameConditions;

    /* TO//DO: setup achievement stats as Hashmap <property,value> */

    public AchievementsStatsComponent() {
        gameConditions = new LinkedHashMap<>();
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

    @Override
    public void create() {
        super.create();

        gameConditions = new LinkedHashMap<>();

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
        gameConditions.put("spaceshipAvoidSuccess", 1.0);
        checkForValidAchievements();
    }

    /**
     * Maintains the distance traveled by the main player character in meters
     */
    public void setDistance(double distance) {
        gameConditions.put("distance", distance);
        checkForValidAchievements();
    }

    /**
     * Maintains the current health of player
     *
     * @param health player's changed health
     */
    public void setHealth(int health) {
        gameConditions.put("health", (double) health);
        checkForValidAchievements();
    }

    /**
     * Maintains the in game time
     *
     * @param time current game time
     */
    public void setTime(long time) {
        gameConditions.put("time", (double) time);
        checkForValidAchievements();
    }

    /**
     * Maintains the current score
     *
     * @param score the current game score
     */
    public void setScore(int score) {
        gameConditions.put("score", (double) score);
        checkForValidAchievements();
    }

    @Override
    public void update() {
        setTime(ServiceLocator.getTimeSource().getTime());
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
        increment("firstAids");
        checkForValidAchievements();
    }

    /**
     * Fetches the value for that particular property and increments it
     * @param propertyName name of the achievement condition
     */
    public void increment(String propertyName) {
        // Get the current property value if any, and increment it.
        // The default value of the property comes from the JSON config.
        int defaultValue = propertyList.get(propertyName).defaultValue;
        double propertyValue = gameConditions.getOrDefault(propertyName, (double) defaultValue);
        propertyValue += 1;
        gameConditions.put(propertyName, propertyValue);
    }

    public void setFirstAidByVal(int firstAids) {
        gameConditions.put("firstAids", (double) firstAids);
        checkForValidAchievements();
    }

    /**
     * Maintains the count of the number of gold coins picked up
     */
    private void setGold() {
        increment("gold");
        checkForValidAchievements();
    }

    public void setGoldByVal(int gold) {
        gameConditions.put("gold", (double) gold);
        checkForValidAchievements();
    }

    /**
     * Maintains the count of the number of items picked up
     */
    public void setItemCount() {
        increment("itemCount");
        checkForValidAchievements();
    }

    public void setItemCountByVal(int itemCount) {
        gameConditions.put("itemCount", (double) itemCount);
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

        for (String cond : propertyList.keySet()) {
            // Operation to perform when checking if achievement property has been satisfied or not
            String operation = propertyList.get(cond).operation;
            // Default game variable values
            double defaultConditionVal = propertyList.get(cond).defaultValue;
            // Current game variable values (current time, score etc)
            double currentPropertyVal = gameConditions.getOrDefault(cond, defaultConditionVal);

            int conditionVal = achievement.getConditionMap().get(cond);

            if (conditionVal != -1) {
                if (cond.equals("time")) {
                    // Convert minutes to milliseconds, since the time in config is supplied in minutes
                    conditionVal = conditionVal * 60000;
                }
                // Perform comparisons to check if conditions of the achievement have been satisfied
                switch (operation) {
                    case "==":
                        valid = conditionVal == currentPropertyVal;
                        break;
                    case "<=":
                        valid = conditionVal <= currentPropertyVal;
                        break;
                    default:
                        // Unknown operation
                        valid = false;
                        break;
                }
                // Stop looping if a condition of achievement was not satisfied
                if (!valid) {
                    break;
                }
            }
        }

        // Finally, if the achievement is valid, unlock it.
        if (valid) {
            achievement.unlocked = true;
            // Emit an event if the achievement is valid
            entity.getEvents().trigger("updateAchievement", achievement);
        }

        return valid;
    }


}
