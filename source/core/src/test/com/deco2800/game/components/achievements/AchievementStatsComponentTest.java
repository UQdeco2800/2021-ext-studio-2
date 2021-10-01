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

        // Generate condition config
        ConditionConfig condition = new ConditionConfig();
        condition.itemCount = itemCount;
        condition.time = time;
        condition.health = health;
        condition.score = score;
        condition.firstAids = firstAids;
        condition.gold = gold;
        condition.distance = distance;
        condition.spaceshipAvoidSuccess = spaceshipAvoidSuccess;

        achievement.condition = condition;
        return achievement;
    }

    /**
     * Tests if none achievements are un-lockable
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
            // By default, all conditions should be invalid unless simulated
            assertEquals(method.invoke(component, a), false);
        }
    }

    /**
     * Returns a list for non-item pickup related achievements
     *
     * @return BaseAchievementConfig list of achievements
     */
    private List<BaseAchievementConfig> getList() {
        // Generate an arbitrary list of achievements
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
     * Tests the isValid method's ability to check if achievement is unlocked or not
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

        // An arbitrary achievement, should by default, be invalid, i.e, locked.
        assertEquals(method.invoke(component, achievement), false);

        // Unlocked achievements should be valid by default
        achievement.unlocked = true;
        assertEquals(method.invoke(component, achievement), true);

    }

    /**
     * Gets the isValid method and sets it up to be accessible. This is to make
     * sure that the method can be invoked from outside the class.
     *
     * @return isValid method
     * @throws NoSuchMethodException thrown if corresponding method doesn't exist
     */
    private Method getIsValidMethod() throws NoSuchMethodException {
        Method method = AchievementsStatsComponent.class
                .getDeclaredMethod("isValid",
                        BaseAchievementConfig.class);
        // Make the private method of that class accessible so that it can be invoked
        method.setAccessible(true);
        return method;
    }

    /**
     * Tests if veteran achievements are un-lockable
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
            // Given enough time, check if the Veteran achievement is valid
            component.setTime(999999999);
            // Invalid health, so that GameBreaker achievements are not affected
            component.setHealth(-1);

            if (a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                // Other achievements should not be valid in this condition
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * Tests if Survival Expert achievements are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            // Given enough distance, the Survival expert achievement should be valid.
            component.setDistance(99999);

            if (a.name.equals("Survival Expert")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                // Other achievements should not be valid in this condition
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * Tests if Alphamineron achievement are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            component.setSpaceshipAvoidSuccess();
            // Only the Alphamineron achievement should be valid if
            // the spaceship has been avoided
            if (a.name.equals("Alphamineron")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                // Other achievements should be invalid in this condition
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * Tests if Tool Master achievements are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        // Check if the achievement is unlocked, as expected
        component.setItemCountByVal(1);
        // Generate said achievement accordingly
        BaseAchievementConfig achievement = genAchievement("Tool Master", -1, -1, 1, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        // Lock the achievement again
        achievement.unlocked = false;

        // Do the same task, but with different values
        component.setItemCountByVal(2);
        // Generate said achievement accordingly
        achievement = genAchievement("Tool Master", -1, -1, 2, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        // Unlock the achievement when 3 items have been picked up.
        component.setItemCountByVal(3);
        // Generate said achievement accordingly, should only pass when 3 items have been unlocked
        achievement = genAchievement("Tool Master", -1, -1, 3, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        // Lock the achievement again
        achievement.unlocked = false;
        // Expects 3 items to be picked up, and should be locked on negative values
        component.setItemCountByVal(-1);
        assertEquals(method.invoke(component, achievement), false);
        // Expects 1 items to be picked up, and should be locked when only 1 item are picked up
        component.setItemCountByVal(1);
        assertEquals(method.invoke(component, achievement), false);
        // Expects 3 items to be picked up, and should be locked when 2 items are picked up
        component.setItemCountByVal(2);
        assertEquals(method.invoke(component, achievement), false);
        // Expects 3 items to be picked up, and should be locked when 4 items have been picked up.
        // This is because strict equality is expected.
        component.setItemCountByVal(4);
        assertEquals(method.invoke(component, achievement), false);
        // Expects 3 items to be picked up, and should be locked when 99 items have been picked up.
        // This is because strict equality is expected.
        component.setItemCountByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * Tests if Stranger achievements are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        // Large time value
        component.setTime(999999999);
        // No items picked up mid game
        component.setItemCountByVal(0);
        // Expect the achievement to pass since enough time has passed since an item has been picked up
        BaseAchievementConfig achievement = genAchievement("Stranger", 10, -1, 0, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        // Lock achievement again
        achievement.unlocked = false;
        // Expect the achievement to pass since enough time has passed since an item has been picked up
        achievement = genAchievement("Stranger", 15, -1, 0, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        // Lock achievement again
        achievement.unlocked = false;
        // Expect the achievement to pass since enough time has passed since an item has been picked up
        achievement = genAchievement("Stranger", 20, -1, 0, -1, -1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        // Lock achievement again
        achievement.unlocked = false;

        // The achievement should fail if the item picked up count is invalid
        component.setItemCountByVal(-1);
        assertEquals(method.invoke(component, achievement), false);

        // The achievement should fail if items have been picked up
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
     * Tests if healer achievements are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        // Valid achievement if expected number of first aids have been picked up
        component.setFirstAidByVal(1);
        BaseAchievementConfig achievement = genAchievement("Healer", -1, -1, -1, -1, 1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        // Valid achievement if expected number of first aids have been picked up
        component.setFirstAidByVal(2);
        achievement = genAchievement("Healer", -1, -1, -1, -1, 2, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        // Valid achievement if expected number of first aids have been picked up
        component.setFirstAidByVal(3);
        achievement = genAchievement("Healer", -1, -1, -1, -1, 3, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        // Lock the achievement again
        achievement.unlocked = false;

        // The achievement will be unlocked on 3 achievements, but only 1 has been picked up.
        component.setFirstAidByVal(1);
        assertEquals(method.invoke(component, achievement), false);
        // The achievement will be unlocked on 3 achievements, but only 2 have been picked up.
        component.setFirstAidByVal(2);
        assertEquals(method.invoke(component, achievement), false);
        // The achievement will be unlocked on 3 achievements, but a larger number
        // has been picked up. Check for strict equality.
        component.setFirstAidByVal(4);
        assertEquals(method.invoke(component, achievement), false);
        // The achievement will be unlocked on 3 achievements, but a larger number
        // has been picked up. Check for strict equality.
        component.setFirstAidByVal(99);
        assertEquals(method.invoke(component, achievement), false);
        // Invalid cases
        component.setFirstAidByVal(-99);
        assertEquals(method.invoke(component, achievement), false);
        component.setFirstAidByVal(-1);
        assertEquals(method.invoke(component, achievement), false);
        component.setFirstAidByVal(-3);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * Tests if Game Breaker achievements are un-lockable
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
            // Simulate the case where the player has survived for a long time with full health
            component.setTime(999999999);
            component.setHealth(100);
            // Note: The Veteran achievement can also valid in this case
            if (a.name.equals("Game Breaker") || a.name.equals("Veteran")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                // Other achievements should be invalid in these conditions
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * Tests if Game Fanatic achievements are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        // Simulate full health
        component.setHealth(100);
        // Simulate first aids picked up mid game
        component.setFirstAidByVal(1);
        BaseAchievementConfig achievement = genAchievement("Game Fanatic", -1, 100, -1, -1, 1, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(2);
        achievement = genAchievement("Game Fanatic", -1, 100, -1, -1, 2, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        component.setFirstAidByVal(3);
        achievement = genAchievement("Game Fanatic", -1, 100, -1, -1, 3, -1, -1, false);
        assertEquals(method.invoke(component, achievement), true);
        achievement.unlocked = false;

        // The number of first aids picked up should be strictly equal to 3,
        // so all these cases should fail.
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

        // Invalid number of first aid count values
        component.setFirstAidByVal(-1);
        assertEquals(method.invoke(component, achievement), false);
        component.setFirstAidByVal(-3);
        assertEquals(method.invoke(component, achievement), false);
        component.setFirstAidByVal(-99);
        assertEquals(method.invoke(component, achievement), false);

        // Simulate 3 first aids picked up, which is a valid.
        component.setFirstAidByVal(3);
        // When the health is boosted, the achievement should still be valid
        component.setHealth(110);
        assertEquals(method.invoke(component, achievement), true);
        // Lock the achievement again
        achievement.unlocked = false;
        // The health value is high, but not full. The achievement should be invalid.
        component.setHealth(99);
        assertEquals(method.invoke(component, achievement), false);
        // Moderate health, achievement should not be unlocked
        component.setHealth(40);
        assertEquals(method.invoke(component, achievement), false);
        // Locked in low health conditions
        component.setHealth(0);
        assertEquals(method.invoke(component, achievement), false);
        component.setHealth(1);
        assertEquals(method.invoke(component, achievement), false);
        // Locked when the health value is invalid
        component.setHealth(-1);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * Tests if Collector achievements are un-lockable
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

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        // Simulate the expected gold value
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

        // Invalid number of gold coins picked up
        component.setGoldByVal(-1);
        assertEquals(method.invoke(component, achievement), false);
        component.setGoldByVal(-3);
        assertEquals(method.invoke(component, achievement), false);
        component.setGoldByVal(-99);
        assertEquals(method.invoke(component, achievement), false);

        // Check for strict equality, the achievement should only be valid
        // when 3 gold items have been picked up
        component.setGoldByVal(1);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(2);
        assertEquals(method.invoke(component, achievement), false);

        // Strict equality, larger values should fail as well.
        component.setGoldByVal(4);
        assertEquals(method.invoke(component, achievement), false);

        component.setGoldByVal(99);
        assertEquals(method.invoke(component, achievement), false);
    }

    /**
     * Tests if Master achievements are un-lockable
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
        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);
        for (BaseAchievementConfig a : getList()) {
            // Simulate a very large score
            component.setScore(999999999);

            if (a.name.equals("Master")) {
                assertEquals(method.invoke(component, a), true);
            } else {
                // Other achievements should fail in these conditions
                assertEquals(method.invoke(component, a), false);
            }
        }
    }

    /**
     * Tests if all achievements are un-lockable
     *
     * @throws NoSuchMethodException     thrown if corresponding method doesn't exist
     * @throws InvocationTargetException wraps exception thrown by constructor invoked
     * @throws IllegalAccessException    thrown if access is not allowed for a method
     */
    @Test
    void allAchievementsShouldBeValid() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = getIsValidMethod();

        // Get component from mocked entity
        AchievementsStatsComponent component = generateEntity()
                .getComponent(AchievementsStatsComponent.class);

        for (BaseAchievementConfig a : getList()) {
            // Unrealistically large values, so all achievements should pass.
            component.setTime(999999999);
            component.setHealth(100);
            component.setScore(99999);
            component.setDistance(99999);
            component.setSpaceshipAvoidSuccess();
            assertEquals(method.invoke(component, a), true);
        }
    }

}
