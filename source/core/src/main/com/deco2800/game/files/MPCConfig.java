package com.deco2800.game.files;

import com.deco2800.game.entities.configs.PlayerConfig;

import java.io.File;

import static com.deco2800.game.files.FileLoader.Location.EXTERNAL;

/**
 * Class to record the MPC attire configuration into a JSON file that is locally persisted
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */

public class MPCConfig {

    private static final String ROOT_DIR = "DECO2800Game";
    private static final String CONFIG_FILE = "mpc.json";
    private static final String path = ROOT_DIR + File.separator + CONFIG_FILE;

    /**
     * Store the current values into a JSON file
     */
    public void readValues() {
        PlayerConfig values = getValues();
    }


    /**
     * Retrieve the current attire value and update PlayerConfig
     */

    public static void updateAttire(String attireType) {
        PlayerConfig values = getValues();
        values.attire = attireType;

        updateValues(values);
    }

    /**
     * Write the current attire value into the JSON file
     */

    public static void updateValues(PlayerConfig values) {

        FileLoader.writeClass(values, path, EXTERNAL);
    }

    /**
     * Update the current attire value to OG in the JSON file when no attire has been selected
     */

    public static void updateValues() {
        PlayerConfig values = getValues();
        if(values.attire == null){
            values.attire = "OG";
        }
        updateValues(values);

    }

    /**
     * Retrieve PlayerConfig values if they exist, else create a new PlayerConfig
     * @return PlayerConfig
     */

    public static PlayerConfig getValues() {
        PlayerConfig values =  FileLoader.readClass(PlayerConfig.class, path, EXTERNAL);
        return values != null ? values : new PlayerConfig();

    }
}