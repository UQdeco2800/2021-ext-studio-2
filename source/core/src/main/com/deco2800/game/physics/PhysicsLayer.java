package com.deco2800.game.physics;

public class PhysicsLayer {
  public static final short NONE = 0;
  public static final short DEFAULT = (1 << 0); // 1
  public static final short PLAYER = (1 << 1); // 2

  // Terrain obstacle, e.g. trees
  public static final short OBSTACLE = (1 << 2); // 4
  // NPC (Non-Playable Character) colliders
  public static final short NPC = (1 << 3); // 8
  public static final short METEORITE = (1 << 4); // 16
  public static final short WALL = (1 << 5); // 32
  public static final short CEILING = (1 << 6);
  public static final short PLAYERCOLLIDER = (1 << 7);
  public static final short WEAPON = (1 << 8);
  public static final short ALL = ~0;

  public static boolean contains(short filterBits, short layer) {
    return (filterBits & layer) != 0;
  }

  private PhysicsLayer() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
