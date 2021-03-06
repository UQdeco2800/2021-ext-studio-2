package com.deco2800.game.components.settingsmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
          "images/settings/uiScale.png", "images/settings/fps.png","setting-page/fullscreen.png",
          "setting-page/vsync.png", "setting-page/fpscap.png", "setting-page/ui.png", "setting-page/resolution.png",
          "setting-page/setting.png", "setting-page/exit2.png", "setting-page/apply2.png",
          "setting-page/inMusic.png", "setting-page/overMusic.png", "setting-page/choose1.png",
          "setting-page/choose2.png"};
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
    Image title = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/setting.png", Texture.class));
    Table settingsTable = makeSettingsTable();
    Table menuBtns = makeMenuBtns();
    bgTable = new Table();
    bgTable.setFillParent(true);
    bgTable.add(img);
    bgTable.add(title);
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
    Image fpsImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/fpscap.png", Texture.class));
    fpsText = new TextField(Integer.toString(settings.fps), skin);

    Image fullscreenImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/fullscreen.png", Texture.class));
    fullScreenCheck = new CheckBox("", skin);
    fullScreenCheck.setChecked(settings.fullscreen);

    Image vsyncImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/vsync.png", Texture.class));
    vsyncCheck = new CheckBox("", skin);
    vsyncCheck.setChecked(settings.vsync);

    Image uiImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/ui.png", Texture.class));
    uiScaleSlider = new Slider(0.2f, 2f, 0.1f, false, skin);
    uiScaleSlider.setValue(settings.uiScale);
    Label uiScaleValue = new Label(String.format("%.2fx", settings.uiScale), skin);

    Image resolutionImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/resolution.png", Texture.class));
    displayModeSelect = new SelectBox<>(skin);
    Monitor selectedMonitor = Gdx.graphics.getMonitor();
    displayModeSelect.setItems(getDisplayModes(selectedMonitor));
    displayModeSelect.setSelected(getActiveMode(displayModeSelect.getItems()));

    Image inGameImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/inMusic.png", Texture.class));
    //TextButton inGameMusicSelect = new TextButton("Choose", skin);
    Button.ButtonStyle inGameMusicSelect = new Button.ButtonStyle();
    inGameMusicSelect.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/choose1.png"))));
    inGameMusicSelect.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/choose2.png"))));
    Button inGameMusicSelectBtn = new Button(inGameMusicSelect);

    inGameMusicSelectBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("in game music selection button clicked");
                entity.getEvents().trigger("openSelectionDialog", "MainGame");

              }
            });

    Image gameOverImg = new Image(ServiceLocator.getResourceService()
            .getAsset("setting-page/overMusic.png", Texture.class));
    //TextButton gameOverMusicSelect = new TextButton("Choose", skin);
    Button.ButtonStyle gameOverMusicSelect = new Button.ButtonStyle();
    gameOverMusicSelect.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/choose1.png"))));
    gameOverMusicSelect.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/choose2.png"))));
    Button gameOverMusicSelectBtn = new Button(gameOverMusicSelect);

    gameOverMusicSelectBtn.addListener(
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
    table.add(fpsImg).left().padLeft(10);
    table.add(fpsText).width(100).left();

    table.row().padTop(10f);
    table.add(getIconImage("fullScreen")).right();
    table.add(fullscreenImg).left().padLeft(10);
    table.add(fullScreenCheck).left();

    table.row().padTop(10f);
    table.add(getIconImage("vsync")).right();
    table.add(vsyncImg).left().padLeft(10);
    table.add(vsyncCheck).left();

    table.row().padTop(10f);
    Table uiScaleTable = new Table();
    uiScaleTable.add(uiScaleSlider).width(100).left();
    uiScaleTable.add(uiScaleValue).left().padLeft(5f).expandX();
    table.add(getIconImage("uiScale")).right();
    table.add(uiImg).left().padLeft(10);
    table.add(uiScaleTable).left();

    table.row().padTop(10f);
    table.add(getIconImage("resolution")).right();

    table.add(resolutionImg).left().padLeft(10);
    table.add(displayModeSelect).left();
    table.row().padTop(5f).padBottom(10);
    table.add(inGameImg).left().padLeft(10).padTop(50);
    table.add(inGameMusicSelectBtn).left().padLeft(10).padTop(50);
    table.add(gameOverImg).left().padLeft(10).padTop(50);
    table.add(gameOverMusicSelectBtn).left().padLeft(10).padTop(50);
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

    Button.ButtonStyle exit = new Button.ButtonStyle();
    exit.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/exit1.png"))));
    exit.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/exit2.png"))));
    Button exitBtn = new Button(exit);

    Button.ButtonStyle apply = new Button.ButtonStyle();
    apply.up = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/apply1.png"))));
    apply.over = new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("setting-page/apply2.png"))));
    Button applyBtn = new Button(apply);

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
