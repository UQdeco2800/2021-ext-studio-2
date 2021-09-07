package com.deco2800.game.entities.configs;

import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines an Arraylist to store a basic set of achievement properties stored in
 * achievement config files (achievements.json) to be loaded by Achievement Factory
 */
public class AchievementConfigs {
    public List<BaseAchievementConfig> achievements = new LinkedList<>();
}
