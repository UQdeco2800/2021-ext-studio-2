package com.deco2800.game.components.items;

import com.deco2800.game.entities.factories.PropStoreFactory;

public class PropStoreDisplay {

    public PropStoreDisplay(){
        PropStoreFactory.getPropStoreItems().forEach(item -> System.out.println(item.name));
    }
}
