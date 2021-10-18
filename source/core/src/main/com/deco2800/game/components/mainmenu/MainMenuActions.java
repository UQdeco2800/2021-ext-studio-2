package com.deco2800.game.components.mainmenu;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class MainMenuActions extends Component {
  private final GdxGame game;

  public MainMenuActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("start", this::onStart);
    entity.getEvents().addListener("load", this::onLoad);
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("settings", this::onSettings);
    entity.getEvents().addListener("gameOver", this::onGameOver);
    //Team History Score board
    entity.getEvents().addListener("displayPropsShop", this::onDisplayPropsShop);
    entity.getEvents().addListener("displayHistoryScores", this::onDisplayHistoryScores);
    entity.getEvents().addListener("achievements", this::onAchievements);
    entity.getEvents().addListener("unlockedAttires", this::onUnlockedAttires);
    entity.getEvents().addListener("monsterMenu", this::onDisplayMonsterMenu);
    entity.getEvents().addListener("buffMenu", this::onDisplayBuffManualMenu);
    entity.getEvents().addListener("GameTutorial", this::onDisplayInstructions);

  }

  /**
   * Swaps to the Main Game screen.
   */
  private void onStart() {
    game.setScreen(GdxGame.ScreenType.MAIN_GAME);
  }

  /**
   * Intended for loading a saved game state.
   * Load functionality is not actually implemented.
   */
  private void onLoad() {
  }

  /**
   * Exits the game.
   */
  private void onExit() {
    game.exit();
  }

  /**
   * Swaps to the Settings screen.
   */
  private void onSettings() {
    game.setScreen(GdxGame.ScreenType.SETTINGS);
  }

  /**
   * Swaps to the GameOver screen.
   */
  private void onGameOver() {
    game.setScreen(GdxGame.ScreenType.GAME_OVER);
  }

  private void onDisplayPropsShop() {
    game.setScreen(GdxGame.ScreenType.PROPS_SHOP);
  }

  private void onDisplayHistoryScores() {
    game.setScreen(GdxGame.ScreenType.HISTORY_SCORES);
  }

  private void onAchievements(){
    game.setScreen(GdxGame.ScreenType.ACHIEVEMENTS);
  }

  private void onUnlockedAttires(){
    game.setScreen(GdxGame.ScreenType.UNLOCKED_ATTIRES);
  }

  private void onDisplayMonsterMenu() {
    game.setScreen(GdxGame.ScreenType.MONSTER_MENU);
  }

  private void onDisplayBuffManualMenu() {
    game.setScreen(GdxGame.ScreenType.BUFF_MENU);
  }

  private void onDisplayInstructions() {
    System.out.println("check 1");
    game.setScreen(GdxGame.ScreenType.INSTRUCTIONS);
  }

}