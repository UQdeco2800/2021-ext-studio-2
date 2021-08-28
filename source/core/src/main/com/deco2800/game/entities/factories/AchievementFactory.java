package com.deco2800.game.entities.factories;

import com.deco2800.game.entities.configs.AchievementConfigs;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.FileLoader;

import java.util.List;
import java.util.stream.Collectors;

public class AchievementFactory {
    private static final AchievementConfigs configs =
            FileLoader.readClass(AchievementConfigs.class, "configs/achieve.json");

    public static List<BaseAchievementConfig> getAchievements(){
        return configs.achievements;
    }

    public static String[] getTextures(){
        return configs.achievements
                .stream().map(achievement -> achievement.iconPath).collect(Collectors.toList())
                .toArray(new String[configs.achievements.size()]);
    }
}
