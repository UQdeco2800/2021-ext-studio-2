package com.deco2800.game.entities.factories;

import com.deco2800.game.entities.configs.PropStoreConfig;
import com.deco2800.game.entities.configs.propStore.PropItemConfig;
import com.deco2800.game.files.FileLoader;

import java.util.List;
import java.util.stream.Collectors;

public class PropStoreFactory {
    public static List<PropItemConfig> getPropStoreItems() {
        return FileLoader.readClass(PropStoreConfig.class, "configs/propStore.json").props;
    }

    public static String[] getPropTextures() {
        return getPropStoreItems().stream()
                .map(item -> item.path).collect(Collectors.toList())
                .toArray(new String[getPropStoreItems().size()]);
    }
}
