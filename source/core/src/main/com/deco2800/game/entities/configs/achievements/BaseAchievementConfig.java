package com.deco2800.game.entities.configs.achievements;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Defines a basic set of achievement properties stored in achievement
 * config file (achievements.json) to be loaded by Achievement Factory
 */
public class BaseAchievementConfig {
     public int bonus = 0;
     public String name = "";
     public String type = "";
     public String iconPath = "";
     public String message = "";
     public boolean unlocked = false;
     public ConditionConfig condition = new ConditionConfig();

     public Map<String, Integer> getConditionMap() {
          Map<String, Integer> conditionProps = new LinkedHashMap<>();
          // Get all the member fields of the condition object
          Field[] fields = ConditionConfig.class.getFields();

          for (Field field : fields) {
               String name = field.getName();
               // Get value of the fields
               int value = -1;
               try {
                    value = field.getInt(condition);
               } catch (IllegalAccessException e) {
                    e.printStackTrace();
               }

               conditionProps.put(name, value);
          }
          return conditionProps;
     }
}
