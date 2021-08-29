package com.deco2800.game.utils.math;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RandomUtils {
  public static Vector2 random(Vector2 start, Vector2 end) {
    return new Vector2(MathUtils.random(start.x, end.x), MathUtils.random(start.y, end.y));
  }

  public static GridPoint2 random(GridPoint2 start, GridPoint2 end) {
    return new GridPoint2(MathUtils.random(start.x, end.x), MathUtils.random(start.y, end.y));
  }

  /**
   * Used to randomly generate entities at a specific height.
   * @param y The height that the entity needs to generate.
   * @param start The minimum value of x.
   * @param end The maximum value of x.
   * @return A point in a 2D grid, with integer x and y coordinates.
   */
  public static GridPoint2 randomX(int y, GridPoint2 start, GridPoint2 end) {
    return new GridPoint2(MathUtils.random(start.x, end.x), y);
  }

  private RandomUtils() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
