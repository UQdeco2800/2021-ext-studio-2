package com.deco2800.game.components.achievements;


import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.configs.achievements.ConditionConfig;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
public class AchievementStatsComponentTest {
    /**
     * Method used to emulate achievement configs values (conditions) read from the achievements.json
     *
     * @param name                  name of the achievement
     * @param time                  time required to be spent in the game
     * @param health                health required of the mpc
     * @param itemCount             total items required to be picked up in game
     * @param score                 score required during gameplay
     * @param firstAids             first aids required to be in inventory
     * @param gold                  gold coins required to be picked up in game
     * @param distance              distance required to be covered by the mpc in game
     * @param spaceshipAvoidSuccess successful avoidance of the spaceship in game
     * @return BaseAchievementConfig with values specified
     */
    private BaseAchievementConfig genAchievement(String name, int time, int health, int itemCount, int score, int firstAids, int gold, int distance, boolean spaceshipAvoidSuccess) {
        BaseAchievementConfig achievement = new BaseAchievementConfig();
        achievement.bonus = 0;
        achievement.name = name;
        achievement.type = "ignored";
        achievement.iconPath = "ignored";
        achievement.message = "ignored";
        achievement.unlocked = false;

        ConditionConfig condition = new ConditionConfig();
        condition.itemCount = itemCount;
        condition.time = time;
        condition.health = health;
        condition.score = score;
        condition.firstAids = firstAids;
        achievement.condition = condition;
        condition.gold = gold;
        condition.distance = distance;
        condition.spaceshipAvoidSuccess = spaceshipAvoidSuccess;

        return achievement;
    }

    /**
     * returns a list for non-item pickup related achievements
     *
     * @return BaseAchievementConfig list of achievements
     */
    private List<BaseAchievementConfig> getList() {
        List<BaseAchievementConfig> achievements = new ArrayList<>();
        achievements.add(genAchievement("Veteran", 10, -1, -1, -1, -1, -1, -1, false));
        achievements.add(genAchievement("Veteran", 15, -1, -1, -1, -1, -1, -1, false));
        achievements.add(genAchievement("Veteran", 20, -1, -1, -1, -1, -1, -1, false));
        achievements.add(genAchievement("Game Breaker", 5, 100, -1, -1, -1, -1, -1, false));
        achievements.add(genAchievement("Game Breaker", 8, 100, -1, -1, -1, -1, -1, false));
        achievements.add(genAchievement("Game Breaker", 10, 100, -1, -1, -1, -1, -1, false));
        achievements.add(genAchievement("Master", -1, -1, -1, 50, -1, -1, -1, false));
        achievements.add(genAchievement("Master", -1, -1, -1, 100, -1, -1, -1, false));
        achievements.add(genAchievement("Master", -1, -1, -1, 150, -1, -1, -1, false));
        achievements.add(genAchievement("Survival Expert", -1, -1, -1, -1, -1, -1, 20, false));
        achievements.add(genAchievement("Survival Expert", -1, -1, -1, -1, -1, -1, 40, false));
        achievements.add(genAchievement("Survival Expert", -1, -1, -1, -1, -1, -1, 60, false));
        achievements.add(genAchievement("Alphamineron", -1, -1, -1, -1, -1, -1, -1, true));

        return achievements;
    }

    private Entity generateEntity() {
        Entity achievementsEntity =
                new Entity().addComponent(new AchievementsStatsComponent());
        achievementsEntity.create();
        return achievementsEntity;
    }

    /**
     * tests the isValid method's ability to check if achievement is unlocked or not
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCheckIsValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        BaseAchievementConfig achievement = genAchievement("ignored", 50, -1, 0, -1, -1, -1, -1, false);

        AchievementsStatsComponent component = new AchievementsStatsComponent();

        Method method = getIsValidMethod();

        assertEquals(method.invoke(component, achievement), false);

        achievement.unlocked = true;
        assertEquals(method.invoke(component, achievement), true);

    }

    /**
     * gets the isValid method and sets it up to be accessible
     *
     * @return isValid method
     * @throws NoSuchMethodException thrown if corresponding method doesn't exist
     */
    private Method getIsValidMethod() throws NoSuchMethodException {
        Method method = AchievementsStatsComponent.class
                .getDeclaredMethod("isValid",
                        BaseAchievementConfig.class);
        method.setAccessible(true);
        return method;
    }

