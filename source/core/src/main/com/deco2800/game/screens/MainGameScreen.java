package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.ItemBar.ItemBarDisplay;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.RecycleDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.maingame.MainGameExitDisplay;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.components.score.DistanceDisplay;
import com.deco2800.game.components.score.ScoreDisplay;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.components.score.TimerDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.meta.GameInfo;
import com.deco2800.game.files.stats.GameRecords;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.*;
import com.deco2800.game.ui.terminal.Terminal;
import com.deco2800.game.ui.terminal.TerminalDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
    private long lastSpawnTime = 0;
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] mainGameTextures = {"images/heart.png", "images/clockV2.png",
            "images/scoreboardV2.png", "images/background.png", "images/water1.png", "images/food1.png",
            "images/Sprint2_Buffs_Debuffs/Poisoning.png",
            "images/Sprint2_Buffs_Debuffs/decrease_health.png",
            "images/Sprint2_Buffs_Debuffs/increase_health_limit.png",
            "images" +
                    "/Sprint2_Buffs_Debuffs/increase_health.png",
            "images/Sprint2_Buffs_Debuffs/decrease_speed.png", "images/distanceboard.png",
            "images/itembar/item/itembar-blood.png",
            "images/itembar/item/itembar-water.png",
            "images/itembar/item/itembar-leg.png",
            "images/itembar/recycle-256px-bb1.png",
            "images/itembar/recycle-256px-bb2.png",
            "images/itembar/recycle-256px-bb3.png",
            "images/itembar/recycle-default.png",
            "images/itembar/recycle-256px-leg1.png",
            "images/itembar/recycle-256px-leg2.png",
            "images/itembar/recycle-256px-leg3.png",
            "images/itembar/recycle-256px-water1.png",
            "images/itembar/recycle-256px-water2.png",
            "images/itembar/recycle-256px-water3.png",
            "images/pao.png",
            "buff-debuff-manual/low_statu_hunger1.png",
            "buff-debuff-manual/low_statu_thirst1.png",
            "images/itembar/item-rework-s4/itembar-blood-s4.png",
            "images/itembar/item-rework-s4/itembar-water-s4.png",
            "images/itembar/item-rework-s4/itembar-leg-s4.png",
            "images/itembar/item-rework-s4/itembar-rocket-s4.png",
            "buff-debuff-manual/low_statu_hunger1.png",
            "buff-debuff-manual/low_statu_thirst1.png",
    };
    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);
    /* new map status, to control if the player into/out the new map*/
    public static NewMap newMapStatus;
    public static Entity players;
    private static boolean slowPlayer;
    private static float slowPlayerTime;
    private final GdxGame game;
    private final Renderer renderer;
    private final PhysicsEngine physicsEngine;
    private final Entity player;
    private final ForestGameArea forestGameArea;
    private int counter = 0;

    public MainGameScreen(GdxGame game) {
        this.game = game;
        logger.debug("Initialising main game screen services");
        ServiceLocator.registerTimeSource(new GameTime());
        ServiceLocator.registerScoreService(new ScoreService());


        PhysicsService physicsService = new PhysicsService();
        ServiceLocator.registerPhysicsService(physicsService);
        physicsEngine = physicsService.getPhysics();

        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());

        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
        renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

        loadAssets();
        createUI();

        logger.debug("Initialising main game screen entities");
        TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
        forestGameArea = new ForestGameArea(terrainFactory);
        forestGameArea.create();

        player = forestGameArea.player;
        players = player;

        newMapStatus = NewMap.OFF;
        slowPlayer = false;
        counter = 0;

        ServiceLocator.registerDistanceService(new DistanceService(player));

        resetSpaceshipAttackVariable();

    }

    /**
     * getter method of slowPlayer
     *
     * @return if the player is slow status
     */
    public static boolean isSlowPlayer() {
        return slowPlayer;
    }

    /**
     * setter method of slowPlayer
     *
     * @param slowPlayer if the player is on a slow status
     */
    public static void setSlowPlayer(boolean slowPlayer) {
        MainGameScreen.slowPlayer = slowPlayer;
    }

    /**
     * Set the player deceleration time, the value set in the function is used by slowPlayer(), and the function is
     * implemented in render(). This function called by thornsDisappear() in ObstacleDisappear.java
     *
     * @param slowPlayerTime How many seconds the player slows down.
     */
    public static void setSlowPlayer(float slowPlayerTime) {
        if (!slowPlayer) { // if the player already slow, don't set double times
            slowPlayer = true;
            MainGameScreen.slowPlayerTime = slowPlayerTime;
        } else { // double set
            MainGameScreen.slowPlayerTime += slowPlayerTime;
        }
        logger.debug("Set slowPlayer = {}, Total slowPlayerTime = {}", slowPlayer, slowPlayerTime);
    }

    /**
     * getter method of slowPlayerTime
     *
     * @return time remain of player slow time
     */
    public static float getSlowPlayerTime() {
        return slowPlayerTime;
    }

    /**
     * setter method of slowPlayerTime
     *
     * @param slowPlayerTime how may time the player will on slow status
     */
    public static void setSlowPlayerTime(float slowPlayerTime) {
        MainGameScreen.slowPlayerTime = slowPlayerTime;
    }

    /**
     * getter method of newMapStatus
     *
     * @return the status of mew map
     */
    public static NewMap getNewMapStatus() {
        return newMapStatus;
    }

    /**
     * setter method of newMapStatus
     *
     * @param status the status of mew map
     */
    public static void setNewMapStatus(NewMap status) {
        newMapStatus = status;
    }

    private void resetSpaceshipAttackVariable() {
        // reset Spaceship Attack variables
        SpaceshipAttackController.spaceshipState = SpaceshipAttackController.SpaceshipAttack.OFF;
        SpaceshipAttackController.positionHitSpaceship = null;
    }

    /**
     * Slow down the player, called by render().
     */
    private void slowPlayer() {
        if (slowPlayer) {
            slowPlayerTime -= ServiceLocator.getTimeSource().getDeltaTime();
            if (slowPlayerTime > 0) {
                logger.debug("Slow player remain time = {}", slowPlayerTime);
                player.setPosition((float) (player.getPosition().x - 0.04), player.getPosition().y);
            } else {
                slowPlayer = false;
                logger.debug("End of slow player");
            }
        }
    }

    /**
     * Slow down the player, called by render().
     */
    private void transferPlayerByMap() {
        if (newMapStatus == NewMap.START) {
            DistanceService.setPreDistance(player.getPosition().x);//get the last map distance of player
            // change 50 -> 53 for bigger player
            player.setPosition(0, 53);
            newMapStatus = NewMap.ON;
            logger.info("New map start.");
        } else if (newMapStatus == NewMap.FINISH) {
            // change 87 -> 89 for bigger player
            DistanceService.setPreDistance(player.getPosition().x - 89);//get the last map distance of player
            player.setPosition(89, 7);
            newMapStatus = NewMap.OFF;
            logger.info("New map finish.");
        }
    }

    private void generateObstaclesEnemiesByMapRefresh(int counter) {
        // Control the spawning of spaceships or other obstacles
        if (counter == 3) {
            forestGameArea.spawnSpaceship();
        } else {
            // Generate obstacles
            forestGameArea.spawnObstacles();
            // Generate meteorites
            forestGameArea.spawnMeteorites(0, 1, 2, 1, 1, 2);
            // Generate enemies
            forestGameArea.spawnFlyingMonkey();
        }
    }

    private Vector2 changeCameraLens() {
        // Centralize the screen to player
        Vector2 screenVector = player.getPosition();
        SpaceshipAttackController.SpaceshipAttack spaceshipState = SpaceshipAttackController.spaceshipState;

        // Update camera position (change based on team6 contribution)
        if (newMapStatus == NewMap.OFF) {
            screenVector.y = 7f;

            if (spaceshipState == SpaceshipAttackController.SpaceshipAttack.ON ||
                    spaceshipState == SpaceshipAttackController.SpaceshipAttack.START ||
                    (spaceshipState == SpaceshipAttackController.SpaceshipAttack.FINISH &&
                            screenVector.x <= SpaceshipAttackController.positionHitSpaceship.x)) {
                renderer.getCamera().getEntity().
                        setPosition(new Vector2(SpaceshipAttackController.positionHitSpaceship.x, 7f));
            } else {
                renderer.getCamera().getEntity().setPosition(screenVector);
            }
        } else if (newMapStatus == NewMap.ON) {
            screenVector.y = 55f;
            renderer.getCamera().getEntity().setPosition(screenVector);
        }
        // else: do nothing

        return screenVector;
    }

    @Override
    public void render(float delta) {

        //new Entity().getEvents().trigger("updateScore");
        //new Entity().getEvents().trigger("updateTime");

        physicsEngine.update();
        ServiceLocator.getEntityService().update();
        renderer.render();
        CombatStatsComponent playerStats = player.getComponent(CombatStatsComponent.class);
        if (playerStats.isDead()) {
            logger.info("Performing Post Game Tasks");
            /* NOTE: Call this method first before displaying the game over screen
             * and performing other tasks. This method has to be called as soon as
             * the player dies. */
            performPostGameTasks();

            logger.info("Display Game Over Screen");
            game.setScreen(GdxGame.ScreenType.GAME_OVER);

            return;
        }

        // Transfer the player according to the portal the player enters
        transferPlayerByMap();

        // making player to move constantly
        player.setPosition((float) (player.getPosition().x + 0.05), player.getPosition().y);

        Vector2 screenVector = changeCameraLens();


        // Generate terrain, obstacle and enemies
        // Generate terrain, obstacle and enemies
        if (newMapStatus == NewMap.OFF) {
            forestGameArea.stopNewMapMusic();
            forestGameArea.playMusic();
            // infinite loop for terrain and obstacles
            if (screenVector.x > (2 * counter + 1) * 10) {
                counter += 1;
                forestGameArea.showScrollingBackground(counter);
                forestGameArea.showNewMapScrollingBackground(counter, 47);
                forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2), TerrainFactory.TerrainType.MUD_ROAD);
                forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2), TerrainFactory.TerrainType.ROCK_ROAD);
                //                   forestGameArea.spawnRocksRandomly((int) (screenVector.x+2));
                forestGameArea.spawnWoodsone((int) (screenVector.x + 2));
                forestGameArea.spawnWoodstwo((int) (screenVector.x + 2));
                forestGameArea.spawnWoodsthree((int) (screenVector.x + 2));
                forestGameArea.spawnWoodsfour((int) (screenVector.x + 2));
                forestGameArea.spawnWoodsfive((int) (screenVector.x + 2));

                forestGameArea.spawnFireRocksone((int) (screenVector.x + 2));
                forestGameArea.spawnFireRockstwo((int) (screenVector.x + 2));
                forestGameArea.spawnFireRocksthree((int) (screenVector.x + 2));
                forestGameArea.spawnRockstwo((int) (screenVector.x + 2));
                //                   forestGameArea.spawnRocksthree((int) (screenVector.x + 2));
