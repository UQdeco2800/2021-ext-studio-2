package com.deco2800.game.files;

import com.deco2800.game.entities.configs.propStore.PropItemConfig;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PropStoreRecord {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String GOLD_FILE = "GOLD_RECORD.json";
    private static final String goldPath = ROOT_DIR + File.separator + GOLD_FILE;
    private static final String PROP_STORE_FILE = "PROP_STORE_RECORD.json";
    private static final String propStorePath = ROOT_DIR + File.separator + PROP_STORE_FILE;

    private static final FileLoader.Location location = FileLoader.Location.EXTERNAL;

    public static int getGold() {
        Gold goldRecord = FileLoader.readClass(Gold.class, goldPath, location);
        return goldRecord != null ? goldRecord.gold : new Gold().gold;
    }

    public static void setGold(int gold) {
        Gold goldRecord = new Gold();
        goldRecord.gold = gold;
        FileLoader.writeClass(goldRecord, goldPath, location);
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
    public static void buyItem(PropItemConfig item){
        if(!hasEnoughGold(item.price)){
            return;
        }
        subtractGold(item.price);
        BoughtProps bought = getBoughtProps();
        item.bought = true;
        item.used = false;
        bought.items.put(item.id,item);
        setBoughtProps(bought);

    }
    public static boolean isItemBought(PropItemConfig item){
        if(getBoughtProps().containsItem(item.id)){
            PropItemConfig storedItem = getBoughtProps().getBoughtItemById(item.id);
            return storedItem.bought;
        }
        return false;
    }

    public static BoughtProps getBoughtProps(){
        BoughtProps record = FileLoader.readClass(BoughtProps.class, propStorePath, location);
        return record != null ? record : new BoughtProps();
    }
    public static void setBoughtProps(BoughtProps boughtProps){
        boughtProps.game = GameInfo.getGameCount();
        FileLoader.writeClass(boughtProps, propStorePath, location);
    }
    public static class BoughtProps{
        int game = -1;
        public String id = UUID.randomUUID().toString();
        public Map<Integer,PropItemConfig> items = new LinkedHashMap();
        private PropItemConfig getBoughtItemById(int id){
         return items.get(String.valueOf(id));
        }
        private boolean containsItem(int id){
            return items.containsKey(String.valueOf(id));
        }
    }
}


