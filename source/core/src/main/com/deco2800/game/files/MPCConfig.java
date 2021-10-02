package com.deco2800.game.files;

import com.deco2800.game.components.achievements.AchievementsStatsComponent;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.services.ServiceLocator;

import java.io.File;

import static com.deco2800.game.files.FileLoader.Location.EXTERNAL;

public class MPCConfig {

    private static final String ROOT_DIR = "DECO2800Game";
    private static final String CONFIG_FILE = "mpc.json";
    private static final String path = ROOT_DIR + File.separator + CONFIG_FILE;

    /**
     * Stores the current values into a JSON file
     */
    public void readValues() {
        PlayerConfig values = getValues();
    }

    

    public static PlayerConfig getValues() {
        PlayerConfig values =  FileLoader.readClass(PlayerConfig.class, path, EXTERNAL);
        return values != null ? values : new PlayerConfig();

    }
}