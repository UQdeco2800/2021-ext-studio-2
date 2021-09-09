package com.deco2800.game.entities.factories;

import com.deco2800.game.components.achievements.AchievementsDisplay;
import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.entities.Entity;
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
 * "achievements.json".
 */
public class AchievementFactory {
    private static final AchievementConfigs configs =
            FileLoader.readClass(AchievementConfigs.class, "configs/achievements.json");

    /**
     * Returns a list of achievements from the "achievements.json" file
     * @return achievements - A list of achievements inflated from the JSON file
     */
    public static Entity createAchievementEntity(){
        return new Entity()
                .addComponent(new AchievementsStatsComponent())
                .addComponent(new AchievementsDisplay());
    }

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

    public static BaseAchievementConfig getAchievementByNameAndType(String name, String type){
        BaseAchievementConfig achievement = new BaseAchievementConfig();

        for (BaseAchievementConfig a : AchievementFactory.getAchievements()) {
            if (name.equals(a.name) && type.equals(a.type)) {
                achievement = a;
            }
        }

        return achievement;
    }
}