    /**
     * tests if veteran achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateVeteranAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(999999999);
            component.setHealth(-1);

            if (a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * tests if Survival Expert achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateSurvivalExpertAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setDistance(99999);

            if (a.name.equals("Survival Expert")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * tests if Alphamineron achievement are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateAlphamineronAchievement() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setSpaceshipAvoidSuccess();

            if (a.name.equals("Alphamineron")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * tests if Tool Master achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateToolMasterAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(0);
        component.setHealth(0);

        component.setItemCountByVal(1);
        BaseAchievementConfig achievement = genAchievement("Tool Master", -1, -1, 1, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(2);
        achievement = genAchievement("Tool Master", -1, -1, 2, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(3);
        achievement = genAchievement("Tool Master", -1, -1, 3, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(-1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * tests if Stranger achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateStrangerAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(999999999);
        component.setItemCountByVal(0);
        BaseAchievementConfig achievement = genAchievement("Stranger", 10, -1, 0, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;
        component.setTime(999999999);
        component.setItemCountByVal(0);
        achievement = genAchievement("Stranger", 15, -1, 0, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;
        component.setTime(999999999);
        component.setItemCountByVal(0);
        achievement = genAchievement("Stranger", 20, -1, 0, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setItemCountByVal(-1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setItemCountByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * tests if healer achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateHealerAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(0);
        component.setHealth(0);
        component.setFirstAidByVal(1);
        BaseAchievementConfig achievement = genAchievement("Healer", -1, -1, -1, -1, 1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(2);
        achievement = genAchievement("Healer", -1, -1, -1, -1, 2, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(3);
        achievement = genAchievement("Healer", -1, -1, -1, -1, 3, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * tests if Game Breaker achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateGameBreakerAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(999999999);
            component.setHealth(100);

            if (a.name.equals("Game Breaker") || a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * tests if Game Fanatic achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateGameFanaticAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(0);
        component.setHealth(100);
        component.setFirstAidByVal(1);
        BaseAchievementConfig achievement = genAchievement("Game Fanatic", -1, -1, -1, -1, 1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(2);
        achievement = genAchievement("Game Fanatic", -1, -1, -1, -1, 2, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(3);
        achievement = genAchievement("Game Fanatic", -1, -1, -1, -1, 3, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setFirstAidByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * tests if Collector achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateCollectorAchievements() throws
            IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        Method method = getIsValidMethod();


        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        component.setTime(0);
        component.setHealth(0);
        component.setGoldByVal(1);
        BaseAchievementConfig achievement = genAchievement("Collector", -1, -1, -1, -1, -1, 1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setGoldByVal(2);
        achievement = genAchievement("Collector", -1, -1, -1, -1, -1, 2, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setGoldByVal(3);
        achievement = genAchievement("Collector", -1, -1, -1, -1, -1, 3, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setGoldByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * tests if Master achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void shouldCorrectlyValidateMasterAchievements() throws
            NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);
        for (BaseAchievementConfig a : getList()) {
            component.setScore(999999999);

            if (a.name.equals("Master")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * tests if all achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void allAchievementsShouldBeValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(999999999);
            component.setHealth(100);
            component.setScore(99999);
            component.setDistance(99999);
            component.setSpaceshipAvoidSuccess();
            assertEquals(method.invoke(component, a), true);
        }
    }

    /**
     * tests if none achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void noAchievementShouldBeValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        AchievementsStatsComponent component =
                generateEntity().getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setTime(2);
            component.setHealth(0);
            assertEquals(method.invoke(component, a), false);
        }
    }


}
