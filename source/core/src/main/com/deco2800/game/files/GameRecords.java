package com.deco2800.game.files;

import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.deco2800.game.files.FileLoader.Location.EXTERNAL;

public class GameRecords {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String RECORDS_FILE = "gameRecords.json";
    private static final String path = ROOT_DIR + File.separator + RECORDS_FILE;

    /**
     * Stores the records of the most recent game played into a JSON file
     * Note: Run this method only after the in game count has updated
     */
    public static void storeGameRecord() {
        Record record = new Record();

        // Storing game count
        int gameCount = GameInfo.getGameCount();
        record.game = gameCount;

        // Storing achievements
        record.achievements = AchievementsStatsComponent.getUnlockedAchievements();

        // Fetching the score
        ScoringSystemV1 scoringSystemV1 = new ScoringSystemV1();

        record.scoreData.score = scoringSystemV1.getScore();
        record.scoreData.game = gameCount;

        // Add the record
        Records records = getRecords();
        records.add(record);

        // Write updated records list JSON
        setRecords(records);
    }

    /**
     * @param game the game number (nth game played)
     * @return unlocked achievements of that particular game
     */
    public static List<BaseAchievementConfig> getAchievementsByGame(int game) {
        return getRecords().findByGame(game).achievements;
    }

    /**
     * @param game the game number (nth game played)
     * @return score of that particular game
     */
    public static Score getScoreByGame(int game) {
        return getRecords().findByGame(game).scoreData;
    }


    /**
     * Returns the list of scores
     * @return score of that particular game
     */
    public static List<Score> getAllScores() {
        return getRecords().records
                .values().stream()
                .map(record -> record.scoreData)
                .collect(Collectors.toList());
    }

    /**
     * Returns the list of scores (highest score first)
     * @return scores by descending order of score value
     */
    public static List<Score> getHighestScores(){
        // sort
        List<Score> list = getAllScores()
                .stream()
                .sorted(Comparator.comparingInt(Score::getScore))
                .collect(Collectors.toList());
        // reverse for descending
        Collections.reverse(list);

        return list;
    }


    public static List<BaseAchievementConfig> getBestRecords() {
        Map<String, BaseAchievementConfig> bestAchievementsMap = new LinkedHashMap<>();

        /* The following algorithm to extract the best achievements can be optimized later. */

        for (Map.Entry<Integer, Record> e : getRecords().records.entrySet()) {
            Record value = e.getValue();
            value.achievements.forEach(a -> {
                if (a.type.equals("GOLD")) {
                    bestAchievementsMap.put(a.name, a);
                }
            });
        }

        for (Map.Entry<Integer, Record> e : getRecords().records.entrySet()) {
            Record value = e.getValue();
            value.achievements.forEach(a -> {
                if (a.type.equals("SILVER")) {
                    /* Add the achievement if it does not have its superior counterpart (Gold) */
                    bestAchievementsMap.putIfAbsent(a.name, a);
                }
            });
        }

        for (Map.Entry<Integer, Record> e : getRecords().records.entrySet()) {
            Record record = e.getValue();
            record.achievements.forEach(a -> {
                if (a.type.equals("BRONZE")) {
                    /* Add the achievement if it does not have its superior counterpart (Silver) */
                    bestAchievementsMap.putIfAbsent(a.name, a);
                }
            });
        }

        List<BaseAchievementConfig> bestAchievements = new LinkedList<>();


        for (Map.Entry<String, BaseAchievementConfig> entry : bestAchievementsMap.entrySet()) {
            BaseAchievementConfig achievement = entry.getValue();
            bestAchievements.add(achievement);
        }

        return bestAchievements;
    }

    public static List<BaseAchievementConfig> getNextUnlockAchievements() {
        List<BaseAchievementConfig> betterAchievements = new LinkedList<>();

        Set<String> nextUnlocks = new LinkedHashSet<>();

        getBestRecords().forEach(achievement -> {
            nextUnlocks.add(achievement.name);
        });

        AchievementFactory.getAchievements().forEach(achievement -> {
            if (!nextUnlocks.contains(achievement.name) && achievement.type.equals("BRONZE")) {
                betterAchievements.add(achievement);
            }
        });
        return betterAchievements;
    }


    public static Records getRecords() {
        Records records = FileLoader.readClass(Records.class, path, EXTERNAL);
        return records != null ? records : new Records();
    }

    /**
     * Store the new records in JSON file
     *
     * @param records new records
     */
    public static void setRecords(Records records) {
        FileLoader.writeClass(records, path, EXTERNAL);
    }

    /**
     * A mapping of the game number (nth game played) and associated record,
     * i.e, the score and list of unlocked achievements.
     */
    public static class Records {
        /**
         * An ordered mapping of the game number and associated achievements
         */
        public Map<Integer, Record> records = new LinkedHashMap<>();

        /**
         * @param game the game number
         * @return records of a particular game (null if absent)
         */
        public Record findByGame(int game) {
            return records.get(game);
        }

        /**
         * Add a new record to the mapping
         *
         * @param record the record to be added
         */
        public void add(Record record) {
            records.put(record.game, record);
        }
    }

    /**
     * The Record class keeps a tab of the score, game number and
     * achievements unlocked in that particular game.
     * Games are identified based on the game numbers.
     */
    public static class Record {
        /**
         * The game number (nth game played)
         */
        public int game = 0;
        /**
         * A unique record id
         */
        public String id = UUID.randomUUID().toString();
        /**
         * List of unlocked achievements
         */
        public List<BaseAchievementConfig> achievements = new LinkedList<>();
        /**
         * Score details of that particular game
         */
        public Score scoreData = new Score();
    }

    public static class Score {
        /**
         * Score of that particular game
         **/
        public int score = 0;
        /**
         * The game number, for ease of access
         */
        public int game = 0;
        /**
         * Time when the game ended, i.e, the player died.
         */
        public String dateTime = LocalDateTime.now().toString();

        public LocalDateTime getDateTime(){
            return LocalDateTime.parse(this.dateTime);
        }

        public Integer getScore() {
            return score;
        }
    }
}
