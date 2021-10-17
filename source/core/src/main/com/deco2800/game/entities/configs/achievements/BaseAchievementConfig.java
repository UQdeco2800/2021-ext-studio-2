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

     /**
      * Returns a mapping of the achievement name and value for easy processing
      * This makes it very easy to iterate through all the properties and fetch
      * the values, and saves a lot of technical debt. In simple words, a mapping
      * of member variables and values of the 'ConditionConfig' object is
      * returned by this function.
      *
      * @return conditionProps mapping of condition name and associated value
      */
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
