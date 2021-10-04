package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.ItemBar.ItemBarDisplay;
import com.deco2800.game.components.foodAndwater.RecoverDisplay;
import com.deco2800.game.components.npc.SpaceshipAttackController;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.score.*;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.files.GameInfo;
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
import com.deco2800.game.components.maingame.MainGameExitDisplay;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;
import com.deco2800.game.components.score.ScoreDisplay;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.components.score.TimerDisplay;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ScoreService;
import com.deco2800.game.services.ServiceLocator;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
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
            "images/itembar/recycle/recycle-256px-bb1.png",
            "images/itembar/recycle/recycle-256px-bb2.png",
            "images/itembar/recycle/recycle-256px-bb3.png",
            "images/itembar/recycle/recycle-256px-default.png",
            "images/itembar/recycle/recycle-256px-leg1.png",
            "images/itembar/recycle/recycle-256px-leg2.png",
            "images/itembar/recycle/recycle-256px-leg3.png",
            "images/itembar/recycle/recycle-256px-water1.png",
            "images/itembar/recycle/recycle-256px-water2.png",
            "images/itembar/recycle/recycle-256px-water3.png",
    };
    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);


    private final GdxGame game;
    private final Renderer renderer;
    private final PhysicsEngine physicsEngine;


    public static NewMap newMapStatus = NewMap.Off;

    public static enum NewMap {
        Off,
        Start,  // Used once in render
        Finish,  // Used once in render
        On;
    }
    public static Entity players;
    private Entity player;
    private ForestGameArea forestGameArea;
    private int counter = 0;


    private static boolean slowPlayer = false;
    private static float slowPlayerTime;


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

        newMapStatus = NewMap.Off;
        counter = 0;

        ServiceLocator.registerDistanceService(new DistanceService(player));

        resetSpaceshipAttackVariable();

    }

    private void resetSpaceshipAttackVariable() {
        // reset Spaceship Attack variables
        SpaceshipAttackController.spaceshipState = SpaceshipAttackController.SpaceshipAttack.Off;
        SpaceshipAttackController.positionHitSpaceship = null;
    }

    public static boolean isSlowPlayer() {
        return slowPlayer;
    }

    public static float getSlowPlayerTime() {
        return slowPlayerTime;
    }

    public static void setSlowPlayer(boolean slowPlayer) {
        MainGameScreen.slowPlayer = slowPlayer;
    }

    public static void setSlowPlayerTime(float slowPlayerTime) {
        MainGameScreen.slowPlayerTime = slowPlayerTime;
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
     * Slow down the player, called by render().
     */
    private void slowPlayer() {
        if (slowPlayer) {
            slowPlayerTime -= ServiceLocator.getTimeSource().getDeltaTime();
            if (slowPlayerTime > 0) {
                logger.debug("Slow player remain time = {}", slowPlayerTime);
                player.setPosition((float) (player.getPosition().x - 0.06), player.getPosition().y);
            } else {
                slowPlayer = false;
                logger.debug("End of slow player");
            }
        }
    }

    public static NewMap getNewMapStatus() {
        return newMapStatus;
    }

    /**
     * @param status How many seconds the player slows down.
     */
    public static void setNewMapStatus(NewMap status) {
        newMapStatus = status;
    }

    /**
     * Slow down the player, called by render().
     */
    private void TransferPlayerByMap() {
        if (newMapStatus == NewMap.Start) {
            player.setPosition(0, 50);
            newMapStatus = NewMap.On;
        } else if (newMapStatus == NewMap.Finish) {
            player.setPosition(87, 3);
            newMapStatus = NewMap.Off;
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
//        System.out.println("player.getPosition() = " + player.getPosition());

        // Update camera position (change based on team6 contribution)
        switch (newMapStatus) {
            case Off:
                screenVector.y = 7f;

                if (spaceshipState == SpaceshipAttackController.SpaceshipAttack.On || spaceshipState == SpaceshipAttackController.SpaceshipAttack.Start ||
                        (spaceshipState == SpaceshipAttackController.SpaceshipAttack.Finish && screenVector.x <= SpaceshipAttackController.positionHitSpaceship.x)) {
                    renderer.getCamera().getEntity().setPosition(new Vector2(SpaceshipAttackController.positionHitSpaceship.x, 7f));
                } else {
                    renderer.getCamera().getEntity().setPosition(screenVector);
                }
                break;
            case On:
                //System.out.println("player.getPosition() = " + player.getPosition());
                screenVector.y = 55f;
                renderer.getCamera().getEntity().setPosition(screenVector);
        }
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
        TransferPlayerByMap();

        // making player to move constantly
        player.setPosition((float) (player.getPosition().x + 0.05), player.getPosition().y);

        Vector2 screenVector = changeCameraLens();


        // Generate terrain, obstacle and enemies
        switch (newMapStatus) {
            case Off:
                forestGameArea.stopNewMapMusic();
                forestGameArea.playMusic();
                // infinite loop for terrain and obstacles
                if (screenVector.x > (2 * counter + 1) * 10) {
                    counter += 1;
                    forestGameArea.showScrollingBackground(counter);
                    forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2), TerrainFactory.TerrainType.MUD_ROAD);
                    forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2), TerrainFactory.TerrainType.ROCK_ROAD);
 //                   forestGameArea.spawnRocksRandomly((int) (screenVector.x+2));
                    forestGameArea.spawnWoodsRandomly((int) (screenVector.x+2));
                    forestGameArea.spawnRocksone((int) (screenVector.x + 2));
                    forestGameArea.spawnRockstwo((int) (screenVector.x + 2));
                    forestGameArea.spawnRocksthree((int) (screenVector.x + 2));
                    forestGameArea.spawnRocksfour((int) (screenVector.x + 2));
                    forestGameArea.spawnRocksfive((int) (screenVector.x + 2));
                    forestGameArea.spawnRockssix((int) (screenVector.x + 2));
                    generateObstaclesEnemiesByMapRefresh(counter);
                }
                // Generate monster
//                spownFacehugger();
                // Thorns effect trigger
                slowPlayer();
                break;
            case On:
                forestGameArea.stopMusic();
                forestGameArea.playNewMapMusic();
                // Render terrains on new map
                if (screenVector.x > (2 * counter + 1) * 10) {
                    counter += 1;
                    forestGameArea.showNewMapScrollingBackground(counter, 47);
                    forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2), TerrainFactory.TerrainType.ROCK_ROAD);
  //                  forestGameArea.spawnRocksRandomly((int) (screenVector.x+2));
                    forestGameArea.spawnWoodsRandomly((int) (screenVector.x+2));
                    forestGameArea.spawnRocksone((int) (screenVector.x+2));
                    forestGameArea.spawnRockstwo((int) (screenVector.x + 2));
                    forestGameArea.spawnRocksthree((int) (screenVector.x + 2));
                    forestGameArea.spawnRocksfour((int) (screenVector.x + 2));
                    forestGameArea.spawnRocksfive((int) (screenVector.x + 2));
                    forestGameArea.spawnRockssix((int) (screenVector.x + 2));
                    forestGameArea.spawnGoldNewMapRandomly((int) (screenVector.x + 2));
                }}

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
                .addComponent(new RecoverDisplay())
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
}