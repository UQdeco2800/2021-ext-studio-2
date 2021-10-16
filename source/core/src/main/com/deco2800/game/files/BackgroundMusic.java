package com.deco2800.game.files;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class BackgroundMusic {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String GAME_INFO_FILE = "backgroundMusic.json";
    private static final String PATH = ROOT_DIR + File.separator + GAME_INFO_FILE;
    private static final FileLoader.Location location = FileLoader.Location.EXTERNAL;
    /**
     * Fetch the mapping of screen name and associated background music paths
     * @return music mapping of screen name and music paths
     */
    private static Map<String, String[]> getAllMusic(){
        MusicList musicList =
                FileLoader.readClass(MusicList.class, "configs/backgroundMusic.json");
        if(musicList == null){
            musicList = new MusicList();
        }
        return musicList.music;
    }

    /**
     * Fetch all music paths for a particular screen
     * @param screenName name of screen
     * @return music paths array, empty if no associated music
     */
    public static String[] getAllMusicByScreen(String screenName) {
        return getAllMusic().getOrDefault(screenName, new String[]{});
    }

    /**
     * Checks if a particular screen has associated background music
     * @param screenName name of the screen
     * @return true if the screen has some background music, false otherwise
     */
    public static boolean containsScreenMusic(String screenName){
        return getAllMusicByScreen(screenName).length != 0;
    }


    /**
     * Selects a new background music and persists the choice
     * @param screenName name of the screen
     * @param trackPath path of the track
     */
    public static void selectMusic(String screenName, String trackPath){
        Map<String, String> chosenMusic = getAllChosenMusic();
        chosenMusic.put(screenName, trackPath);
        setChosenMusic(chosenMusic);
    }

    /**
     * Get selected music track for the supplied screen name
     * @param screenName name of screen
     * @return trackPath path of the chosen track, defaults to the first track
     */
    public static String getSelectedMusic(String screenName){
        String defaultTrack = "";
        if(containsScreenMusic(screenName)){
            defaultTrack = getAllMusicByScreen(screenName)[0];
        }
        return getAllChosenMusic().getOrDefault(screenName, defaultTrack);
    }

    /**
     * Overwrite the existing file with the new mappings
     * @param musicMap new mapping of screen name and chosen tracks
     */
    private static void setChosenMusic(Map<String, String> musicMap) {
        SelectedMusicList music = new SelectedMusicList();
        music.selectedMusic = musicMap;
        FileLoader.writeClass(music, PATH, location);
    }

    /**
     * Fetch all the chosen music
     * @return allSelectedMusic mapping of screen name and chosen track path
     */
    private static Map<String, String> getAllChosenMusic(){
        SelectedMusicList music = FileLoader.readClass(SelectedMusicList.class, PATH, location);
        if(music == null){
            music = new SelectedMusicList();
        }
        return music.selectedMusic;
    }

    public static class SelectedMusicList {
        Map<String, String> selectedMusic = new LinkedHashMap<>();
    }

    public static class MusicList {
        /** Mapping of screen name and track paths */
        Map<String, String[]> music = new LinkedHashMap<>();
    }
}
