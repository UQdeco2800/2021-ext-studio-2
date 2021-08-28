package com.deco2800.game.entities.factories;

import com.deco2800.game.entities.configs.AchievementConfigs;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.FileLoader;

import java.util.List;

public class AchievementFactory {
    private static final AchievementConfigs configs =
            FileLoader.readClass(AchievementConfigs.class, "configs/achieve.json");

    public static List<BaseAchievementConfig> getAchievements(){
        return configs.achievements;
    }
}
