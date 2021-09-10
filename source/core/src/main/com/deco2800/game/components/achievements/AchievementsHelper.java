package com.deco2800.game.components.achievements;

import com.deco2800.game.events.EventHandler;

/**
 * An achievement helper class which tracks properties across
 * the game and has a built-in event handler. This is similar
 * to an analytics system found in many software applications.
 *
 * Example:
 * AchievementsHelper.getInstance().trackItemPickedUpEvent();
 */
public class AchievementsHelper {
    public static final String ITEM_PICKED_UP_EVENT = "itemPickedUpEvent";
    public static final String HEALTH_EVENT = "healthEvent";

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
        handler.trigger(ITEM_PICKED_UP_EVENT);
    }

    /**
     * Tracks changes in the health property of the player
     * and triggers an event for the achievements system
     * @param health the player's health
     */
    public void trackHealthEvent(int health) {
        handler.trigger(HEALTH_EVENT, health);
    }

}
