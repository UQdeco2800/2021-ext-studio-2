package com.deco2800.game.files;

import java.io.File;

/**
 * The class is responsible for keeping a tab of the game metadata
 */
public class GameInfo {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String GAME_INFO_FILE = "gameInfo.json";
    private static final String path = ROOT_DIR + File.separator + GAME_INFO_FILE;
    private static final FileLoader.Location location = FileLoader.Location.EXTERNAL;

    /**
     * Increment the total number of games that have been
     * played by the user. Persist this in local storage.
     * Ideally, this should be incremented as soon as the
     * main character dies.
     */
    public static void incrementGameCount(){
        GameMetadata info = getGameMetadata();
        info.game += 1;
        setGameMetadata(info);
    }

    /**
     * Returns the number of games that the user has played from local storage
     * By default, 0 games have been played.
     * @return the number of games
     */
    public static int getGameCount(){
        return getGameMetadata().game;
    }

    /**
     * Persist the game metadata
     * @param info the game metadata to persist in local storage
     */
    public static void setGameMetadata(GameMetadata info){
        FileLoader.writeClass(info, path, location);
    }

    /**
     * Reads the game metadata from local JSON file
     * @return latest metadata stored in local storage
     */
    public static GameMetadata getGameMetadata(){
        GameMetadata info = FileLoader.readClass(GameMetadata.class, path, location);
        return info != null ? info : new GameMetadata();
    }

    /**
     * Represents the game metadata
     */
    public static class GameMetadata {
        /** Number of games that have been played by the user */
        public int game = 0;
    }
}
