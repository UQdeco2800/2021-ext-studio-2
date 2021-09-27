package com.deco2800.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.terrain.TerrainComponent;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.Obstacle.ObstacleDisappear;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.items.PropsShopDisplay;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.maingame.MainGameDisplay;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import com.deco2800.game.components.player.PlayerActions;
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
import com.deco2800.game.utils.math.Vector2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.components.foodAndwater.FoodDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] mainGameTextures = {"images/heart.png", "images/clockV2.png",
            "images/scoreboardV2.png", "images/background.png", "images/water1.png", "images/food1.png",
            "images/Sprint2_Buffs_Debuffs/Poisoning.png", "images/Sprint2_Buffs_Debuffs/decrease_health.png", "images" +
            "/Sprint2_Buffs_Debuffs/increase_health.png", "images/Sprint2_Buffs_Debuffs/decrease_speed.png"
    };
    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);


    private final GdxGame game;
    private final Renderer renderer;
    private final PhysicsEngine physicsEngine;

    private static Vector2 facehuggerPosition;
    private static boolean spownFacehugger;

    public static spaceshipAttack spaceshipState = spaceshipAttack.Off;
    private static Vector2 positionHitSpaceship;
    public static float spaceshipTime = 10f;
    private int counterSmallMissile = 0;
    public static newMap newMapStatus = newMap.Off;

    public static enum spaceshipAttack {
        Off,
        Start, // Used once in render
        On,
        Finish;
    }

    public static enum newMap {
        Off,
        Begin,  // Used once in render
        Finish,  // Used once in render
        On;
    }

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

        ServiceLocator.registerDistanceService(new DistanceService(player));
    }

    /**
     * Set the location where the monster is spawned, and called by start() in ObstacleAttackTask.java.
     * The variable is used by the spokenFacehugger(). This function plays a role in render().
     *
     * @param position Where the monster spawns.
     */
    public static void setSpownFacehugger(Vector2 position) {
        facehuggerPosition = position;
        spownFacehugger = true;
        logger.debug("Set facehuggerPosition = {}, spownFacehugger = {}", facehuggerPosition, spownFacehugger);
    }

    /**
     * Generate monsters based on the position of the enemy monkey, which is called by render().
     */
    private void spownFacehugger() {
        if (spownFacehugger) {
            forestGameArea.spawnFaceWorm(facehuggerPosition);
            spownFacehugger = false;
            logger.debug("Set spownFacehugger = {}", spownFacehugger);
        }
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

    /**
     * Generate monsters based on the position of the enemy monkey, which is called by render().
     */
    public static void setSpaceshipAttack() {
        spaceshipState = spaceshipAttack.Start;
    }

    private void spaceshipSceneBegins() {
        //                System.out.println("Start");
        positionHitSpaceship = player.getPosition();
        player.setPosition(player.getPosition().x - 8, player.getPosition().y);
        player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);
        // If the player is walking, stop
        if (!player.getComponent(KeyboardPlayerInputComponent.class).getWalkDirection().epsilonEquals(Vector2.Zero)) {
            player.getComponent(KeyboardPlayerInputComponent.class).keyUp(Input.Keys.D);
        }
        spaceshipState = spaceshipAttack.On;
    }

    private void spaceshipAttackScene() {
//                System.out.println("On");
        player.setPosition((float) (player.getPosition().x - 0.05), player.getPosition().y);
        spaceshipTime -= ServiceLocator.getTimeSource().getDeltaTime();

        if (spaceshipTime <= 0) {
            spaceshipState = spaceshipAttack.Finish;
            forestGameArea.spawnPortal(new Vector2(85, 8), ObstacleDisappear.ObstacleType.PortalEntrance);
            AchievementsHelper.getInstance().trackSpaceshipAvoidSuccess();

        } else if (spaceshipTime <= 5 && counterSmallMissile % 100 == 0) {
//            System.out.println("Hard counterSmallMissile = " + counterSmallMissile);

            // 难
            switch (counterSmallMissile) {
                case 300:
                    forestGameArea.spawnSmallMissile(new Vector2(85, 5));
                    forestGameArea.spawnSmallMissile(new Vector2(85, 11));
                    break;
                case 400:
                    forestGameArea.spawnSmallMissile(new Vector2(85, 8));
                    forestGameArea.spawnSmallMissile(new Vector2(85, 11));
                    break;
                case 500:
                    forestGameArea.spawnSmallMissile(new Vector2(85, 5));
                    forestGameArea.spawnSmallMissile(new Vector2(85, 8));
            }

        } else if (spaceshipTime <= 10 && counterSmallMissile % 100 == 0) {
//            System.out.println("Essay counterSmallMissile = " + counterSmallMissile);
            // 简单
            switch (counterSmallMissile) {
                case 0:
                    forestGameArea.spawnSmallMissile(new Vector2(85, 5f));
                    break;
                case 100:
                    forestGameArea.spawnSmallMissile(new Vector2(85, 8f));
                    break;
                case 200:
                    forestGameArea.spawnSmallMissile(new Vector2(85, 11f));
            }
        }

        counterSmallMissile++;
    }

    /**
     * Set the position and status of the character according to the state of the spacecraft, which is called by render().
     */
    private void SpaceshipAttackScene() {
        switch (spaceshipState) {
            case Start:
                spaceshipSceneBegins();
                break;
            case On:
                spaceshipAttackScene();
                break;
            case Finish:
//                System.out.println("Finish");
            default:
        }
    }

    /**
     * @param status How many seconds the player slows down.
     */
    public static void setNewMapStatus(newMap status) {
        newMapStatus = status;
    }

    /**
     * Slow down the player, called by render().
     */
    private void TransferPlayerByMap() {
        if (newMapStatus == newMap.Begin) {
            player.setPosition(0, 50);
            newMapStatus = newMap.On;
        } else if (newMapStatus == newMap.Finish) {
            player.setPosition(87, 3);
            newMapStatus = newMap.Off;
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
//        System.out.println("player.getPosition() = " + player.getPosition());

        // Update camera position (change based on team6 contribution)
        switch (newMapStatus) {
            case Off:
                screenVector.y = 7f;
                if (spaceshipState == spaceshipAttack.On || spaceshipState == spaceshipAttack.Start ||
                        (spaceshipState == spaceshipAttack.Finish && screenVector.x <= positionHitSpaceship.x)) {
                    renderer.getCamera().getEntity().setPosition(new Vector2(positionHitSpaceship.x, 7f));
                } else {
                    renderer.getCamera().getEntity().setPosition(screenVector);
                }
                break;
            case On:
                System.out.println("player.getPosition() = " + player.getPosition());
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

        // Control the position of the character when the spaceship appears
        SpaceshipAttackScene();
        // Transfer the player according to the portal the player enters
        TransferPlayerByMap();

        // making player to move constantly
        player.setPosition((float) (player.getPosition().x + 0.05), player.getPosition().y);

        Vector2 screenVector = changeCameraLens();


        // Generate terrain, obstacle and enemies
        switch (newMapStatus) {
            case Off:
                // infinite loop for terrain and obstacles
                if (screenVector.x > (2 * counter + 1) * 10) {
                    counter += 1;
                    forestGameArea.showScrollingBackground(counter);
                    forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2));
                    //      forestGameArea.spawnRocksRandomly((int) (screenVector.x+2));
                    //      forestGameArea.spawnWoodsRandomly((int) (screenVector.x+2));
                    generateObstaclesEnemiesByMapRefresh(counter);
                }
                // Generate monster
                spownFacehugger();
                // Thorns effect trigger
                slowPlayer();
                break;
        }

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