package com.deco2800.game.areas.terrain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.areas.terrain.TerrainComponent.TerrainOrientation;
import com.deco2800.game.components.CameraComponent;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import java.util.Random;

/** Factory for creating game terrains. */
public class TerrainFactory {

  private static final GridPoint2 MAP_SIZE = new GridPoint2(22, 3);



  private final OrthographicCamera camera;
  private final TerrainOrientation orientation;

  public GridPoint2 getMapSize() {
    return MAP_SIZE;
  }


  /**
   * Create a terrain factory with Orthogonal orientation
   *
   * @param cameraComponent Camera to render terrains to. Must be ortographic.
   */
  public TerrainFactory(CameraComponent cameraComponent) {
    this(cameraComponent, TerrainOrientation.ORTHOGONAL);
  }

  /**
   * Create a terrain factory
   *
   * @param cameraComponent Camera to render terrains to. Must be orthographic.
   * @param orientation orientation to render terrain at
   */
  public TerrainFactory(CameraComponent cameraComponent, TerrainOrientation orientation) {
    this.camera = (OrthographicCamera) cameraComponent.getCamera();
    this.orientation = orientation;
  }

  /**
   * Create a terrain of the given type, using the orientation of the factory. This can be extended
   * to add additional game terrains.
   *
   * @param terrainType Terrain to create
   * @return Terrain component which renders the terrain
   */
  public TerrainComponent createTerrain(TerrainType terrainType) {
    ResourceService resourceService = ServiceLocator.getResourceService();
    switch (terrainType) {
      case FOREST_DEMO:
        TextureRegion orthoRoad =
            new TextureRegion(resourceService.getAsset("images/road.png", Texture.class));

        return createForestDemoTerrain(1, orthoRoad);

      default:
        return null;
    }
  }

  private TerrainComponent createForestDemoTerrain(
      float tileWorldSize, TextureRegion road) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createForestDemoTiles(tilePixelSize, road);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TiledMapRenderer createRenderer(TiledMap tiledMap, float tileScale) {
    switch (orientation) {
      case ORTHOGONAL:
        return new OrthogonalTiledMapRenderer(tiledMap, tileScale);
      case ISOMETRIC:
        return new IsometricTiledMapRenderer(tiledMap, tileScale);
      case HEXAGONAL:
        return new HexagonalTiledMapRenderer(tiledMap, tileScale);
      default:
        return null;
    }
  }

  private TiledMap createForestDemoTiles(
      GridPoint2 tileSize, TextureRegion road) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // Create base grass
    fillTiles(layer, MAP_SIZE, grassTile);

    // Add some grass and rocks
    // fillTilesAtRandom(layer, MAP_SIZE, grassTuftTile, TUFT_TILE_COUNT);

    tiledMap.getLayers().add(layer);
    return tiledMap;
  }
/*
  private static void fillTilesAtRandom(
      TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int amount) {
    GridPoint2 min = new GridPoint2(0, 0);
    GridPoint2 max = new GridPoint2(mapSize.x - 1, mapSize.y - 1);

    for (int i = 0; i < amount; i++) {
      GridPoint2 tilePos = RandomUtils.random(min, max);
      Cell cell = layer.getCell(tilePos.x, tilePos.y);
      cell.setTile(tile);
    }
  }*/

  private static void fillTiles(TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile) {
    for (int x = 0; x < mapSize.x; x++) {
      for (int y = 1; y < mapSize.y; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
      }
    }
  }



  public TerrainComponent createTerrainRandomly(TerrainType terrainType, int xValue) {
    MAP_SIZE.set(MAP_SIZE.x+20, MAP_SIZE.y);
    ResourceService resourceService = ServiceLocator.getResourceService();
    switch (terrainType) {
      case FOREST_DEMO:
        TextureRegion orthoRoad =
                new TextureRegion(resourceService.getAsset("images/road.png", Texture.class));
        TextureRegion orthoWater =
                new TextureRegion(resourceService.getAsset("images/water.png", Texture.class));
        return createForestDemoTerrainRandomly(1, orthoRoad, orthoWater, xValue);
      default:
        return null;
    }
  }

  private TerrainComponent createForestDemoTerrainRandomly(
          float tileWorldSize, TextureRegion road, TextureRegion water, int xValue) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createForestDemoTilesRandomly(tilePixelSize, road, water, xValue);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TiledMap createForestDemoTilesRandomly(
          GridPoint2 tileSize, TextureRegion road, TextureRegion water, int xValue) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TerrainTile waterTile = new TerrainTile(water);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // Create base grass
    fillTilesRandomly(layer, MAP_SIZE, grassTile, xValue);

    // Add water
    fillTilesAtRandom(layer, MAP_SIZE, waterTile, 5, xValue);

    // Add some grass and rocks
    // fillTilesAtRandom(layer, MAP_SIZE, grassTuftTile, TUFT_TILE_COUNT);

    tiledMap.getLayers().add(layer);
    return tiledMap;
  }

  private static void fillTilesAtRandom(
          TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int amount, int xValue) {
    GridPoint2 min = new GridPoint2(xValue+10, 2);
    GridPoint2 max = new GridPoint2(mapSize.x - 1, mapSize.y - 1);

    for (int i = 0; i < amount; i++) {
      GridPoint2 tilePos = RandomUtils.random(min, max);
      Cell cell = layer.getCell(tilePos.x, tilePos.y);
      cell.setTile(tile);
    }
  }

  private static void fillTilesRandomly(TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int xValue) {
    Random rand = new Random();
    int index = rand.nextInt(1);

    if(index == 0){
      for (int x = xValue+10; x < mapSize.x; x++) {
        for (int y = 1; y < mapSize.y; y++) {
          Cell cell = new Cell();
          cell.setTile(tile);
          layer.setCell(x, y, cell);
        }
      }
    }
  }




  /**
   * This enum should contain the different terrains in your game, e.g. forest, cave, home, all with
   * the same oerientation. But for demonstration purposes, the base code has the same level in 3
   * different orientations.
   */
  public enum TerrainType {
    FOREST_DEMO,
    FOREST_DEMO_ISO,
    FOREST_DEMO_HEX
  }
}
