package com.deco2800.game.files.stats;

import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.files.meta.GameInfo;
import com.deco2800.game.services.ServiceLocator;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.deco2800.game.files.FileLoader.Location.EXTERNAL;

public class GameRecords {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String RECORDS_FILE = "gameRecords.json";
    private static final String PATH = ROOT_DIR + File.separator + RECORDS_FILE;

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

        // Storing score, distance and game count. The latter is for convenient access.
        record.scoreData.score = ServiceLocator.getScoreService().getScore();
        record.scoreData.distance = ServiceLocator.getDistanceService().getDistance();
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

    public static double getDistanceByGame(int game) {
        return getScoreByGame(game).distance;
    }


    /**
     * Returns the list of scores
     *
     * @return score of that particular game
     */
    public static List<Score> getAllScores() {
        return getRecords().records
                .values().stream()
                .map(record -> record.scoreData)
                .collect(Collectors.toList());
    }


    public static Records getRecords() {
        Records records = FileLoader.readClass(Records.class, PATH, EXTERNAL);
        return records != null ? records : new Records();
    }

    /**
     * Store the new records in JSON file
     *
     * @param records new records
     */
    public static void setRecords(Records records) {
        FileLoader.writeClass(records, PATH, EXTERNAL);
    }


    public static Score getLatestScore() {
        return getScoreByGame(GameInfo.getGameCount());
    }

    public static double getLatestDistance() {
        return getScoreByGame(GameInfo.getGameCount()).distance;
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
            return records.get(String.valueOf(game));
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
         * Distance of that particular game
         **/
        public double distance = 0;
        /**
         * The game number, for ease of access
         */
        public int game = 0;
        /**
         * Time when the game ended, i.e, the player died.
         */
        public String dateTime = LocalDateTime.now().toString();

        /**
         * Returns local date and time object of when the game ended, i.e,
         * the time of player's death.
         * <p>
         * LocalDateTime objects are easier to work with. Parse the
         * given dateTime string into a LocalDateTime object.
         * <p>
         * Note: This could be mapped to the JSON but the FileLoader
         * gives SEVERE type errors when reading the file.
         *
         * @return LocalDateTime object of the dateTime property
         */
        public LocalDateTime getDateTime() {
            return LocalDateTime.parse(this.dateTime);
        }

        /**
         * In game score
         *
         * @return the score of the particular game
         */
        public Integer getScore() {
            return score;
        }
    }
}
