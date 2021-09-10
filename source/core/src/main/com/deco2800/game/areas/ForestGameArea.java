package com.deco2800.game.areas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.rendering.BackgroundRenderComponent;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Forest area for the demo game with trees, a player, and some enemies.
 */
public class ForestGameArea extends GameArea {

    private void spawnRocks() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(4, minPos, maxPos);
            Entity rock = ObstacleFactory.createRock();
            spawnEntityAt(rock, randomPos, true, false);
        }
    }

    public void spawnRocksRandomly(int xValue) {
        GridPoint2 minPos = new GridPoint2(xValue + 10, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(3, minPos, maxPos);
            Entity rock = ObstacleFactory.createRock();
            spawnEntityAt(rock, randomPos, true, false);
            GridPoint2 randomPosTwo = RandomUtils.randomX(4, minPos, maxPos);
            Entity rockTwo = ObstacleFactory.createRock();
            spawnEntityAt(rockTwo, randomPosTwo, true, false);
        }
    }

    private void spawnWoods() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(6, minPos, maxPos);
            Entity rock = ObstacleFactory.createWood();
            spawnEntityAt(rock, randomPos, true, false);
            GridPoint2 randomPosTwo = RandomUtils.randomX(8, minPos, maxPos);
            Entity rockTwo = ObstacleFactory.createWood();
            spawnEntityAt(rockTwo, randomPosTwo, true, false);
        }
    }

    public void spawnWoodsRandomly(int xValue) {
        GridPoint2 minPos = new GridPoint2(xValue + 10, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(6, minPos, maxPos);
            Entity rock = ObstacleFactory.createWood();
            spawnEntityAt(rock, randomPos, true, false);
            GridPoint2 randomPosTwo = RandomUtils.randomX(8, minPos, maxPos);
            Entity rockTwo = ObstacleFactory.createWood();
            spawnEntityAt(rockTwo, randomPosTwo, true, false);
        }
    }

    //  private void spawnTrees() {
    //    GridPoint2 minPos = new GridPoint2(0, 0);
    //    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);
    //
    //    for (int i = 0; i < NUM_TREES; i++) {
    //      GridPoint2 randomPos = RandomUtils.randomX(3, minPos, maxPos);
    //      Entity tree = ObstacleFactory.createTree(player);
    //      spawnEntityAt(tree, randomPos, true, false);
    //    }
    //  }

    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
    /* The number of each type of obstacle. Note: total obstacles cannot be greater than 20 (range of loading map)*/
    private static final int NUM_OBSTACLES = 2;

    private static final int NUM_GHOSTS = 2;
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(0, 10);
    private static final float WALL_WIDTH = 0.1f;
    private static final String[] forestTextures = {
            "images/box_boy_leaf.png",
            "images/images.jpg",
            "images/ghost_king.png",
            "images/ghost_1.png",
            "images/grass_1.png",
            "images/grass_2.png",
            "images/grass_3.png",
            "images/hex_grass_1.png",
            "images/hex_grass_2.png",
            "images/hex_grass_3.png",
            "images/enemy2.png",
            "images/iso_grass_1.png",
            "images/iso_grass_2.png",
            "images/iso_grass_3.png",
            "images/mpc/mpc_right.png",
            "images/road.png",
            "images/water.png",
            "images/rock.jpg",
            "images/wood.jpg",
            "images/Items/first_aid_kit.png",
            "images/Items/food.png",
            "images/obstacle_1_new.png",
            "images/obstacle2_vision2.png",
            "images/stone.png",
            "images/background.png",
            "images/monkey_original.png",
            "images/Facehugger.png",
            "images/stone1.png",
            "images/mpc/mpcAnimation.png"

    };
    private static final String[] forestTextureAtlases = {
            "images/terrain_iso_grass.atlas",
            "images/ghost.atlas",
            "images/ghostKing.atlas",
            "images/airport.atlas",
            "images/buff.atlas",
            "images/debuff.atlas",
            "images" +
                    "/obstacle_1.atlas",
            "images/obstacle_2.atlas",
            "images/mpcMovement.atlas",
            "images/monkey.atlas",
            "images/Facehugger.atlas",
            "images/obstacle_Meteorite.atlas",
            "images/mpc/mpcAnimation.atlas",
    };
    private static final String[] forestSounds = {"sounds/Impact4.ogg"};
    private static final String[] jumpSounds = {"sounds/jump.ogg"};
    private static final String[] turnSounds = {"sounds/turnDirection.ogg"};
    private static final String backgroundMusic = "sounds/temp_bgm.wav";
    private static final String[] forestMusic = {backgroundMusic};
    private boolean firstGenerate = true;

    private final TerrainFactory terrainFactory;

    public Entity player;

    public ForestGameArea(TerrainFactory terrainFactory) {
        super();
        this.terrainFactory = terrainFactory;
    }

    /**
     * Create the game area, including terrain, static entities (trees), dynamic entities (player)
     */
    @Override
    public void create() {
        loadAssets();
        showBackground();
        displayUI();
        spawnTerrain();

//        spawnRocks();
//        spawnWoods();

        player = spawnPlayer();
        spawnObstacles();

//        spawnGhosts();
//        spawnGhostKing();

        spawnFirstAid();
        playMusic();
        trackAchievements();
    }

    private void showBackground() {
        Entity gameBg = new Entity();
        gameBg.addComponent(new BackgroundRenderComponent("images/background.png"));
        spawnEntity(gameBg);
    }

    public void showScrollingBackground(int counter) {
        Entity gameBg = new Entity();
        BackgroundRenderComponent newBg = new BackgroundRenderComponent("images/background.png");
        newBg.setHorizontal(30f * counter);
        gameBg.addComponent(newBg);
        spawnEntity(gameBg);
    }

    private void displayUI() {
        Entity ui = new Entity();
        ui.addComponent(new GameAreaDisplay("Box Forest"));
        spawnEntity(ui);
    }

    public void spawnTerrain() {
        // Background terrain
        terrain = terrainFactory.createTerrain(TerrainType.FOREST_DEMO);

        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y), GridPoint2Utils.ZERO, false, false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y),
                new GridPoint2(tileBounds.x, 0),
                false,
                false);
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH),
                new GridPoint2(0, tileBounds.y),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH), GridPoint2Utils.ZERO, false, false);
    }


    public void spawnTerrainRandomly(int xValue) {
        // Background terrain
        terrain = terrainFactory.createTerrainRandomly(TerrainType.FOREST_DEMO, xValue);
        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y), GridPoint2Utils.ZERO, false, false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y),
                new GridPoint2(tileBounds.x, 0),
                false,
                false);
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH),
                new GridPoint2(0, tileBounds.y),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH), GridPoint2Utils.ZERO, false, false);
    }


    /**
     * Obstacles are randomly generated according to the position of the character. The first time it
     * is called, NUM_OBSTACLES obstacles are generated in the range of (0-30) units after the
     * player's position. For each subsequent call, the position of generating obstacles is twenty
     * units behind the player, and the generating range is 20 units.
     * <p>
     * For example, the first call to the player x position is 0, and the x range for generating
     * obstacles is 0-30.  The second call to the player's x position is 10, and the x range for
     * generating obstacles is 31-50.
     */
    public void spawnObstacles() {
        GridPoint2 minPos;
        GridPoint2 maxPos;
        GridPoint2 randomPos;
        GridPoint2 randomPos2;
        // Record the coordinates of all obstacles, Record the coordinates of all obstacles to prevent obstacles
        // from being generated at the same location
        ArrayList<GridPoint2> randomPoints = new ArrayList<GridPoint2>();

        int playerX = (int) player.getPosition().x;
        logger.info("player x coordinate: {}", playerX);

        if (firstGenerate) {
            minPos = new GridPoint2(playerX, 0);
            maxPos = new GridPoint2(playerX + 30, 0);
            firstGenerate = false;
        } else {
            minPos = new GridPoint2(playerX + 21, 0);
            maxPos = new GridPoint2(playerX + 40, 0);
        }

        for (int i = 0; i < NUM_OBSTACLES; i++) {
            do {
                randomPos = RandomUtils.randomX(3, minPos, maxPos);
            } while (randomPoints.contains(randomPos));
            randomPoints.add(randomPos);

            do {
                randomPos2 = RandomUtils.randomX(3, minPos, maxPos);
            } while (randomPoints.contains(randomPos2));
            randomPoints.add(randomPos2);

            Entity obstacle = ObstacleFactory.createPlantsObstacle(player);
            Entity obstacle2 = ObstacleFactory.createThornsObstacle(player);
            spawnEntityAt(obstacle, randomPos, true, false);
            spawnEntityAt(obstacle2, randomPos2, true, true);

        }
        logger.info("Min x: {}, Max x: {}; Total randomPoints {}", minPos.x, maxPos.x, randomPoints);
    }

    public void spawnRangeObstacles() {
        int playerX = (int) player.getPosition().x;
        GridPoint2 minPos = new GridPoint2(playerX + 10, 0);
        GridPoint2 maxPos = new GridPoint2(playerX + 40, 0);
        GridPoint2 randomPosTwo = RandomUtils.randomX(11, minPos, maxPos);
        Entity Range = ObstacleFactory.createRangeObstacle(player);
        spawnEntityAt(Range, randomPosTwo, true, true);
//        Entity ghost = NPCFactory.createGhost(player);
//        if (ObstacleAttackTask.enemy_posion!=null) {
//            spawnEntityAt(ghost, ObstacleAttackTask.enemy_posion, true, true);
//            System.out.println(ObstacleAttackTask.enemy_posion);
////            System.out.println(randomPosTwo);
//        }
    }

    public void spawnAttackObstacles(Vector2 position) {
        Entity ghost = NPCFactory.createGhost(player);

        spawnEntityAt(ghost, position, false, false);

    }


    /**
     * Generate a certain number of meteorites, called by render() in MainGameScreen. The final number of meteorites
     * is the sum of all parameters.
     *
     * Big size meteorites: 1.5 - 2 times of the multiples of meteorites texture,
     *                      total number is bigNum(+bigRandomRange), the values in parentheses are random.
     * Midden size meteorites: 1 - 1.5 times of the multiples of meteorites texture,
     *                      total number is middleNum(+midRandomRange)
     * Small size meteorites: 0.5 + randomSize: 0.5 - 1 times of the multiples of meteorites texture,
     *                      total number is smallNum(+smaillRandomRange)
     *
     * e.g. 1(+2) means that the number of generations is at least 1, and the final possible range is 1-3.
     *
     * @param bigNum At least the number of large meteorites generated.
     * @param middleNum At least the number of middle meteorites generated.
     * @param smallNum At least the number of small meteorites generated.
     * @param bigRandomRange The number of large meteorites that may be randomly generated.
     * @param midRandomRange The number of middle meteorites that may be randomly generated.
     * @param smaillRandomRange The number of small meteorites that may be randomly generated.
     */
    public void spawnMeteorites(int bigNum, int middleNum, int smallNum, int bigRandomRange, int midRandomRange, int smaillRandomRange) {
        int bigNumRandom = bigNum + (int) (Math.random() * (bigRandomRange + 1));
        int midNumRandom = middleNum + (int) (Math.random() * (midRandomRange + 1));
        int smallNumRandom = smallNum + (int) (Math.random() * (smaillRandomRange + 1));
        int meteoritesNum = bigNumRandom + midNumRandom + smallNumRandom;

        double randomSize; // Generate random range for size
        double bigSize;
        double midSize;
        double smallSize;

        for (int i = 0; i < meteoritesNum; i++) {
            randomSize = Math.random() * 0.5; // 0-0.5
            bigSize = 1.5 + randomSize; // 1.5 - 2 size of the meteorites
            midSize = 1 + randomSize; // 1 - 1.5 size of the meteorites
            smallSize = 0.5 + randomSize; // 0.5 - 1 size of the meteorites
            int x = (int) (int) (player.getPosition().x + Math.random() * 10);
            int y = (int) (20 + Math.random() * 2);
            GridPoint2 point = new GridPoint2(x, y);

            Entity stone;

            if (i < bigNumRandom) { // must have a big meteorites
                stone = ObstacleFactory.createMeteorite(player, (float) bigSize, ObstacleFactory.MeteoriteType.BigMeteorite);
                System.out.println("Big stone = " + point + "\ti = " + i);
            } else if (i < bigNumRandom + midNumRandom) {
                stone = ObstacleFactory.createMeteorite(player, (float) midSize, ObstacleFactory.MeteoriteType.MiddleMeteorite);
                System.out.println("Mid stone = " + point + "\ti = " + i);
            } else {
                stone = ObstacleFactory.createMeteorite(player, (float) smallSize, ObstacleFactory.MeteoriteType.SmallMeteorite);
                System.out.println("Small stone = " + point + "\ti = " + i);
            }


            spawnEntityAt(stone, point, true, true);
        }
        logger.info("bigNumRandom = {}, midNumRandom = {}, smallNumRandom = {}", bigNumRandom, midNumRandom, smallNumRandom);
    }


    private void spawnFirstAid() {

        for (int i = 1; i < 31; i++) {
            GridPoint2 position = new GridPoint2(i * 3, 5);
            Entity firstAid = ItemFactory.createFirstAid(player);
            spawnEntityAt(firstAid, position, false, false);
        }
    }


    private Entity spawnPlayer() {
        Entity newPlayer = PlayerFactory.createPlayer();
        spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
        return newPlayer;
    }

    private void spawnGhosts() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_GHOSTS; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            Entity ghost = NPCFactory.createGhost(player);
            spawnEntityAt(ghost, randomPos, true, true);
        }
    }

    private void spawnGhostKing() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity ghostKing = NPCFactory.createGhostKing(player);
        spawnEntityAt(ghostKing, randomPos, true, true);
    }

    private void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(forestTextures);
        resourceService.loadTextureAtlases(forestTextureAtlases);
        resourceService.loadSounds(forestSounds);
        resourceService.loadSounds(jumpSounds);
        resourceService.loadSounds(turnSounds);
        resourceService.loadMusic(forestMusic);

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            logger.info("Loading... {}%", resourceService.getProgress());
        }
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(forestTextures);
        resourceService.unloadAssets(forestTextureAtlases);
        resourceService.unloadAssets(forestSounds);
        resourceService.unloadAssets(jumpSounds);
        resourceService.unloadAssets(turnSounds);
        resourceService.unloadAssets(forestMusic);
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
        this.unloadAssets();
    }


    /**
     * Achievements system tracking which depends on the game area's lifecycle
     */
    private void trackAchievements() {
        spawnEntity(AchievementFactory.createAchievementEntity());
    }

}
