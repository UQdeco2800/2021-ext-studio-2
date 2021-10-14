package com.deco2800.game.areas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.items.PropShopHelper;
import com.deco2800.game.components.obstacle.ObstacleEventHandler;
import com.deco2800.game.components.achievements.AchievementsBonusItems;
import com.deco2800.game.components.items.InventorySystem;
import com.deco2800.game.components.items.ItemBar;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.files.MPCConfig;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.PhysicsComponent;
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


//    private void spawnRocks() {
//        GridPoint2 minPos = new GridPoint2(0, 0);
//        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);
//
//        for (int i = 0; i < 5; i++) {
//            GridPoint2 randomPos = new GridPoint2(0, 0);
//            Entity rock = ObstacleFactory.createRock();
//            spawnEntityAt(rock, randomPos, true, false);
//        }
//    }

// nail

//public void spawnNailsRandomly(int xValue) {
 //   for (int i = 0; i < 5; i++) {
//        GridPoint2 pos = new GridPoint2(xValue + 2 +i, 53);
//        Entity rock = ObstacleFactory.createNail();
//        spawnEntityAt(rock, pos, true, false);
//    }
//}

    private void spawnNails() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(52, minPos, maxPos);
            Entity nail = ObstacleFactory.createNail();
            spawnEntityAt(nail, randomPos, true, false);
            GridPoint2 randomPosTwo = RandomUtils.randomX(54, minPos, maxPos);
            Entity nailTwo = ObstacleFactory.createWood();
            spawnEntityAt(nailTwo, randomPosTwo, true, false);
        }
    }
    public void spawnNailsone(int xValue) {

        for (int i = 0; i < 5; i++) {
            GridPoint2 pos = new GridPoint2(xValue +i, 60);
            Entity nail = ObstacleFactory.createNail();
            spawnEntityAt(nail, pos, true, false);
        }
    }
    public void spawnNailstwo(int xValue) {

        for (int i = 0; i < 3; i++) {
            GridPoint2 pos = new GridPoint2(xValue +7 +i, 60);
            Entity nail = ObstacleFactory.createNail();
            spawnEntityAt(nail, pos, true, false);
        }
    }

    public void spawnNailsthree(int xValue) {

        for (int i = 0; i < 4; i++) {
            GridPoint2 pos = new GridPoint2(xValue -3 +i, 58);
            Entity nail = ObstacleFactory.createNail();
            spawnEntityAt(nail, pos, true, false);
        }
    }
    public void spawnNailsfour(int xValue) {

        for (int i = 0; i < 3; i++) {
            GridPoint2 pos = new GridPoint2(xValue +3 +i, 58);
            Entity nail = ObstacleFactory.createNail();
            spawnEntityAt(nail, pos, true, false);
        }
    }
    public void spawnWoodsone(int xValue) {

        for (int i = 0; i < 5; i++) {
            GridPoint2 pos = new GridPoint2(xValue +i, 55);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, pos, true, false);
        }
    }
    public void spawnWoodstwo(int xValue) {

        for (int i = 0; i < 3; i++) {
            GridPoint2 pos = new GridPoint2(xValue -5 +i, 55);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, pos, true, false);
        }
    }
    public void spawnWoodsthree(int xValue) {

        for (int i = 0; i < 2; i++) {
            GridPoint2 pos = new GridPoint2(xValue +6 +i, 53);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, pos, true, false);
        }
    }
    public void spawnWoodsfour(int xValue) {

        for (int i = 0; i < 2; i++) {
            GridPoint2 pos = new GridPoint2(xValue +9 +i, 54);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, pos, true, false);
        }
    }
    public void spawnWoodsfive(int xValue) {

        for (int i = 0; i < 2; i++) {
            GridPoint2 pos = new GridPoint2(xValue +12 +i, 56);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, pos, true, false);
        }
    }

    /**
     * Generate line 5 rocks in the new map
     * @param xValue horizontal start point
    */
    public void spawnFireRocksone(int xValue) {

        for (int i = 0; i < 5; i++) {
            GridPoint2 pos = new GridPoint2(xValue + -4 +i, 50);
            Entity frock = ObstacleFactory.createFirerock();
            spawnEntityAt(frock, pos, true, false);
        }
    }

    /**
     * Generate rocks pyramid
     *     N
     *    NN
     *   NNN
     * @param xValue horizontal start point
     */
    public void spawnRockstwo(int xValue) {

            GridPoint2 Pos = new GridPoint2 ( xValue + 10, 50);
            Entity rock = ObstacleFactory.createRock();
            spawnEntityAt(rock, Pos, true, false);
            GridPoint2 PosTwo = new GridPoint2 ( xValue + 11, 51);
            Entity rockTwo = ObstacleFactory.createRock();
            spawnEntityAt(rockTwo, PosTwo, true, false);
            GridPoint2 PosThree = new GridPoint2 ( xValue + 12, 52);
            Entity rockThree = ObstacleFactory.createRock();
            spawnEntityAt(rockThree, PosThree, true, false);
            GridPoint2 PosFour = new GridPoint2 ( xValue + 12, 51);
            Entity rockFour = ObstacleFactory.createRock();
            spawnEntityAt(rockFour, PosFour, true, false);
            GridPoint2 PosFive = new GridPoint2 ( xValue + 12, 50);
            Entity rockFive = ObstacleFactory.createRock();
            spawnEntityAt(rockFive, PosFive, true, false);
            GridPoint2 PosSix = new GridPoint2 ( xValue + 11, 50);
            Entity rockSix = ObstacleFactory.createRock();
            spawnEntityAt(rockSix, PosSix, true, false);
    }
    public void spawnFireRocksthree(int xValue) {

        GridPoint2 Pos = new GridPoint2(xValue -5, 53);
        Entity rock = ObstacleFactory.createFirerock();
        spawnEntityAt(rock, Pos, true, false);
        GridPoint2 PosTwo = new GridPoint2(xValue -6, 53);
        Entity rockTwo = ObstacleFactory.createFirerock();
        spawnEntityAt(rockTwo, PosTwo, true, false);

    }

    /**
     * Generate opposite rocks pyramid
     *    N
     *    NN
     *    NNN
     * @param xValue horizontal start point
     */

    /*
    public void spawnRocksthree(int xValue) {


        GridPoint2 Pos = new GridPoint2(xValue + 14, 52);
        Entity rock = ObstacleFactory.createRock();
        spawnEntityAt(rock, Pos, true, false);
        GridPoint2 PosTwo = new GridPoint2(xValue + 14, 51);
        Entity rockTwo = ObstacleFactory.createRock();
        spawnEntityAt(rockTwo, PosTwo, true, false);
        GridPoint2 PosThree = new GridPoint2(xValue + 14, 50);
        Entity rockThree = ObstacleFactory.createRock();
        spawnEntityAt(rockThree, PosThree, true, false);
        GridPoint2 PosFour = new GridPoint2(xValue + 15, 51);
        Entity rockFour = ObstacleFactory.createRock();
        spawnEntityAt(rockFour, PosFour, true, false);
        GridPoint2 PosFive = new GridPoint2(xValue + 15, 50);
        Entity rockFive = ObstacleFactory.createRock();
        spawnEntityAt(rockFive, PosFive, true, false);
        GridPoint2 PosSix = new GridPoint2(xValue + 16, 50);
        Entity rockSix = ObstacleFactory.createRock();
        spawnEntityAt(rockSix, PosSix, true, false);

    }

     */

    /**
     * Generate 5 rocks column from the ground up
     * @param xValue horizontal start point
     */
    /*
    public void spawnRocksfour(int xValue) {

        for (int i = 0; i < 5; i++) {

            GridPoint2 pos = new GridPoint2(xValue + 18, 50 + i);
            Entity rock = ObstacleFactory.createRock();
            spawnEntityAt(rock, pos, true, false);

        }
    }
*/


    /**
     * Generate 5 rocks column from the sky down
     * @param xValue horizontal start point
     */
     /*
    public void spawnRocksfive(int xValue) {

        for (int i = 0; i < 4; i++) {

            GridPoint2 pos = new GridPoint2(xValue + 20, 57 + i);
            Entity rock = ObstacleFactory.createRock();
            spawnEntityAt(rock, pos, true, false);

        }
    }
     */
    /**
     * Genrate large rock pyramid
     *    N
     *    NN
     *    NNN
     *    NNNN
     * @param xValue horizontal start point
     */


    public void spawnFireRockstwo(int xValue) {

        GridPoint2 Pos = new GridPoint2(xValue + 22, 50);
        Entity rock = ObstacleFactory.createFirerock();
        spawnEntityAt(rock, Pos, true, false);
        GridPoint2 PosTwo = new GridPoint2(xValue + 23, 50);
        Entity rockTwo = ObstacleFactory.createFirerock();
        spawnEntityAt(rockTwo, PosTwo, true, false);
        GridPoint2 PosThree = new GridPoint2(xValue + 23, 51);
        Entity rockThree = ObstacleFactory.createFirerock();
        spawnEntityAt(rockThree, PosThree, true, false);
        GridPoint2 PosFour = new GridPoint2(xValue + 24, 50);
        Entity rockFour = ObstacleFactory.createFirerock();
        spawnEntityAt(rockFour, PosFour, true, false);
        GridPoint2 PosFive = new GridPoint2(xValue + 24, 51);
        Entity rockFive = ObstacleFactory.createFirerock();
        spawnEntityAt(rockFive, PosFive, true, false);
        GridPoint2 PosSix = new GridPoint2(xValue + 24, 52);
        Entity rockSix = ObstacleFactory.createFirerock();
        spawnEntityAt(rockSix, PosSix, true, false);
        GridPoint2 PosSeven = new GridPoint2(xValue + 25, 50);
        Entity rockSeven = ObstacleFactory.createFirerock();
        spawnEntityAt(rockSeven, PosSeven, true, false);
        GridPoint2 PosEight = new GridPoint2(xValue + 25, 51);
        Entity rockEight = ObstacleFactory.createFirerock();
        spawnEntityAt(rockEight, PosEight, true, false);
        GridPoint2 PosNine = new GridPoint2(xValue + 25, 52);
        Entity rockNine = ObstacleFactory.createFirerock();
        spawnEntityAt(rockNine, PosNine, true, false);
        GridPoint2 PosTen = new GridPoint2(xValue + 25, 53);
        Entity rockTen = ObstacleFactory.createFirerock();
        spawnEntityAt(rockTen, PosTen, true, false);


    }


    /**
     * Gennerate 5 wood randomly in the 52f and 54f height
     */
    private void spawnWoods() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(52, minPos, maxPos);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, randomPos, true, false);
            GridPoint2 randomPosTwo = RandomUtils.randomX(54, minPos, maxPos);
            Entity woodTwo = ObstacleFactory.createWood();
            spawnEntityAt(woodTwo, randomPosTwo, true, false);
        }
    }

    /**
     * Generate 5 wood randomly in the 52f and 54f height and given horizontal start point
     * @param xValue horizontal start point
     */
    public void spawnWoodsRandomly(int xValue) {
        GridPoint2 minPos = new GridPoint2(xValue + 10, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < 5; i++) {
            GridPoint2 randomPos = RandomUtils.randomX(52, minPos, maxPos);
            Entity wood = ObstacleFactory.createWood();
            spawnEntityAt(wood, randomPos, true, false);
            GridPoint2 randomPosTwo = RandomUtils.randomX(54, minPos, maxPos);
            Entity woodTwo = ObstacleFactory.createWood();
            spawnEntityAt(woodTwo, randomPosTwo, true, false);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
    /* The number of each type of obstacle. Note: total obstacles cannot be greater than 20 (range of loading map)*/
    private static final int NUM_OBSTACLES = 2;

    private static final int NUM_GHOSTS = 2;
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(0, 10);
    private static final float WALL_WIDTH = 0.1f;
    private ItemBar itembar;
    private static final String[] forestTextures = {
            "images/firerock.jpg",
            "images/nail.jpg",
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
            "images/road.png",
            "images/water.png",
            "images/rock.jpg",
            "images/wood.jpg",
            "images/terrain2.png",
            "images/Items/first_aid_kit.png",
            "images/Items/food.png",
            "images/Items/water.png",
            "images/Items/magic_potion.png",
            "images/Items/bandage.png",
            "images/Items/syringe.png",
            "images/Items/goldCoin.png",
            "images/obstacle_1_new.png",
            "images/obstacle2_vision2.png",
            "images/stone.png",
            "images/background.png",
            "images/background_2.png",
            "images/background_2.JPG",
            "images/monkey_original.png",
            "images/Facehugger.png",
            "images/stone1.png",
            "images/food1.png",
            "images/water1.png",
            "images/ufo.png",
            "images/rocket-ship-launch.png",
            "images/portal.png",
            "images/missile.png",
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
            "images/pao.png",


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
            "images/monkey.atlas",
            "images/Facehugger.atlas",
            "images/obstacle_Meteorite.atlas",
            "images/mpc/mpcAnimation.atlas",
            "images/spaceship.atlas",

            "images/food1.png",
            "images/water1.png",
            "images/missile.atlas",
            "images/portal.atlas",
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
            "images/pao.png"


    };
    private static final String[] forestSounds = {
            "sounds/Impact4.ogg",
            "sounds/missile_explosion.mp3",
            "sounds/monster_roar.mp3",
            "sounds/spacecraft_floating.mp3"
    };

    private static final String[] mpcTextures = {
            "images/mpc/finalAtlas/OG_buff_to_be_tested/mpcAnimation.png",
            "images/mpc/finalAtlas/gold_2/mpcAnimation_2.png",
            "images/mpc/finalAtlas/gold_4_buff_to_be_test/mpcAnimation_4.png",
            "images/mpc/finalAtlas/gold_6_buff_to_be_tested/mpcAnimation_6.png",
            "images/mpc/finalAtlas/OG_buff_to_be_tested/mpc_right.png",
            "images/mpc/finalAtlas/gold_2/mpc_right.png",
            "images/mpc/finalAtlas/gold_4_buff_to_be_test/mpc_right.png",
            "images/mpc/finalAtlas/gold_6_buff_to_be_tested/mpc_right.png",
            "images/Items/3.png"

    };
    private static final String[] mpcTexturesAtlases = {
            "images/mpc/finalAtlas/OG_buff_to_be_tested/mpcAnimation.atlas",
            "images/mpc/finalAtlas/gold_2/mpcAnimation_2.atlas",
            "images/mpc/finalAtlas/gold_4_buff_to_be_test/mpcAnimation_4.atlas",
            "images/mpc/finalAtlas/gold_6_buff_to_be_tested/mpcAnimation_6.atlas",

    };

    private static final String[] jumpSounds = {"sounds/jump.ogg"};
    private static final String itemSounds = "sounds/itembar/item-use.mp3";
    private static final String[] itemMusic = {itemSounds};
    private static final String[] turnSounds = {"sounds/turnDirection.ogg"};
    private static final String BACKGROUNDMUSIC = "sounds/temp_bgm.wav";
    private static final String NEWMAP_BACKGROUNDMUSIC = "sounds/track2.mp3";
    private static final String[] forestMusic = {BACKGROUNDMUSIC};
    private static final String[] newMapMusic = {NEWMAP_BACKGROUNDMUSIC};
    private boolean firstGenerate = true;

    private final TerrainFactory terrainFactory;

    public Entity player;
    private InventorySystem pro;

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
        showNewMapBackground();
        displayUI();
        spawnTerrain(TerrainType.MUD_ROAD);
        spawnTerrain(TerrainType.ROCK_ROAD);

//        spawnRocks();
//         spawnWoods();
//        spawnNails();


        MPCConfig.updateValues();

        player = spawnPlayer();
        spawnObstacles();
        //Buff buff = new Buff(player);
        //buff.addHealth();
        pro = new InventorySystem(player);
        spawnFirstAid();
        spawnGold();
        spawnGoldNewMap();
        playMusic();
        trackAchievements();
        trackBuffDescription();
        setBonusItems(player);
        PropShopHelper.useProps(player);
        player.getEvents().addListener("B pressed", this::InvSys);
        spawnPortal(new Vector2(50, 55), ObstacleEventHandler.ObstacleType.PortalExport);
    }

    public void InvSys() {
        pro.pressbutton();
    }

    /**
     * Show regular map background
     */
    private void showBackground() {
        Entity gameBg = new Entity();
        gameBg.addComponent(new BackgroundRenderComponent("images/background.png"));
        spawnEntity(gameBg);
    }

    /**
     * Show background in the new map
     */
    private void showNewMapBackground() {
        Entity gameBg = new Entity();
        BackgroundRenderComponent newBg = new BackgroundRenderComponent("images/background_2.JPG");
        newBg.setVertical(47);
        gameBg.addComponent(newBg);
        spawnEntity(gameBg);
    }

    /**
     * Show background in the regular map according to the given counter
     * @param counter change the horizontal start point of the background
     */
    public void showScrollingBackground(int counter) {
        Entity gameBg = new Entity();
        BackgroundRenderComponent newBg = new BackgroundRenderComponent("images/background.png");
        newBg.setHorizontal(30f * counter);
        gameBg.addComponent(newBg);
        spawnEntity(gameBg);
    }

    /**
     * Show background in the new map according to the given horizontal and vertical start point
     * @param counter change the horizontal start point
     * @param vertical change the vertical start point
     */
    public void showNewMapScrollingBackground(int counter, float vertical) {
        Entity gameBg = new Entity();
        BackgroundRenderComponent newBg = new BackgroundRenderComponent("images/background_2.JPG");
        newBg.setVertical(vertical);
        newBg.setHorizontal(30f * counter);
        gameBg.addComponent(newBg);
        spawnEntity(gameBg);
    }

    private void displayUI() {
        Entity ui = new Entity();
        ui.addComponent(new GameAreaDisplay("Box Forest"));
        spawnEntity(ui);
    }

    /**
     * Generate the first set of terrain
     * @param terrainType change the type of the terrain
     */
    public void spawnTerrain(TerrainType terrainType) {
        // Background terrain
        if (terrainType == TerrainType.MUD_ROAD) {
            terrain = terrainFactory.createTerrain(TerrainType.MUD_ROAD);
        } else if (terrainType == TerrainType.ROCK_ROAD) {
            terrain = terrainFactory.createTerrain(TerrainType.ROCK_ROAD);
        }


        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

//        // Left
//        spawnEntityAt(
//                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y, PhysicsLayer.WALL), GridPoint2Utils.ZERO, false, false);
//        // Right
//        spawnEntityAt(
//                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y, PhysicsLayer.WALL),
//                new GridPoint2(tileBounds.x, 0),
//                false,
//                false);

        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH, PhysicsLayer.WALL),
                new GridPoint2(0, tileBounds.y),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH, PhysicsLayer.WALL), GridPoint2Utils.ZERO, false, false);

        spawnInvisibleCeiling(worldBounds, tileBounds);
    }

    /**
     * Generate the continuous terrain after the first set of terrain
     *
     * @param xValue control the position of the terrain
     * @param terrainType change the type of the terrain
     */
    public void spawnTerrainRandomly(int xValue, TerrainType terrainType) {
        // Background terrain
        if (terrainType == TerrainType.MUD_ROAD) {
            terrain = terrainFactory.createTerrainRandomly(TerrainType.MUD_ROAD, xValue);
        } else if (terrainType == TerrainType.ROCK_ROAD) {
            terrain = terrainFactory.createTerrainRandomly(TerrainType.ROCK_ROAD, xValue);
        }
        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

//        // Left
//        spawnEntityAt(
//                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y, PhysicsLayer.WALL), GridPoint2Utils.ZERO, false, false);
//        // Right
//        spawnEntityAt(
//                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y, PhysicsLayer.WALL),
//                new GridPoint2(tileBounds.x, 0),
//                false,
//                false);

        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH, PhysicsLayer.WALL),
                new GridPoint2(0, tileBounds.y),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH, PhysicsLayer.WALL), GridPoint2Utils.ZERO, false, false);


        spawnInvisibleCeiling(worldBounds, tileBounds);

    }


    /**
     * Generate invisible ceiling on top to avoid the character jump out of the map
     *
     * @param worldBounds wall position value
     * @param tileBounds  tile position value
     */
    public void spawnInvisibleCeiling(Vector2 worldBounds, GridPoint2 tileBounds) {

        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH, PhysicsLayer.CEILING),
                new GridPoint2(0, tileBounds.y + 8),
                false,
                false);
    }


    /**
     * Obstacles are randomly generated according to the position of the character. The first time it
     * is called, NUM_OBSTACLES obstacles are generated in the range of (0-30) units after the
     * player's position. For each subsequent call, the position of generating obstacles is twenty
     * units behind the player, and the generating range is 20 units. Called by render() in MainGameScreen.java.
     * <p>
     * For example, the first call to the player x position is 0, and the x range for generating
     * obstacles is 0-30.  The second call to the player's x position is 10, and the x range for
     * generating obstacles is 31-50.
     * <p>
     * Note: Reduce the range of obstacles generated for the first time, leaving enough space for the item group
     */
    public void spawnObstacles() {
        GridPoint2 minPos;
        GridPoint2 maxPos;
        GridPoint2 randomPos;
        GridPoint2 randomPos2;
        // Record the coordinates of all obstacles, Record the coordinates of all obstacles to prevent obstacles
        // from being generated at the same location
        ArrayList<GridPoint2> randomPoints = new ArrayList<GridPoint2>();

        String loggerInfo = "";

        int playerX = (int) player.getPosition().x;
        logger.debug("player x coordinate: {}", playerX);

        if (firstGenerate) {
            firstGenerate = false;
        } else {
            minPos = new GridPoint2(playerX + 21, 0);
            maxPos = new GridPoint2(playerX + 40, 0);
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
                loggerInfo += "Create Plants Obstacle at " + randomPos + "\t";
                loggerInfo += "Create Thorns Obstacle at " + randomPos2 + "\t";
            }
            logger.debug("Min x: {}, Max x: {}; Total randomPoints {}; Obstacles: {}",
                    minPos.x, maxPos.x, randomPoints, loggerInfo);
        }


    }

    /**
     * Generate Flying Monkeys at random locations. Called by render() in MainGameScreen.java
     */
    public void spawnFlyingMonkey() {
        int playerX = (int) player.getPosition().x;
        GridPoint2 minPos = new GridPoint2(playerX + 21, 0);
        GridPoint2 maxPos = new GridPoint2(playerX + 40, 0);
        GridPoint2 randomPosTwo = RandomUtils.randomX(11, minPos, maxPos);
        Entity flyingMonkey = NPCFactory.createFlyingMonkey(player);
        spawnEntityAt(flyingMonkey, randomPosTwo, true, true);

        flyingMonkey.getEvents().addListener("spawnFaceWorm", this::spawnFaceWorm);

        logger.debug("Spawn a flying monkey on position = {}", randomPosTwo);
    }

    /**
     * Generate Face Worm at current Flying Monkeys location. Called by render() in MainGameScreen.java
     *
     * @param position the location of flying monkeys
     */
    public void spawnFaceWorm(Vector2 position) {
        Entity ghost = NPCFactory.createFaceWorm(player);
        spawnEntityAt(ghost, position, false, false);
        logger.debug("Spawn a face worm on position = {}", position);
    }


    /**
     * Generate a certain number of meteorites, called by render() in MainGameScreen.java. The final number of
     * meteorites is the sum of all parameters.
     * <p>
     * Big size meteorites: 1.5 - 2 times of the multiples of meteorites texture,
     * total number is bigNum(+bigRandomRange), the values in parentheses are random.
     * Midden size meteorites: 1 - 1.5 times of the multiples of meteorites texture,
     * total number is middleNum(+midRandomRange)
     * Small size meteorites: 0.5 + randomSize: 0.5 - 1 times of the multiples of meteorites texture,
     * total number is smallNum(+smallRandomRange)
     * <p>
     * e.g. 1(+2) means that the number of generations is at least 1, and the final possible range is 1-3.
     *
     * @param bigNum           At least the number of large meteorites generated.
     * @param middleNum        At least the number of middle meteorites generated.
     * @param smallNum         At least the number of small meteorites generated.
     * @param bigRandomRange   The number of large meteorites that may be randomly generated.
     * @param midRandomRange   The number of middle meteorites that may be randomly generated.
     * @param smallRandomRange The number of small meteorites that may be randomly generated.
     */
    public void spawnMeteorites(int bigNum, int middleNum, int smallNum, int bigRandomRange, int midRandomRange,
                                int smallRandomRange) {
        int bigNumRandom = bigNum + (int) (Math.random() * (bigRandomRange + 1));
        int midNumRandom = middleNum + (int) (Math.random() * (midRandomRange + 1));
        int smallNumRandom = smallNum + (int) (Math.random() * (smallRandomRange + 1));
        int meteoritesNum = bigNumRandom + midNumRandom + smallNumRandom;

        double randomSize; // Generate random range for size
        double bigSize;
        double midSize;
        double smallSize;

        String loggerInfo = "";

        for (int i = 0; i < meteoritesNum; i++) {
            randomSize = Math.random() * 0.5; // 0-0.5
            bigSize = 1.5 + randomSize; // 1.5 - 2 size of the meteorites
            midSize = 1 + randomSize; // 1 - 1.5 size of the meteorites
            smallSize = 0.5 + randomSize; // 0.5 - 1 size of the meteorites
            int x = (int) (player.getPosition().x + 5 + Math.random() * 5);
            int y = (int) (20 + Math.random() * 2);
            GridPoint2 point = new GridPoint2(x, y);

            Entity stone;

            if (i < bigNumRandom) { // must have a big meteorites
                stone = ObstacleFactory.createMeteorite(player,
                        (float) bigSize, ObstacleFactory.MeteoriteType.BigMeteorite);
                loggerInfo += "Big stone = " + point + "\t";
            } else if (i < bigNumRandom + midNumRandom) {
                stone = ObstacleFactory.createMeteorite(player,
                        (float) midSize, ObstacleFactory.MeteoriteType.MiddleMeteorite);
                loggerInfo += "Mid stone = " + point + "\t";
            } else {
                stone = ObstacleFactory.createMeteorite(player,
                        (float) smallSize, ObstacleFactory.MeteoriteType.SmallMeteorite);
                loggerInfo += "Small stone = " + point + "\t";
            }


            spawnEntityAt(stone, point, true, true);
        }
        logger.debug("bigNumRandom = {}, midNumRandom = {}, smallNumRandom = {}, stones points: {}",
                bigNumRandom, midNumRandom, smallNumRandom, loggerInfo);
    }


    /**
     * Generate Spaceship at fixed location.
     */
    public void spawnSpaceship() {
        int playerX = (int) player.getPosition().x;
        GridPoint2 position = new GridPoint2(playerX + 35, 3);
        Entity spaceship = NPCFactory.createSpaceShip(player);
        spawnEntityAt(spaceship, position, true, false);

        spaceship.getEvents().addListener("spawnPortalEntrance", this::spawnPortalEntrance);
        spaceship.getEvents().addListener("spawnSmallMissile", this::spawnSmallMissile);

        logger.debug("Spawn a spaceship on position = {}", position);
    }

    /**
     * Generate Small Missile at Spaceship location. Called by render() in MainGameScreen.java
     *
     * @param position the location of Spaceship
     */
    public void spawnSmallMissile(Vector2 position) {
        Entity smallMissile = NPCFactory.createSmallMissile(player);
        spawnEntityAt(smallMissile, position, true, true);
        smallMissile.getComponent(PhysicsComponent.class).getBody().applyLinearImpulse(new Vector2(-10, 2),
                position, true);
        smallMissile.getComponent(PhysicsComponent.class).getBody().setLinearDamping(0f);
        smallMissile.getComponent(PhysicsComponent.class).getBody().setGravityScale(0.1f);
        logger.debug("Spawn a small missile on position = {}", position);
    }


    /**
     * Generate Small Missile at Spaceship location. Called by render() in MainGameScreen.java
     *
     * @param position the location of Spaceship
     * @param type     ObstacleType category
     */
    public void spawnPortal(Vector2 position, ObstacleEventHandler.ObstacleType type) {
        Entity portal = ObstacleFactory.createPortal(player, type);
        spawnEntityAt(portal, position, true, true);
        logger.debug("Spawn a portal on position = {}", position);
    }

    /**
     * Called by the listening event "spawnPortalEntrance" of spawnSpaceship().
     *
     * @param position position the location of Spaceship
     * @param type     ObstacleType category
     */
    private void spawnPortalEntrance(Vector2 position, ObstacleEventHandler.ObstacleType type) {
        spawnPortal(position, type);
    }


    private void spawnFirstAid() {

        for (int i = 1; i < 31; i++) {
            GridPoint2 position = new GridPoint2(i * 3, 5);
            Entity firstAid = ItemFactory.createFirstAid(player, pro);
            spawnEntityAt(firstAid, position, false, false);
        }
    }

    private void spawnGold() {
        int k = 0;
        for (int i = 0; i < 20; i++) {
            GridPoint2 position = new GridPoint2(20 + k++, 40);
            Entity gold = ItemFactory.createGold(player);
            spawnEntityAt(gold, position, false, false);
        }
    }

    /**
     * Generate the first set of gold in the new map
     */
    private void spawnGoldNewMap() {
        int k = 0;
        for (int i = 0; i < 30; i++) {
            GridPoint2 position = new GridPoint2(k++, 70);
            Entity gold = ItemFactory.createGold(player);
            spawnEntityAt(gold, position, false, false);
        }
    }

    /**
     * Generate another set of gold from the given horizontal start point in the new map
     * @param x horizontal start point
     */
    public void spawnGoldNewMapRandomly(int x) {
        for (int i = 0; i < 30; i++) {
            GridPoint2 position = new GridPoint2(10 + x++, 70);
            Entity gold = ItemFactory.createGold(player);
            spawnEntityAt(gold, position, false, false);
        }
    }

    private Entity spawnPlayer() {
        Entity newPlayer = PlayerFactory.createPlayer();
        spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
        newPlayer.getEvents().addListener("useWeapon", this::spawnWeapon);
        return newPlayer;
    }

    private void spawnWeapon(Vector2 position) {
        Entity weapon = ObstacleFactory.createWeapon();

        weapon.getComponent(PhysicsComponent.class).getBody().applyLinearImpulse(new Vector2(10, 0),
                position, true);
        weapon.getComponent(PhysicsComponent.class).getBody().setLinearDamping(0.5f);
        weapon.getComponent(PhysicsComponent.class).getBody().setGravityScale(0f);

        spawnEntityAt(weapon, position, true, true);
    }

    private void setBonusItems(Entity player) {
        AchievementsBonusItems bonusItems = new AchievementsBonusItems(player);
        bonusItems.setBonusItem();
    }

    /**
     * Play the regular map bgm
     */
    public void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(BACKGROUNDMUSIC, Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    public static void playitemMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(itemSounds, Music.class);
        music.setVolume(0.3f);
        music.play();
    }

    /**
     * Play the new map bgm
     */
    public void playNewMapMusic() {
        Music newMusic = ServiceLocator.getResourceService().getAsset(NEWMAP_BACKGROUNDMUSIC, Music.class);
        newMusic.setLooping(true);
        newMusic.setVolume(0.3f);
        newMusic.play();
    }

    /**
     * Stop the regular map bgm
     */
    public void stopMusic() {
        ServiceLocator.getResourceService().getAsset(BACKGROUNDMUSIC, Music.class).stop();
    }

    /**
     * Stop the new map bgm
     */
    public void stopNewMapMusic() {
        ServiceLocator.getResourceService().getAsset(NEWMAP_BACKGROUNDMUSIC, Music.class).stop();
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
        resourceService.loadMusic(itemMusic);

        resourceService.loadMusic(newMapMusic);
        resourceService.loadTextures(mpcTextures);
        resourceService.loadTextureAtlases(mpcTexturesAtlases);

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
        resourceService.unloadAssets(itemMusic);

        resourceService.unloadAssets(newMapMusic);
        resourceService.unloadAssets(mpcTextures);
        resourceService.unloadAssets(mpcTexturesAtlases);
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().getAsset(BACKGROUNDMUSIC, Music.class).stop();
        this.unloadAssets();
    }


    /**
     * Achievements system tracking which depends on the game area's lifecycle
     */
    private void trackAchievements() {
        spawnEntity(AchievementFactory.createAchievementEntity());
    }

    private void trackBuffDescription() {
        spawnEntity(BuffDescriptionFactory.createBuffDescriptionEntity());
    }
}
