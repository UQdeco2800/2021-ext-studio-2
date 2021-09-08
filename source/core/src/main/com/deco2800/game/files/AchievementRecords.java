package com.deco2800.game.files;

import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;

import java.io.File;
import java.util.*;

import static com.deco2800.game.files.FileLoader.Location.EXTERNAL;

public class AchievementRecords {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String RECORDS_FILE = "achievements.json";
    private static final String path = ROOT_DIR + File.separator + RECORDS_FILE;

    /**
     * Stores the records of the most recent game played into a JSON file
     * Note: Run this method only after the in game count has updated
     */
    public static void storeGameRecord(){
        Record record = new Record();

        record.game = GameInfo.getGameCount();
        record.achievements = AchievementsStatsComponent.getUnlockedAchievements();

        ScoringSystemV1 scoringSystemV1 = new ScoringSystemV1();
        record.score = scoringSystemV1.getScore();

        Records records = getRecords();
        records.add(record);

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
     * Store the new records in JSON file
     * @param records new records
     */
    public static void setRecords(Records records){
        FileLoader.writeClass(records, path, EXTERNAL);
    }


    public static Records getRecords(){
        Records records = FileLoader.readClass(Records.class, path, EXTERNAL);
        return records != null ? records : new Records();
    }

    /**
     * A mapping of the game number (nth game played) and associated record,
     * i.e, the score and list of unlocked achievements.
     */
    public static class Records {
        /**
         * An ordered mapping of the game number and associated achievements
         */
        public HashMap<Integer, Record> records = new LinkedHashMap<>();

        /**
         * @param game the game number
         * @return records of a particular game (null if absent)
         */
        public Record findByGame(int game){
            return records.get(game);
        }

        /**
         * Add a new record to the mapping
         * @param record the record to be added
         */
        public void add(Record record){
            records.put(record.game, record);
        }
    }

    /**
     * The Record class keeps a tab of the score, game number and
     * achievements unlocked in that particular game.
     * Games are identified based on the game numbers.
     */
    public static class Record {
        /** The game number (nth game played) */
        public int game = 0;
        /** A unique record id */
        public String id = UUID.randomUUID().toString();
        /** List of unlocked achievements */
        public List<BaseAchievementConfig> achievements = new LinkedList<>();
        /** Score of that particular game */
        public int score = 0;
    }
}
