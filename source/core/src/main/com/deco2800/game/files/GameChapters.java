package com.deco2800.game.files;

import java.util.*;

public class GameChapters {

    public static List<Chapter> getChapters() {
        return FileLoader.readClass(Chapters.class, "configs/story.json").chapters;
    }

    public static List<GameChapters.Chapter> getUnlockedChapters() {

        List<GameChapters.Chapter> chapters = getChapters();

        Set<String> goldAchievements = new LinkedHashSet<>();

        /* Extracting gold achievements unlocked by the player */
        for (Map.Entry<Integer, AchievementRecords.Record> e :
                AchievementRecords.getRecords().records.entrySet()) {
            AchievementRecords.Record value = e.getValue();
            value.achievements.forEach(a -> {
                if (a.type.equals("GOLD")) {
                    goldAchievements.add(a.name);
                }
            });
        }

        /* The number of unlocked gold achievements equals the number of unlocked chapters */
        for (int i = 0; i < goldAchievements.size(); i++) {
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
