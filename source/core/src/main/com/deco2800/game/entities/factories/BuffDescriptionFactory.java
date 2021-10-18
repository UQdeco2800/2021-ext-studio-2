package com.deco2800.game.entities.factories;

import com.deco2800.game.components.buff.BuffDescriptionDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.BuffDescriptionConfigs;
import com.deco2800.game.files.FileLoader;


public class BuffDescriptionFactory {
    private static final BuffDescriptionConfigs configs =
            FileLoader.readClass(BuffDescriptionConfigs.class, "configs/buffs.json");
    /**
     * Returns a list of buff from the "buffDescription.json" file
     */
    public static Entity createBuffDescriptionEntity() {
        return new Entity()
                .addComponent(new BuffDescriptionDisplay());
    }

}
