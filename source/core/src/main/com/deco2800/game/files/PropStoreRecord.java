package com.deco2800.game.files;

import com.deco2800.game.entities.configs.propStore.PropItemConfig;
import com.deco2800.game.files.meta.GameInfo;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is used to record the goldCoin that will be used from the prop store
 */
public class PropStoreRecord {
    private static final String ROOT_DIR = "DECO2800Game";
    private static final String GOLD_FILE = "GOLD_RECORD.json";
    private static final String goldPath = ROOT_DIR + File.separator + GOLD_FILE;
    private static final String PROP_STORE_FILE = "PROP_STORE_RECORD.json";
    private static final String propStorePath = ROOT_DIR + File.separator + PROP_STORE_FILE;

    private static final FileLoader.Location location = FileLoader.Location.EXTERNAL;

    /**
     * The func is used to get the goldCoin got from the goldRecord.json file
     *
     * @return gold got
     */
    public static int getGold() {
        Gold goldRecord = FileLoader.readClass(Gold.class, goldPath, location);
        return goldRecord != null ? goldRecord.gold : new Gold().gold;
    }

    /**
     * The func is used to set the goldCoin amount
     *
     * @param gold goldCoin amount
     */
    public static void setGold(int gold) {
        Gold goldRecord = new Gold();
        goldRecord.gold = gold;
        FileLoader.writeClass(goldRecord, goldPath, location);
    }

    /**
     * The func is used to subtract goldCoin amount got
     *
     * @param amount goldCoin amount
     */
    public static void subtractGold(int amount) {

        int gold = getGold();
        if (gold >= amount) {
            gold = gold - amount;
            setGold(gold);
        }
    }

    /**
     * The func is used to add goldCoin amount got
     *
     * @param amount goldCoin amount
     */
    public static void addGold(int amount) {
        int gold = getGold();
        gold = gold + amount;
        setGold(gold);
    }

    /**
     * The func is used to check if there is enough goldCoin amount
     *
     * @param amount goldCoin amount
     * @return true, enough gold got
     * false, not enough
     */
    public static boolean hasEnoughGold(int amount) {
        int gold = getGold();
        return (gold >= amount);

    }

    /**
     * The func is used to buy some item
     *
     * @param item the item could be purchased
     */
    public static void buyItem(PropItemConfig item) {
        if (!hasEnoughGold(item.price)) {
            return;
        }
        subtractGold(item.price);
        BoughtProps bought = getBoughtProps();
        item.bought = true;
        item.used = false;
        bought.items.put(item.id, item);
        setBoughtProps(bought);

    }

    /**
     * The func is used to check if the item is bought
     *
     * @param item the item purchased
     * @return true, the item has been purchased
     * false, not be purchased
     */
    public static boolean isItemBought(PropItemConfig item) {
        if (getBoughtProps().containsItem(item.id)) {
            PropItemConfig storedItem = getBoughtProps().getBoughtItemById(item.id);
            return storedItem.bought;
        }
        return false;
    }

    /**
     * The func is used to remove the Item
     *
     * @param item the item purchased
     */
    public static void removeItem(PropItemConfig item) {
        BoughtProps record = getBoughtProps();
        record.removeItemById(item.id);
        setBoughtProps(record);
    }

    /**
     * The func is used to get the BoughtProps
     *
     * @return The BoughtProps
     */
    public static BoughtProps getBoughtProps() {
        BoughtProps record = FileLoader.readClass(BoughtProps.class, propStorePath, location);
        return record != null ? record : new BoughtProps();
    }

    /**
     * The func is used to set the BoughtProps
     *
     * @param boughtProps the BoughtProps
     */
    public static void setBoughtProps(BoughtProps boughtProps) {
        boughtProps.game = GameInfo.getGameCount();
        FileLoader.writeClass(boughtProps, propStorePath, location);
    }

    public static class Gold {
        int gold = 0;

    }

    public static class BoughtProps {
        public String id = UUID.randomUUID().toString();
        public Map<Integer, PropItemConfig> items = new LinkedHashMap();
        int game = -1;

        private PropItemConfig getBoughtItemById(int id) {
            return items.get(String.valueOf(id));
        }

        private PropItemConfig removeItemById(int id) {
            return items.remove(String.valueOf(id));
        }

        private boolean containsItem(int id) {
            return items.containsKey(String.valueOf(id));
        }
    }
}


