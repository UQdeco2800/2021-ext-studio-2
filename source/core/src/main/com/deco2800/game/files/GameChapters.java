package com.deco2800.game.files;

import java.util.LinkedList;
import java.util.List;

public class GameChapters {

    public static List<Chapter> getChapters(){
        return FileLoader.readClass(Chapters.class, "configs/story.json").chapters;
    }


    public static class Chapters {
        public List<Chapter> chapters = new LinkedList<>();
    }

    public static class Chapter {
        public int id = -1;
        public String content = "";
        public String achievement = "";
        public boolean unlocked = false;
    }
}
