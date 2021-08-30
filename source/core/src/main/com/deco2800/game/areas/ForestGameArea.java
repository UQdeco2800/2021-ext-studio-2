package com.deco2800.game.areas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
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
            "images/mpc_front_stroke.png",
            "images/mpc_left_view.png",
            "images/mpc_right_view.png",
            "images/road.png",
            "images/water.png",
            "images/Items/first_aid_kit.png",
            "images/obstacle_1_new.png",
            "images/obstacle2_vision2.png"
    };
    private static final String[] forestTextureAtlases = {
            "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas", "images/airport.atlas", "images/obstacle_1.atlas", "images/obstacle_2.atlas"
    };
    private static final String[] forestSounds = {"sounds/Impact4.ogg"};
    private static final String[] jumpSounds = {"sounds/jump.ogg"};
    private static final String[] turnSounds = {"sounds/turnDirection.ogg"};
    private static final String backgroundMusic = "sounds/neverGonna.mp3";
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

        displayUI();

        spawnTerrain();

        player = spawnPlayer();
        spawnObstacles();


//        spawnGhosts();
//        spawnGhostKing();
        spawnFirstAid();
        playMusic();
        trackAchievements();
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
     * <p>
     * You can uncomment to view the player's position and the range and specific coordinates of the
     * generated obstacles.
     */
    public void spawnObstacles() {
        GridPoint2 minPos;
        GridPoint2 maxPos;
        GridPoint2 randomPos;
        GridPoint2 randomPos2;
        ArrayList<GridPoint2> randomPoints = new ArrayList<GridPoint2>();

        int playerX = (int) player.getPosition().x;
//        System.out.print("playerX:" + playerX + "\n");

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
//        System.out.print("minPos: " + minPos + "\tmaxPos: " + maxPos + "\nTotal randomPoints" + randomPoints + "\n");
    }

    private void spawnFirstAid() {

        for (int i = 1; i < 6; i++) {
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