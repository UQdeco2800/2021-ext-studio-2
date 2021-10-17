package com.deco2800.game.files;

import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.stats.GameRecordUtils;
import com.deco2800.game.files.stats.GameRecords;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameRecordUtilsTest {

    MockedStatic<GameRecords> gameRecordsMockedStatic =
            Mockito.mockStatic(GameRecords.class);

    /**
     * Given the score, generate a scoreData object
     * @param score an arbitrary score
     * @return scoreData object with score and necessary defaults
     */
    private GameRecords.Score genScoreData(int score) {
        GameRecords.Score scoreData = new GameRecords.Score();
        scoreData.score = score;
        return scoreData;
    }

    /**
     * Given the type, generate achievement object.
     * @param type The type of achievement - GOLD, SILVER, BRONZE
     * @return achievement object with specified type and necessary defaults
     */
    private BaseAchievementConfig genAchievement(String name, String type){
        BaseAchievementConfig achievement = new BaseAchievementConfig();
        achievement.name = name;
        achievement.type = type;
        return achievement;
    }

    /**
     * Mock the list of achievements for a particular game that's returned by GameRecords
     */
    private void mockAchievementList(){
        List<BaseAchievementConfig> achievementList = new LinkedList<>();
        // Arbitrary list of achievements
        achievementList.add(genAchievement("A1", "SILVER"));
        achievementList.add(genAchievement("A1","BRONZE"));
        achievementList.add(genAchievement("A1","GOLD"));
        achievementList.add(genAchievement("A2", "BRONZE"));
        achievementList.add(genAchievement("A2","SILVER"));
        achievementList.add(genAchievement("A3","BRONZE"));
        achievementList.add(genAchievement("A4","SILVER"));
        achievementList.add(genAchievement("A5","GOLD"));
        // Mock the static method and return the generated list
        gameRecordsMockedStatic.when(() -> GameRecords.getAchievementsByGame(Mockito.anyInt()))
                .thenReturn(achievementList);
    }

    /**
     * Mock the list of scores that is returned by GameRecords
     */
    private void mockScoreList() {
        // Generate list of arbitrary scores
        List<GameRecords.Score> scoreList = new LinkedList<>();
        scoreList.add(genScoreData(1));
        scoreList.add(genScoreData(99));
        scoreList.add(genScoreData(23));
        scoreList.add(genScoreData(51));
        scoreList.add(genScoreData(23));
        // Mock the static method and return the generated list
        gameRecordsMockedStatic.when(GameRecords::getAllScores).thenReturn(scoreList);
    }

    @Test
    void getHighestScores() {
        // Setup mocks
        mockScoreList();

        List<GameRecords.Score> highestScores = GameRecordUtils.getHighestScores();

        int[] highestScoresExpected = {99, 51, 23, 23, 1};

        for (int i = 0; i < highestScoresExpected.length; i++) {
            assertEquals(highestScoresExpected[i], highestScores.get(i).score);
        }
    }

    @Test
    void getBestAchievementsByGame(){
        // Setup mocks
        mockAchievementList();

        List<BaseAchievementConfig> bestAchievements = GameRecordUtils.getBestAchievementsByGame(99);
        // Based on mocked data, should return only 5 achievements (only the highest tier ones)
        assertEquals(bestAchievements.size(), 5);

        // Based on mock data, there should be only one A1 achievement with type GOLD,
        // and one A2 achievement with type SILVER. Otherwise, assertion will fail.
        bestAchievements.forEach(achievement -> {
            if(achievement.name.equals("A1")){
                assertEquals(achievement.type, "GOLD");
            } else if(achievement.name.equals("A2")){
                assertEquals(achievement.type, "SILVER");
            }
        });
    }


    @AfterEach
    void afterEach() {
        gameRecordsMockedStatic.close();
    }
}
