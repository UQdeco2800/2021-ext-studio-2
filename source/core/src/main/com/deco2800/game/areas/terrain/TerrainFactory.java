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
      case MUD_ROAD:
        TextureRegion mudRoad =
            new TextureRegion(resourceService.getAsset("images/road.png", Texture.class));
        return createMudRoadTerrain(1, mudRoad);
      case ROCK_ROAD:
        TextureRegion rockRoad =
                new TextureRegion(resourceService.getAsset("images/terrain2.png", Texture.class));
        return createRockRoadTerrain(1, rockRoad);
      default:
        return null;
    }
  }

  /**
   * Create a terrain of the mud road
   * @param tileWorldSize size of one tile
   * @param road textureRegion represents the mud road
   * @return a terrain of the mud road
   */
  private TerrainComponent createMudRoadTerrain(
      float tileWorldSize, TextureRegion road) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createMudRoadTiles(tilePixelSize, road);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  /**
   * Create a terrain of the rock road
   * @param tileWorldSize size of one tile
   * @param road textureRegion represents the rock road
   * @return a terrain of the rock road
   */
  private TerrainComponent createRockRoadTerrain(
          float tileWorldSize, TextureRegion road) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createRockRoadTiles(tilePixelSize, road);
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

  private TiledMap createMudRoadTiles(
      GridPoint2 tileSize, TextureRegion road) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // Create base mud road
    fillTiles(layer, MAP_SIZE, grassTile, 1);

    tiledMap.getLayers().add(layer);
    //tiledMap.getLayers().add(newLayer);
    return tiledMap;
  }

  private TiledMap createRockRoadTiles(
          GridPoint2 tileSize, TextureRegion road) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer newLayer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y + 47, tileSize.x, tileSize.y);
    fillTiles(newLayer, MAP_SIZE, grassTile, 49);
    tiledMap.getLayers().add(newLayer);
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

  private static void fillTiles(TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int vertical) {
    for (int x = 0; x < mapSize.x; x++) {
      for (int y = vertical; y < mapSize.y + vertical - 1; y++) {
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
      case MUD_ROAD:
        TextureRegion mudRoad =
                new TextureRegion(resourceService.getAsset("images/road.png", Texture.class));
        return createMudRoadTerrainRandomly(1, mudRoad, xValue);
      case ROCK_ROAD:
        TextureRegion rockRoad =
                new TextureRegion(resourceService.getAsset("images/terrain2.png", Texture.class));
        return createRockRoadTerrainRandomly(1, rockRoad, xValue);
      default:
        return null;
    }
  }

  private TerrainComponent createMudRoadTerrainRandomly(
          float tileWorldSize, TextureRegion road, int xValue) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createMudRoadTilesRandomly(tilePixelSize, road, xValue);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TerrainComponent createRockRoadTerrainRandomly(
          float tileWorldSize, TextureRegion road, int xValue) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createRockRoadTilesRandomly(tilePixelSize, road, xValue);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TiledMap createMudRoadTilesRandomly(
          GridPoint2 tileSize, TextureRegion road, int xValue) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);
    //TiledMapTileLayer newLayer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y + 50, tileSize.x, tileSize.y);

    // Create base grass
    fillTilesRandomly(layer, MAP_SIZE, grassTile, xValue, 1);
    //fillTilesRandomly(newLayer, MAP_SIZE, grassTile, xValue, 49);

    // Add some grass and rocks
    // fillTilesAtRandom(layer, MAP_SIZE, grassTuftTile, TUFT_TILE_COUNT);

    tiledMap.getLayers().add(layer);
    //tiledMap.getLayers().add(newLayer);
    return tiledMap;
  }

  private TiledMap createRockRoadTilesRandomly(
          GridPoint2 tileSize, TextureRegion road, int xValue) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer newLayer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y + 47, tileSize.x, tileSize.y);

    // Create base grass
    fillTilesRandomly(newLayer, MAP_SIZE, grassTile, xValue, 49);

    // Add some grass and rocks
    // fillTilesAtRandom(layer, MAP_SIZE, grassTuftTile, TUFT_TILE_COUNT);

    tiledMap.getLayers().add(newLayer);
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

  private static void fillTilesRandomly(TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int xValue, int vertical) {
    Random rand = new Random();
    int index = rand.nextInt(1);

    if(index == 0){
      for (int x = xValue+10; x < mapSize.x; x++) {
        for (int y = vertical; y < mapSize.y + vertical - 1; y++) {
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
    MUD_ROAD,
    ROCK_ROAD
  }
}
