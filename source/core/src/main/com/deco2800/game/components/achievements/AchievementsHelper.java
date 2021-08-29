package com.deco2800.game.components.achievements;

import com.deco2800.game.events.EventHandler;

public class AchievementsHelper {
    public static final String ITEM_PICKED_UP_EVENT = "itemPickedUpEvent";
    public static final String HEALTH_EVENT = "healthEvent";

    private static AchievementsHelper instance;
    private final EventHandler handler;

    private AchievementsHelper() {
        handler = new EventHandler();
    }

    public static AchievementsHelper getInstance() {
        if (instance == null) {
            instance = new AchievementsHelper();
        }
        return instance;
    }

    public EventHandler getEvents() {
        return handler;
    }

    public void trackItemPickedUpEvent() {
        handler.trigger(ITEM_PICKED_UP_EVENT);
    }

    public void trackHealthEvent(int health) {
        handler.trigger(HEALTH_EVENT, health);
    }

}
