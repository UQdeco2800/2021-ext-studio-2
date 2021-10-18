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
        return createMudRoadTerrain(mudRoad);
      case ROCK_ROAD:
        TextureRegion rockRoad =
                new TextureRegion(resourceService.getAsset("images/terrain2.png", Texture.class));
        return createRockRoadTerrain(rockRoad);
      default:
        return null;
    }
  }

  /**
   * Create a terrain of the mud road
   * @param road textureRegion represents the mud road
   * @return a terrain of the mud road
   */
  private TerrainComponent createMudRoadTerrain(
          TextureRegion road) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createMudRoadTiles(tilePixelSize, road);
    TiledMapRenderer renderer = createRenderer(tiledMap, (float) 1 / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation,  1);
  }

  /**
   * Create a terrain of the rock road
   * @param road textureRegion represents the rock road
   * @return a terrain of the rock road
   */
  private TerrainComponent createRockRoadTerrain(
          TextureRegion road) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createRockRoadTiles(tilePixelSize, road);
    TiledMapRenderer renderer = createRenderer(tiledMap, (float) 1 / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation,  1);
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
    fillTiles(layer, grassTile, 1);

    tiledMap.getLayers().add(layer);
    return tiledMap;
  }

  private TiledMap createRockRoadTiles(
          GridPoint2 tileSize, TextureRegion road) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer newLayer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y + 47, tileSize.x, tileSize.y);
    fillTiles(newLayer, grassTile, 49);
    tiledMap.getLayers().add(newLayer);
    return tiledMap;
  }

  private static void fillTiles(TiledMapTileLayer layer, TerrainTile tile, int vertical) {
    for (int x = 0; x < TerrainFactory.MAP_SIZE.x; x++) {
      for (int y = vertical; y < TerrainFactory.MAP_SIZE.y + vertical - 1; y++) {
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
        return createMudRoadTerrainRandomly(mudRoad, xValue);
      case ROCK_ROAD:
        TextureRegion rockRoad =
                new TextureRegion(resourceService.getAsset("images/terrain2.png", Texture.class));
        return createRockRoadTerrainRandomly(rockRoad, xValue);
      default:
        return null;
    }
  }

  private TerrainComponent createMudRoadTerrainRandomly(
          TextureRegion road, int xValue) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createMudRoadTilesRandomly(tilePixelSize, road, xValue);
    TiledMapRenderer renderer = createRenderer(tiledMap, (float) 1 / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation,  1);
  }

  private TerrainComponent createRockRoadTerrainRandomly(
          TextureRegion road, int xValue) {
    GridPoint2 tilePixelSize = new GridPoint2(road.getRegionWidth(), road.getRegionHeight());
    TiledMap tiledMap = createRockRoadTilesRandomly(tilePixelSize, road, xValue);
    TiledMapRenderer renderer = createRenderer(tiledMap, (float) 1 / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation,  1);
  }

  private TiledMap createMudRoadTilesRandomly(
          GridPoint2 tileSize, TextureRegion road, int xValue) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // Create base grass
    fillTilesRandomly(layer, grassTile, xValue, 1);

    tiledMap.getLayers().add(layer);
    return tiledMap;
  }

  private TiledMap createRockRoadTilesRandomly(
          GridPoint2 tileSize, TextureRegion road, int xValue) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(road);
    TiledMapTileLayer newLayer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y + 47, tileSize.x, tileSize.y);

    // Create base grass
    fillTilesRandomly(newLayer, grassTile, xValue, 49);

    tiledMap.getLayers().add(newLayer);
    return tiledMap;
  }

  private static void fillTilesRandomly(TiledMapTileLayer layer, TerrainTile tile, int xValue, int vertical) {

    for (int x = xValue+10; x < TerrainFactory.MAP_SIZE.x; x++) {
      for (int y = vertical; y < TerrainFactory.MAP_SIZE.y + vertical - 1; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
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
