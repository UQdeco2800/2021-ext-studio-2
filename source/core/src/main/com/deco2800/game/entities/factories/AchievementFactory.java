package com.deco2800.game.entities.factories;

import com.deco2800.game.entities.configs.AchievementConfigs;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.FileLoader;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory to create Achievement entities with predefined conditions.
 *
 * <p>Each Achievement entity type should have a creation method that returns a corresponding entity.
 * Predefined achievement entity conditions can be loaded from configs stored as json files which are defined in
 * "achieve.json".
 */
public class AchievementFactory {
    private static final AchievementConfigs configs =
            FileLoader.readClass(AchievementConfigs.class, "configs/achieve.json");

    /**
     * Returns a list of achievements from the "achieve.json" file
     * @return achievements - A list of achievements inflated from the JSON file
     */
    public static List<BaseAchievementConfig> getAchievements(){
        return configs.achievements;
    }

    /**
     * Returns a list of paths to corresponding achievement texture images
     * @return paths - A list of paths inflated from achievement list generated using getAchievements()
     */
    public static String[] getTextures(){
        return configs.achievements
                .stream().map(achievement -> achievement.iconPath).collect(Collectors.toList())
                .toArray(new String[configs.achievements.size()]);
    }
}
