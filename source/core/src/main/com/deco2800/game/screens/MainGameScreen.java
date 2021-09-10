package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.terrain.TerrainComponent;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.foodAndwater.ChickenDisplay;
import com.deco2800.game.components.foodAndwater.WaterDisplay;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.maingame.MainGameDisplay;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.score.ScoreDisplay;
import com.deco2800.game.components.score.ScoringSystem;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.components.score.TimerDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.AchievementRecords;
import com.deco2800.game.files.GameInfo;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.terminal.Terminal;
import com.deco2800.game.ui.terminal.TerminalDisplay;
import com.deco2800.game.components.maingame.MainGameExitDisplay;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] mainGameTextures = {"images/heart.png", "images/clock.png", "images/scoreboard.png", "images/background.png"};
    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);


    private final GdxGame game;
    private final Renderer renderer;
    private final PhysicsEngine physicsEngine;

    private static Vector2 facehuggerPosition;
    private static boolean spownFacehugger;

    private Entity player;
    private ForestGameArea forestGameArea;
    private int counter = 0;

    private static boolean slowPlayer = false;
    private static float slowPlayerTime;


    public MainGameScreen(GdxGame game) {
        this.game = game;
        logger.debug("Initialising main game screen services");
        ServiceLocator.registerTimeSource(new GameTime());

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
    }

    /**
     * Set the location where the monster is spawned, and called by start() in ObstacleAttackTask.java.
     * The variable is used by the spokenFacehugger(). This function plays a role in render().
     * @param position Where the monster spawns.
     */
    public static void setSpownFacehugger(Vector2 position) {
        facehuggerPosition = position;
        spownFacehugger = true;
    }

    /**
     * Generate monsters based on the position of the enemy monkey, which is called by render().
     */
    private void spownFacehugger() {
        if (spownFacehugger) {
            forestGameArea.spawnFaceWorm(facehuggerPosition);
            spownFacehugger = false;
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
    }


    /**
     * Slow down the player, called by render().
     */
    private void slowPlayer() {
        if (slowPlayer) {
            slowPlayerTime -= ServiceLocator.getTimeSource().getDeltaTime();
            if (slowPlayerTime > 0) {
                player.setPosition((float) (player.getPosition().x - 0.06), player.getPosition().y);
            } else {
                slowPlayer = false;
            }
        }
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

        // making player to move constantly

        player.setPosition((float) (player.getPosition().x + 0.05), player.getPosition().y);

        // Centralize the screen to player
        Vector2 screenVector = player.getPosition();
        screenVector.y = 7f;
        renderer.getCamera().getEntity().setPosition(screenVector);
        // infinite loop for terrain and obstacles
        if (screenVector.x > (2 * counter + 1) * 10) {
            counter += 1;
            forestGameArea.showScrollingBackground(counter);
            forestGameArea.spawnTerrainRandomly((int) (screenVector.x + 2));
//      forestGameArea.spawnRocksRandomly((int) (screenVector.x+2));
//      forestGameArea.spawnWoodsRandomly((int) (screenVector.x+2));

            // Generate obstacles
            forestGameArea.spawnObstacles();
            // Generate meteorites
            forestGameArea.spawnMeteorites(0, 1, 2, 1, 1, 2);
            forestGameArea.spawnFlyingMonkey();
        }
        // Generate monster
        spownFacehugger();
        // Thorns effect trigger
        slowPlayer();

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
                .addComponent(new TimerDisplay())
                .addComponent(new TerminalDisplay())
                .addComponent(new ChickenDisplay())
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
        AchievementRecords.storeGameRecord();
    }
}