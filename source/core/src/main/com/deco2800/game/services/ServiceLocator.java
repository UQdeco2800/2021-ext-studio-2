package com.deco2800.game.services;

import com.deco2800.game.entities.EntityService;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simplified implementation of the Service Locator pattern:
 * https://martinfowler.com/articles/injection.html#UsingAServiceLocator
 *
 * <p>Allows global access to a few core game services. Warning: global access is a trap and should
 * be used extremely sparingly. Read the README for details.
 */
public class ServiceLocator {
  private static EntityService entityService;
  private static RenderService renderService;
  private static PhysicsService physicsService;
  private static GameTime timeSource;
  private static InputService inputService;
  private static ResourceService resourceService;

  private static ScoreService scoreService;
  private static DistanceService distanceService;


  public static EntityService getEntityService() {
    return entityService;
  }

  public static RenderService getRenderService() {
    return renderService;
  }

  public static PhysicsService getPhysicsService() {
    return physicsService;
  }

  public static GameTime getTimeSource() {
    return timeSource;
  }

  public static InputService getInputService() {
    return inputService;
  }

  public static ResourceService getResourceService() {
    return resourceService;
  }

  public static ScoreService getScoreService() {
    return scoreService;
  }

  public static DistanceService getDistanceService() {
    return distanceService;
  }

  public static void registerEntityService(EntityService service) {
    entityService = service;
  }

  public static void registerRenderService(RenderService service) {
    renderService = service;
  }

  public static void registerPhysicsService(PhysicsService service) {
    physicsService = service;
  }

  public static void registerTimeSource(GameTime source) {
    timeSource = source;
  }

  public static void registerInputService(InputService source) {
    inputService = source;
  }

  public static void registerResourceService(ResourceService source) {
    resourceService = source;
  }

  public static void registerScoreService(ScoreService source) {
    scoreService = source;
  }

  public static void registerDistanceService(DistanceService source) {
    distanceService = source;
  }

  public static void clear() {
    entityService = null;
    renderService = null;
    physicsService = null;
    timeSource = null;
    inputService = null;
    resourceService = null;
    scoreService = null;
    distanceService = null;
  }

  private ServiceLocator() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
