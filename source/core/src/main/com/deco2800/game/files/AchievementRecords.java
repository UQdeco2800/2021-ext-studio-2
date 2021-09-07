package com.deco2800.game.files;

import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;

import java.time.LocalDateTime;
import java.util.*;

public class AchievementRecords {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String GAME_INFO_FILE = "gameInfo.json";

    public static void storeRecord(){

    }

    public static void getAchievements() {

    }

    public static void getBestRecords(){

    }

    /**
     * A mapping of the game number (nth game played) and associated record,
     * i.e, the score and list of unlocked achievements.
     */
    public class Records {
        /**
         * A mapping of the game number and associated achievements
         */
        public HashMap<Integer, Record> records = new LinkedHashMap<>();
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
        /** Date of game played */
        public LocalDateTime dateTime = LocalDateTime.now();
    }
}
