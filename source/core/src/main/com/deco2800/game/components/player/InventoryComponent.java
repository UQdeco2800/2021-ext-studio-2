package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.achievements.AchievementsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A component intended to be used by the player to track their inventory.
 *
 * Currently only stores the gold amount but can be extended for more advanced functionality such as storing items.
 * Can also be used as a more generic component for other entities.
 */
public class InventoryComponent extends Component {
  private int gold;

  public InventoryComponent(int gold) {
    setGold(gold);
  }

  @Override
  public void create() {
    super.create();

    AchievementsHelper.getInstance().getEvents().addListener(AchievementsHelper.EVENT_ADD_BONUS_GOLD, this::addToGold);
  }

  /**
   * Returns the player's gold.
   *
   * @return entity's health
   */
  public int getGold() {
    return this.gold;
  }

  /**
   * Returns if the player has a certain amount of gold.
   * @param gold required amount of gold
   * @return player has greater than or equal to the required amount of gold
   */
  public Boolean hasGold(int gold) {
    return this.gold >= gold;
  }

  /**
   * Sets the player's gold. Gold has a minimum bound of 0.
   *
   * @param gold gold
   */
  public void setGold(int gold) {
    if (gold >= 0) {
      this.gold = gold;
    } else {
      this.gold = 0;
    }

    if(entity != null) {
      entity.getEvents().trigger("updateGold", this.gold);
    }
  }

  /**
   * Add to the player's existing gold
   *
   * @param amount gold to be added
   */
  public void addToGold(int amount){
    setGold(getGold() + amount);
  }

  /**
   * Adds to the player's gold. The amount added can be negative.
   * @param gold gold to add
   */
  public void addGold(int gold) {
    setGold(this.gold + gold);
  }
}
