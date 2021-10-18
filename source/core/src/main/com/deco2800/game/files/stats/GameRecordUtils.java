package com.deco2800.game.files.stats;

import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.deco2800.game.files.stats.GameRecords.getAchievementsByGame;
import static com.deco2800.game.files.stats.GameRecords.getRecords;

public class GameRecordUtils {
    /**
     * Returns the list of scores (the highest score first)
     *
     * @return scores by descending order of score value
     */
    public static List<GameRecords.Score> getHighestScores() {
        // Sort
        List<GameRecords.Score> list = GameRecords.getAllScores()
                .stream()
                .sorted(Comparator.comparingInt(GameRecords.Score::getScore))
                .collect(Collectors.toList());
        // Reverse the list to get a descending order of scores
        Collections.reverse(list);

        return list;
    }

    /**
     * Loops through all the game records and returns the list of achievements
     * with the highest tier and ignores lower ones. In order words, the all-time
     * best achievements unlocked by the user are returned.
     *
     * @return bestAchievements the highest tier of achievements unlocked by user
     */
    public static List<BaseAchievementConfig> getAllTimeBestAchievements() {
        Map<String, BaseAchievementConfig> bestAchievementsMap = new LinkedHashMap<>();

        // Map of unlocked gold achievements
        getRecords().records.values().forEach(record -> record.achievements.forEach(achievement -> {
            if (achievement.type.equals("GOLD")) {
                bestAchievementsMap.put(achievement.name, achievement);
            }
        }));

        // Add silver achievements with no gold counterparts
        getRecords().records.values().forEach(record -> record.achievements.forEach(achievement -> {
            if (achievement.type.equals("SILVER")) {
                bestAchievementsMap.putIfAbsent(achievement.name, achievement);
            }
        }));

        // And bronze achievements with no superior counterparts
        getRecords().records.values().forEach(record -> record.achievements.forEach(achievement -> {
            if (achievement.type.equals("BRONZE")) {
                bestAchievementsMap.putIfAbsent(achievement.name, achievement);
            }
        }));

        // Return the list of best achievements
        return new LinkedList<>(bestAchievementsMap.values());
    }

    /**
     * Returns the achievements of the highest tier of a particular game.
     *
     * @param game the game number
     * @return bestAchievements the best achievements of a particular game
     */
    public static List<BaseAchievementConfig> getBestAchievementsByGame(int game) {
        List<BaseAchievementConfig> achievements = getAchievementsByGame(game);

        Map<String, BaseAchievementConfig> bestAchievements = new LinkedHashMap<>();

        // Add gold achievements to list
        achievements.forEach(achievement -> {
            if (achievement.type.equals("GOLD")) {
                bestAchievements.put(achievement.name, achievement);
            }
        });
        // Add silver achievements to list if gold counterparts are locked
        achievements.forEach(achievement -> {
            if (achievement.type.equals("SILVER")) {
                bestAchievements.putIfAbsent(achievement.name, achievement);
            }
        });
        // Add bronze to list if silver and gold counterparts are locked
        achievements.forEach(achievement -> {
            if (achievement.type.equals("BRONZE")) {
                bestAchievements.putIfAbsent(achievement.name, achievement);
            }
        });

        return new LinkedList<>(bestAchievements.values());
    }

    /**
     * Get the number of gold achievements that the user has unlocked
     *
     * @return count of unlocked gold achievements
     */
    public static int getGoldAchievementsCount() {
        Set<String> goldAchievements = new LinkedHashSet<>();

        for (Map.Entry<Integer, GameRecords.Record> e :
                getRecords().records.entrySet()) {
            GameRecords.Record value = e.getValue();
            value.achievements.forEach(a -> {
                if (a.type.equals("GOLD")) {
                    goldAchievements.add(a.name);
                }
            });
        }
        return goldAchievements.size();
    }

    /**
     * Returns a list of achievements that can be unlocked next
     *
     * @return list of bronze achievements yet to be unlocked
     */
    public static List<BaseAchievementConfig> getNextUnlockAchievements() {
        List<BaseAchievementConfig> betterAchievements = new LinkedList<>();

        Set<String> nextUnlocks = new LinkedHashSet<>();

        getAllTimeBestAchievements().forEach(achievement -> nextUnlocks.add(achievement.name));

        AchievementFactory.getAchievements().forEach(achievement -> {
            if (!nextUnlocks.contains(achievement.name) && achievement.type.equals("BRONZE")) {
                betterAchievements.add(achievement);
            }
        });
        return betterAchievements;
    }

}
