package com.deco2800.game.files;


import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.ConditionConfig;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class GameRecordsTest {
    MockedStatic<AchievementsStatsComponent> achievementStatsMock =
            Mockito.mockStatic(AchievementsStatsComponent.class);


    /**
     * Using Reflection to change the value of a private static final variable
     *
     * @param field    the private static final variable field
     * @param newValue the value to replace
     */
    static void setFinalStatic(Field field, Object newValue) {
        try {
            // Make the field accessible
            field.setAccessible(true);

            // Remove the final modifier so that the field can be modified again
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            // Set the required value for the purposes of this test
            field.set(null, newValue);
        } catch (Exception ignored) {

        }
    }

    private List<BaseAchievementConfig> genBestAchievements() {
        List<BaseAchievementConfig> achievements = new LinkedList<>();

        // An arbitrary list of randomly locked & unlocked achievements
        achievements.add(genUnlockedAchievement("Veteran", "BRONZE"));
        achievements.add(genUnlockedAchievement("Veteran", "GOLD"));
        achievements.add(genUnlockedAchievement("Tool Master", "BRONZE"));
        achievements.add(genUnlockedAchievement("Tool Master", "GOLD"));
        achievements.add(genUnlockedAchievement("Game Breaker", "GOLD"));
        achievements.add(genUnlockedAchievement("Master", "SILVER"));
        achievements.add(genUnlockedAchievement("Master", "GOLD"));
        achievements.add(genUnlockedAchievement("Stranger", "BRONZE"));

        return achievements;
    }

    private List<BaseAchievementConfig> genUnlockedAchievementsList() {
        List<BaseAchievementConfig> achievements = new LinkedList<>();
        // An arbitrary list of randomly locked & unlocked achievements
        achievements.add(genRandomAchievement("Veteran", "BRONZE"));
        achievements.add(genRandomAchievement("Veteran", "SILVER"));
        achievements.add(genRandomAchievement("Veteran", "GOLD"));
        achievements.add(genRandomAchievement("Tool Master", "BRONZE"));
        achievements.add(genRandomAchievement("Tool Master", "SILVER"));
        achievements.add(genRandomAchievement("Tool Master", "GOLD"));
        achievements.add(genRandomAchievement("Game Breaker", "GOLD"));
        achievements.add(genRandomAchievement("Game Breaker", "SILVER"));
        achievements.add(genRandomAchievement("Master", "GOLD"));
        achievements.add(genRandomAchievement("Master", "SILVER"));
        achievements.add(genRandomAchievement("Master", "GOLD"));
        achievements.add(genRandomAchievement("Stranger", "BRONZE"));

        return achievements
                .stream().filter(achievement -> achievement.unlocked)
                .collect(Collectors.toList());
    }

    private BaseAchievementConfig genRandomAchievement(String name, String type) {
        BaseAchievementConfig achievement = new BaseAchievementConfig();
        achievement.bonus = 10;
        achievement.name = name;
        achievement.type = type;
        achievement.iconPath = "ignored";
        achievement.message = "ignored";
        achievement.unlocked = new Random().nextBoolean();

        ConditionConfig condition = new ConditionConfig();
        condition.itemCount = 99;
        condition.time = 99;
        condition.health = 99;
        condition.score = 99;
        condition.firstAids = 99;
        achievement.condition = condition;

        return achievement;
    }

    private BaseAchievementConfig genUnlockedAchievement(String name, String type) {
        BaseAchievementConfig achievement = new BaseAchievementConfig();
        achievement.bonus = 10;
        achievement.name = name;
        achievement.type = type;
        achievement.iconPath = "ignored";
        achievement.message = "ignored";
        achievement.unlocked = true;

        ConditionConfig condition = new ConditionConfig();
        condition.itemCount = 99;
        condition.time = 99;
        condition.health = 99;
        condition.score = 99;
        condition.firstAids = 99;
        achievement.condition = condition;

        return achievement;
    }

    @BeforeEach
    void beforeEach() throws NoSuchFieldException {
        // The feature depends on the Game Info feature
        setFinalStatic(GameInfo.class.getDeclaredField("path"),
                "test/files/gameInfoValidTest.json");
        setFinalStatic(GameInfo.class.getDeclaredField("location"),
                FileLoader.Location.LOCAL);

        // Changing the JSON file path so that the original game is not affected
        setFinalStatic(GameRecords.class.getDeclaredField("path"),
                "test/files/gameRecords.json");
        setFinalStatic(GameRecords.class.getDeclaredField("location"),
                FileLoader.Location.LOCAL);

        // Mock unlocked achievements
        achievementStatsMock.when(AchievementsStatsComponent::getUnlockedAchievements)
                .thenReturn(genUnlockedAchievementsList());
    }

    @AfterEach
    void afterEach() {
        achievementStatsMock.close();
    }

    @Test
    void missingRecordsFile() throws NoSuchFieldException {
        // Missing record file should not crash the game
        setFinalStatic(GameRecords.class.getDeclaredField("path"),
                "test/files/missingRecords.json");
        Map<Integer, GameRecords.Record> records = GameRecords.getRecords().records;
        assertTrue(records.isEmpty());
    }

    @Test
    void storesTheGameCountCorrectly() {
        int count = GameInfo.getGameCount();
        // Increment the game count
        GameInfo.incrementGameCount();

        assertEquals(GameInfo.getGameCount(), count + 1);
        // Store a record
        GameRecords.storeGameRecord();

        assertNotNull(GameRecords.getRecords());
        assertNotNull(GameRecords.getRecords().records);

        // The particular game count has a corresponding achievements
        assertFalse(GameRecords.getAchievementsByGame(count + 1).isEmpty());

        // The particular game count has a corresponding score
        assertEquals(GameRecords.getScoreByGame(count + 1).game, count + 1);

        assertTrue(GameRecords.getRecords().records.containsKey(String.valueOf(count + 1)));
    }

    @Test
    void correctlyReturnsBestAchievements() {
        // Reset test records file
        GameRecords.setRecords(new GameRecords.Records());

        // Mock unlocked achievements, but return the best achievements test set
        achievementStatsMock.when(AchievementsStatsComponent::getUnlockedAchievements)
                .thenReturn(genBestAchievements());

        GameRecords.storeGameRecord();

        // Make assertions based on the best achievements' dataset generated
        assertEquals(GameRecords.getBestRecords().size(), 5);
        assertEquals((int) GameRecords.getBestRecords().stream()
                .filter(achievement -> achievement.type.equals("BRONZE")).count(), 1);
        assertEquals((int) GameRecords.getBestRecords().stream()
                .filter(achievement -> achievement.type.equals("GOLD")).count(), 4);

    }

    @Test
    void getHighestScores() throws NoSuchFieldException {
        // Reset test records file
        GameRecords.setRecords(new GameRecords.Records());

        // Read the sample test scores file
        setFinalStatic(GameRecords.class.getDeclaredField("path"),
                "test/files/gameRecordScore.json");


        int[] expectedScoreOrder = {99999, 32321, 4343, 3233, 2332, 12, 9};
        final int[] i = {0};

        // Check if the scores were sorted properly
        List<GameRecords.Score> highestScores = GameRecords.getHighestScores();

        highestScores.forEach(scoreData -> {
            assertEquals(expectedScoreOrder[i[0]], scoreData.score);
            ++i[0];
        });

        assertEquals(expectedScoreOrder.length, highestScores.size());
        assertEquals(expectedScoreOrder.length, GameRecords.getAllScores().size());

        // Nevertheless, a dynamic test to check if its in descending order
        for (int j = 1; j < highestScores.size(); j++) {
            assertFalse(highestScores.get(0).getScore() < highestScores.get(1).getScore());
        }

    }

}
