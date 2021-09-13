package com.deco2800.game.components.achievements;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.score.ScoringSystemV1;
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
    private final ScoringSystemV1 scoringSystemV1;
    private int health;
    private long time;
    private int itemCount;

    private boolean bonusItemSign;


    private int score;
    private int firstAids;


    public AchievementsStatsComponent() {
        scoringSystemV1 = new ScoringSystemV1();

        initStats();
    }

    private void initStats(){
        itemCount = 0;
        health = 100;
        time = -1;

        bonusItemSign = false;

        score = 0;
        firstAids = 0;
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

        initStats();

        resetAchievements();

        AchievementsHelper.getInstance().getEvents()
                .addListener(AchievementsHelper.HEALTH_EVENT, this::setHealth);
        AchievementsHelper.getInstance().getEvents()
                .addListener(AchievementsHelper.ITEM_PICKED_UP_EVENT, this::handleItemPickup);
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


        if(bonusItemSign) {
            AchievementsHelper.getInstance().getEvents().trigger("spawnBonusItem");
            bonusItemSign = false;
        }

        setScore(scoringSystemV1.getScore());

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
            default:
        }

    }

    private void setFirstAid() {
        ++firstAids;
        checkForValidAchievements();
    }
    public void setFirstAidByVal(int firstAids) {
        this.firstAids = firstAids;
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
     * @param achievement
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

        if (valid) {
            achievement.unlocked = true;
            entity.getEvents().trigger("updateAchievement", achievement);
            bonusItemSign = true;
        }

        return true;
    }


}