//                    forestGameArea.spawnRocksfour((int) (screenVector.x + 2));
//                    forestGameArea.spawnRocksfive((int) (screenVector.x + 2));
                //                   forestGameArea.spawnRockssix((int) (screenVector.x + 2));
                forestGameArea.spawnNailsone((int) (screenVector.x + 2));
                forestGameArea.spawnNailstwo((int) (screenVector.x + 2));
                forestGameArea.spawnNailsthree((int) (screenVector.x + 2));
                forestGameArea.spawnNailsfour((int) (screenVector.x + 2));
                generateObstaclesEnemiesByMapRefresh(counter);
            }

            slowPlayer();

        } else if (newMapStatus == NewMap.ON) {
            forestGameArea.stopMusic();
            forestGameArea.playNewMapMusic();
            // Render terrains on new map
            if (screenVector.x > (2 * counter + 1) * 10) {
                counter += 1;
//                    forestGameArea.showNewMapScrollingBackground(counter, 47);
                forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2), TerrainFactory.TerrainType.ROCK_ROAD);
                //                  forestGameArea.spawnRocksRandomly((int) (screenVector.x+2));
                forestGameArea.spawnWoodsone((int) (screenVector.x + 2));
                forestGameArea.spawnWoodstwo((int) (screenVector.x + 2));
                forestGameArea.spawnWoodsthree((int) (screenVector.x + 2));
                forestGameArea.spawnWoodsfour((int) (screenVector.x + 2));
                forestGameArea.spawnWoodsfive((int) (screenVector.x + 2));

                forestGameArea.spawnFireRocksone((int) (screenVector.x + 2));
                forestGameArea.spawnFireRockstwo((int) (screenVector.x + 2));
                forestGameArea.spawnFireRocksthree((int) (screenVector.x + 2));
                forestGameArea.spawnRockstwo((int) (screenVector.x + 2));
                //                   forestGameArea.spawnRocksthree((int) (screenVector.x + 2));
