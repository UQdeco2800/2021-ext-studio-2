package com.deco2800.game.entities.configs.buff;

import com.deco2800.game.entities.configs.achievements.ConditionConfig;

/**
 * Defines a basic set of achievement properties stored in achievement
 * config file (achievements.json) to be loaded by Achievement Factory
 */

public class BuffDescriptionConfig {
     public String name = "";
     public String description = "";

     public BuffDescriptionConfig(String name, String description) {
          this.name = name;
          this.description = description;
     }

     public BuffDescriptionConfig() {
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }
}