package com.deco2800.game.entities.configs.achievements;

/**
 * Defines a basic set of achievement properties stored in achievement
 * config file (achievements.json) to be loaded by Achievement Factory
 */
public class BaseAchievementConfig {
     public int bonus = 0;
     public String name = "";
     public String type = "";
     public String iconPath = "";
     public String message = "";
     public boolean unlocked = false;
     public ConditionConfig condition = new ConditionConfig();
}
