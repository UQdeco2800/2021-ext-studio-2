package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.score.ScoreDetailsDialog;
import com.deco2800.game.components.score.ScoreHistoryDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.BackgroundMusic;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryScoreScreen extends ScreenAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HistoryScoreScreen.class);
    private static final String[] historyScoreTextures =
            {"images/historyScoreBoard.png", "images/historyScoreBg.png", "images/achievements/trophyDialogSilver.png"};
    private static final String[] trophyTextures = AchievementFactory.getTrophyTextures();
    private final Renderer renderer;
    private final ScoreHistoryDisplay scoreHistoryDisplay;
    private final Entity ui;


    public HistoryScoreScreen(GdxGame game) {
        logger.debug("drawing history score board ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        loadAssets();
        Stage stage = ServiceLocator.getRenderService().getStage();
        ui = new Entity();
        scoreHistoryDisplay = new ScoreHistoryDisplay(game);
        ui.addComponent(scoreHistoryDisplay)
                .addComponent(new ScoreDetailsDialog())
                .addComponent(new BackgroundSelectionComponent("HistoryScore"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("HistoryScore"), 0.5f))
                .addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }

    @Override
    public void render(float delta) {
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.info("Resized renderer: ({} x {})", width, height);
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(historyScoreTextures);
        resourceService.loadTextures(trophyTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(trophyTextures);
        resourceService.unloadAssets(historyScoreTextures);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        unloadAssets();
        ui.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }
}
