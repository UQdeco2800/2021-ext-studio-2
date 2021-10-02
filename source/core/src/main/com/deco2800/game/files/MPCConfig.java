package com.deco2800.game.files;

import com.deco2800.game.entities.configs.PlayerConfig;

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

    public static void updateAttire(String attireType) {
        PlayerConfig values = getValues();
        values.attire = attireType;

        updateValues(values);
    }
    public static void updateValues(PlayerConfig values) {

        FileLoader.writeClass(values, path, EXTERNAL);
    }
    public static void setDefaultAttire() {
        PlayerConfig values = getValues();
        values.attire = "OG";
        updateValues(values);
    }
    public static PlayerConfig getValues() {
        PlayerConfig values =  FileLoader.readClass(PlayerConfig.class, path, EXTERNAL);
        return values != null ? values : new PlayerConfig();

    }
}