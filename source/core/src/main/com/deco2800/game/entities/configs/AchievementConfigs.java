package com.deco2800.game.entities.configs;

import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.PropertyListDefaults;

import java.util.*;

/**
 * Defines an Arraylist to store a basic set of achievement properties stored in
 * achievement config files (achievements.json) to be loaded by Achievement Factory,
 * as well as the property list with operations that need to be conducted and necessary defaults.
 */
public class AchievementConfigs {
    public Map<String, PropertyListDefaults> propertyList = new LinkedHashMap<>();
    public List<BaseAchievementConfig> achievements = new LinkedList<>();
}
