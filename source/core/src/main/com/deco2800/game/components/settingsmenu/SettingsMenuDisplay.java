package com.deco2800.game.components.settingsmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.files.UserSettings.DisplaySettings;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.utils.StringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Settings menu display and logic. If you bork the settings, they can be changed manually in
 * DECO2800Game/settings.json under your home directory (This is C:/users/[username] on Windows).
 */
public class SettingsMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
  private final GdxGame game;
  private final String[] textures = {"setting-page/exit1.png", "setting-page/apply1.png",
          "images/settings/settingsBackground2.png", "images/settings/fullScreen.png",
          "images/settings/fullScreenOff.png", "images/settings/resolution.png", "images/settings/vsync.png",
          "images/settings/uiScale.png", "images/settings/fps.png",};
  private Table rootTable;
  private Table bgTable;
  private TextField fpsText;
  private CheckBox fullScreenCheck;
  private CheckBox vsyncCheck;
  private Slider uiScaleSlider;
  private SelectBox<StringDecorator<DisplayMode>> displayModeSelect;

  public SettingsMenuDisplay(GdxGame game) {
    super();
    this.game = game;
  }

  @Override
  public void create() {
    super.create();
    loadAssets();
    addActors();

  }

  private void loadAssets() {
    ServiceLocator.getResourceService().loadTextures(textures);
    ServiceLocator.getResourceService().loadAll();
  }

  private void addActors() {
    Image img = new Image(ServiceLocator.getResourceService()
            .getAsset("images/settings/settingsBackground2.png", Texture.class));
    Label title = new Label("Settings", skin, "title");
    Table settingsTable = makeSettingsTable();
    Table menuBtns = makeMenuBtns();
    bgTable = new Table();
    bgTable.setFillParent(true);
    bgTable.add(img);
    rootTable = new Table();
    rootTable.setFillParent(true);

    rootTable.add(title).expandX().top().padTop(20f);

    rootTable.row().padTop(30f);
    rootTable.add(settingsTable).expandX().expandY();

    rootTable.row();
    rootTable.add(menuBtns).fillX();
    stage.addActor(bgTable);
    stage.addActor(rootTable);
  }

  private Table makeSettingsTable() {
    // Get current values
    UserSettings.Settings settings = UserSettings.get();

    // Create components
    Label fpsLabel = new Label("FPS Cap:", skin);
    fpsText = new TextField(Integer.toString(settings.fps), skin);

    Label fullScreenLabel = new Label("Fullscreen:", skin);
    fullScreenCheck = new CheckBox("", skin);
    fullScreenCheck.setChecked(settings.fullscreen);

    Label vsyncLabel = new Label("VSync:", skin);
    vsyncCheck = new CheckBox("", skin);
    vsyncCheck.setChecked(settings.vsync);

    Label uiScaleLabel = new Label("ui Scale (Unused):", skin);
    uiScaleSlider = new Slider(0.2f, 2f, 0.1f, false, skin);
    uiScaleSlider.setValue(settings.uiScale);
    Label uiScaleValue = new Label(String.format("%.2fx", settings.uiScale), skin);

    Label displayModeLabel = new Label("Resolution:", skin);
    displayModeSelect = new SelectBox<>(skin);
    Monitor selectedMonitor = Gdx.graphics.getMonitor();
    displayModeSelect.setItems(getDisplayModes(selectedMonitor));
    displayModeSelect.setSelected(getActiveMode(displayModeSelect.getItems()));

    Label inGameLabel = new Label("In Game Music:", skin);
    TextButton inGameMusicSelect = new TextButton("Choose", skin);
    inGameMusicSelect.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("in game music selection button clicked");
                entity.getEvents().trigger("openSelectionDialog", "MainGame");

              }
            });

    Label gameOverLabel = new Label("Game Over Music:", skin);
    TextButton gameOverMusicSelect = new TextButton("Choose", skin);
    gameOverMusicSelect.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("in game music selection button clicked");
                entity.getEvents().trigger("openSelectionDialog", "GameOver");
              }
            });

    // Position Components on table
    Table table = new Table();
    table.add(getIconImage("fps")).right();
    table.add(fpsLabel).left().padLeft(10);
    table.add(fpsText).width(100).left();

    table.row().padTop(10f);
    table.add(getIconImage("fullScreen")).right();
    table.add(fullScreenLabel).left().padLeft(10);
    table.add(fullScreenCheck).left();

    table.row().padTop(10f);
    table.add(getIconImage("vsync")).right();
    table.add(vsyncLabel).left().padLeft(10);
    table.add(vsyncCheck).left();

    table.row().padTop(10f);
    Table uiScaleTable = new Table();
    uiScaleTable.add(uiScaleSlider).width(100).left();
    uiScaleTable.add(uiScaleValue).left().padLeft(5f).expandX();
    table.add(getIconImage("uiScale")).right();
    table.add(uiScaleLabel).left().padLeft(10);
    table.add(uiScaleTable).left();

    table.row().padTop(10f);
    table.add(getIconImage("resolution")).right();

    table.add(displayModeLabel).left().padLeft(10);
    table.add(displayModeSelect).left();
    table.row().padTop(5f);
    table.add(inGameLabel).left().padLeft(10);
    table.add(inGameMusicSelect).left();
    table.add(gameOverLabel).left().padLeft(10);
    table.add(gameOverMusicSelect).left();
    // Events on inputs
    uiScaleSlider.addListener(
            (Event event) -> {
              float value = uiScaleSlider.getValue();
              uiScaleValue.setText(String.format("%.2fx", value));
              return true;
            });

    return table;
  }

  private Image getIconImage(String icon) {
    return new Image(ServiceLocator.getResourceService()
            .getAsset("images/settings/" + icon + ".png", Texture.class));
  }

  private StringDecorator<DisplayMode> getActiveMode(Array<StringDecorator<DisplayMode>> modes) {
    DisplayMode active = Gdx.graphics.getDisplayMode();

    for (StringDecorator<DisplayMode> stringMode : modes) {
      DisplayMode mode = stringMode.object;
      if (active.width == mode.width
              && active.height == mode.height
              && active.refreshRate == mode.refreshRate) {
        return stringMode;
      }
    }
    return null;
  }

  private Array<StringDecorator<DisplayMode>> getDisplayModes(Monitor monitor) {
    DisplayMode[] displayModes = Gdx.graphics.getDisplayModes(monitor);
    Array<StringDecorator<DisplayMode>> arr = new Array<>();

    for (DisplayMode displayMode : displayModes) {
      arr.add(new StringDecorator<>(displayMode, this::prettyPrint));
    }

    return arr;
  }

  private String prettyPrint(DisplayMode displayMode) {
    return displayMode.width + "x" + displayMode.height + ", " + displayMode.refreshRate + "hz";
  }

  private Table makeMenuBtns() {
    ImageButton exitBtn = new ImageButton(new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/exit1.png", Texture.class)).getDrawable());
    ImageButton applyBtn = new ImageButton(new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/apply1.png", Texture.class)).getDrawable());

    exitBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                exitMenu();
              }
            });

    applyBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                applyChanges();
              }
            });

    Table table = new Table();
    table.add(exitBtn).expandX().left().pad(0f, 15f, 15f, 0f);
    table.add(applyBtn).expandX().right().pad(0f, 0f, 15f, 15f);
    return table;
  }

  private void applyChanges() {
    UserSettings.Settings settings = UserSettings.get();

    Integer fpsVal = parseOrNull(fpsText.getText());
    if (fpsVal != null) {
      settings.fps = fpsVal;
    }
    settings.fullscreen = fullScreenCheck.isChecked();
    settings.uiScale = uiScaleSlider.getValue();
    settings.displayMode = new DisplaySettings(displayModeSelect.getSelected().object);
    settings.vsync = vsyncCheck.isChecked();

    UserSettings.set(settings, true);
  }

  private void exitMenu() {
    game.setScreen(ScreenType.MAIN_MENU);
  }

  private Integer parseOrNull(String num) {
    try {
      return Integer.parseInt(num, 10);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  protected void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public void update() {
    stage.act(ServiceLocator.getTimeSource().getDeltaTime());
  }

  @Override
  public void dispose() {
    rootTable.clear();
    super.dispose();
  }
}
