package com.deco2800.game.files.stats;

import com.deco2800.game.files.FileLoader;

import java.util.LinkedList;
import java.util.List;


public class GameChapters {

    public static List<Chapter> getChapters() {
        return FileLoader.readClass(Chapters.class, "configs/story.json").chapters;
    }

    /**
     * Returns the number of chapters
     *
     * @return unlocked chapters
     */
    public static List<GameChapters.Chapter> getUnlockedChapters() {

        List<GameChapters.Chapter> chapters = getChapters();


        /* Extracting gold achievements unlocked by the player */
        int goldAchievements = GameRecordUtils.getGoldAchievementsCount();

        /* The number of unlocked gold achievements equals the number of unlocked chapters */
        for (int i = 0; i < goldAchievements; i++) {
            if (i < chapters.size()) {
                chapters.get(i).unlocked = true;
            }
        }

        return chapters;
    }

    public static class Chapters {
        public List<Chapter> chapters = new LinkedList<>();
    }

    public static class Chapter {
        public int id = -1;
        public String content = "";
        public boolean unlocked = false;
    }
}
