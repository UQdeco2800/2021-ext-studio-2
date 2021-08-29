package com.deco2800.game.entities.configs.achievements;

public class BaseAchievementConfig {
     public int bonus = 0;
     public String name = "";
     public String type = "";
     public String iconPath = "";
     public String message = "";
     public boolean unlocked = false;
     public ConditionConfig condition = new ConditionConfig();
}
