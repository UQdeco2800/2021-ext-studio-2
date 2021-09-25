package com.deco2800.game.components.achievements;

import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.events.EventHandler;

/**
 * An achievement helper class which tracks properties across
 * the game and has a built-in event handler. This is similar
 * to an analytics system found in many software applications.
 * <p>
 * Triggering events:
 * <pre>{@code
 * AchievementsHelper.getInstance().trackItemPickedUpEvent();
 * AchievementsHelper.getInstance().trackSpaceshipAvoidSuccess();
 * }</pre>
 * <p>
 * Listening for events:
 * <pre>{@code
 * // Listening for events
 * AchievementsHelper.getInstance().getEvents()
 *      .addListener(AchievementsHelper.EVENT_UNLOCKED_ACHIEVEMENT_DISPLAYED);
 * }</pre>
 */
public class AchievementsHelper {
    public static final String EVENT_ITEM_PICKED_UP = "itemPickedUpEvent";
    public static final String EVENT_HEALTH = "healthEvent";
    public static final String EVENT_ACHIEVEMENT_BONUS_TRIGGERED = "updateBonusPoints";
    public static final String EVENT_SPACESHIP_AVOIDED = "spaceshipAvoided";
    public static final String EVENT_UNLOCKED_ACHIEVEMENT_DISPLAYED = "unlockedAchievementDisplayed";

    public static final String ITEM_FIRST_AID = "firstAid";
    public static final String ITEM_GOLD_COIN = "goldCoin";

    private static AchievementsHelper instance;
    private final EventHandler handler;

    private AchievementsHelper() {
        handler = new EventHandler();
    }

    /**
     * The class implements the singleton pattern. It creates
     * an instance of itself if it does not exist and returns
     * it. Events can be tracked and listened to, through
     * the instance.
     *
     * @return instance AchievementHelper class singleton
     */
    public static AchievementsHelper getInstance() {
        if (instance == null) {
            instance = new AchievementsHelper();
        }
        return instance;
    }

    /**
     * Access to the event handler object
     *
     * @return handler
     */
    public EventHandler getEvents() {
        return handler;
    }

    /**
     * Triggers an event when an item is picked up for
     * the achievements system
     */
    public void trackItemPickedUpEvent() {
        handler.trigger(EVENT_ITEM_PICKED_UP, "");
    }

    /**
     * Tracks changes in the health property of the player
     * and triggers an event for the achievements system
     *
     * @param health the player's health
     */
    public void trackHealthEvent(int health) {
        handler.trigger(EVENT_HEALTH, health);
    }

    /**
     * Triggers an event when a new bonus point event is encountered
     *
     * @param bonusPoints the bonus points
     */
    public void trackBonusPoints(int bonusPoints) {
        handler.trigger(EVENT_ACHIEVEMENT_BONUS_TRIGGERED, bonusPoints);
    }

    /**
     * Triggers an event when a first aid item is picked up by the player
     *
     * @param itemName name of the item
     */
    public void trackItemPickedUpEvent(String itemName) {
        handler.trigger(EVENT_ITEM_PICKED_UP, itemName);
    }

    /**
     * Triggers an event when an achievement popup is being showed mid game
     *
     * @param achievement unlocked achievement being displayed mid game
     */
    public void trackAchievementDisplayed(BaseAchievementConfig achievement) {
        handler.trigger(EVENT_UNLOCKED_ACHIEVEMENT_DISPLAYED, achievement);
    }

    /**
     * Triggers an event when the spaceship boss was avoided with success
     */
    public void trackSpaceshipAvoidSuccess() {
        handler.trigger(EVENT_SPACESHIP_AVOIDED);
    }
}
