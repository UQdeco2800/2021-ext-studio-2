package com.deco2800.game.files;

import java.io.File;

public class PropStoreRecord {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String PROP_STORE_FILE = "propStoreRecord.json";
    private static final String path = ROOT_DIR + File.separator + PROP_STORE_FILE;
    private static final FileLoader.Location location = FileLoader.Location.EXTERNAL;

    public static int getGold() {
        Gold goldRecord = FileLoader.readClass(Gold.class, path, location);
        return goldRecord != null ? goldRecord.gold : new Gold().gold;
    }

    public static void setGold(int gold) {
        Gold goldRecord = new Gold();
        goldRecord.gold = gold;
        FileLoader.writeClass(goldRecord, path, location);
    }
    public static void subtractGold(int amount){

        int gold = getGold();
        if(gold >= amount) {
            gold = gold - amount;
            setGold(gold);
        }
    }

    public static void addGold(int amount){
        int gold = getGold();
        gold = gold + amount;
        setGold(gold);
    }

    public static boolean hasEnoughGold(int amount){
        int gold = getGold();
        return (gold >= amount);

    }

    public static class Gold {
        int gold = 0;

    }

}

