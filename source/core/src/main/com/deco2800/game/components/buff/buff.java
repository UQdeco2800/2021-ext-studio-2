package com.deco2800.game.components.buff;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;

public class buff extends CombatStatsComponent {
    public buff(int health, int baseAttack){
        super(health, baseAttack);
    }
    public void addHealth(){
        addHealth(10);

    }

    public void increaseHealthLimit(){
        setHealthMax(getHealthMax()+ 20);
    }

}