//                    forestGameArea.spawnRocksfour((int) (screenVector.x + 2));
//                    forestGameArea.spawnRocksfive((int) (screenVector.x + 2));
//                    forestGameArea.spawnRockssix((int) (screenVector.x + 2));
                forestGameArea.spawnNailsone((int) (screenVector.x + 2));
                forestGameArea.spawnNailstwo((int) (screenVector.x + 2));
                forestGameArea.spawnNailsthree((int) (screenVector.x + 2));
                forestGameArea.spawnNailsfour((int) (screenVector.x + 2));
                forestGameArea.spawnGoldNewMapRandomly((int) (screenVector.x + 2));
            }
        }
        long time = ServiceLocator.getTimeSource().getTime();
        if((time - lastSpawnTime) > 5000){
            forestGameArea.spawnFirstAid();
            lastSpawnTime = time;
        }
        // else: do nothing
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void pause() {
        logger.info("Game paused");
        //when user closes the window, the timer stops.
        ScoringSystemV1.stopTimer();
    }

    @Override
    public void resume() {
        logger.info("Game resumed");
    }

    @Override
    public void dispose() {
        logger.debug("Disposing main game screen");

        renderer.dispose();
        unloadAssets();

        ServiceLocator.getEntityService().dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getResourceService().dispose();

        ServiceLocator.clear();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(mainGameTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(mainGameTextures);
    }

    /**
     * Creates the main game's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new InputDecorator(stage, 10))
                .addComponent(new PerformanceDisplay())
                .addComponent(new MainGameActions(this.game))
                .addComponent(new MainGameExitDisplay())
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                //display the score and the time -- team 9
                .addComponent(new ScoreDisplay())
                .addComponent(new DistanceDisplay())
                .addComponent(new TimerDisplay())
                .addComponent(new TerminalDisplay())
                .addComponent(new FoodDisplay())
                .addComponent(new ItemBarDisplay())
                .addComponent(new RecycleDisplay())
                .addComponent(new WaterDisplay());


        ServiceLocator.getEntityService().register(ui);
    }

    /**
     * Tasks to perform when the game is over.
     * The game is considered to be over when the player dies.
     * <p>
     * NOTE: Make sure this method is called as soon as the player dies,
     * and before the game over screen is displayed.
     */
    private void performPostGameTasks() {
        /* Increment the number of games that have been played
         * NOTE: Perform all subsequent tasks after this has been called */
        GameInfo.incrementGameCount();
        /* Store the achievements record and in a JSON file and then reset achievements */
        GameRecords.storeGameRecord();
    }

    /**
     * all status of new map
     */
    public enum NewMap {
        OFF,
        START,  // Used once in render
        FINISH,  // Used once in render
        ON
    }
}