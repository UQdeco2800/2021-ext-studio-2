package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.achievements.screen.AchievementRecordsDisplay;
import com.deco2800.game.components.achievements.screen.ChapterDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.AchievementRecords;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AchievementsScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AchievementsScreen.class);
    private static final String[] achievementTextures = AchievementFactory.getTextures();
    private static final String[] backgroundImages = {"images/achievements/achievementBackground.png", "images/story/chapterDialog.png"};
    private static final String chapterPath = "images/story/chapter";
    private final GdxGame game;
    private final Renderer renderer;

    public AchievementsScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising achievements screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerTimeSource(new GameTime());

        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(5f, 5f);

        loadAssets();
        createUI();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(achievementTextures);
        resourceService.loadTextures(backgroundImages);
        for (int i = 1; i < 6; i++) {
            resourceService.loadTextures(new String[]{chapterPath + i + ".png"});
        }
        resourceService.loadTextures(new String[]{chapterPath + "Link.png"});
        resourceService.loadTextures(new String[]{chapterPath + "Lock.png"});

        resourceService.loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(achievementTextures);
        resourceService.unloadAssets(backgroundImages);
        for (int i = 1; i < 6; i++) {
            resourceService.unloadAssets(new String[]{chapterPath + i + ".png"});
        }
        resourceService.unloadAssets(new String[]{chapterPath + "Link.png"});
        resourceService.unloadAssets(new String[]{chapterPath + "Lock.png"});
    }

    @Override
    public void render(float delta) {
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        unloadAssets();

        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();

        ServiceLocator.clear();
    }

    private void createUI() {
        logger.debug("Creating achievement screen ui");

        List<BaseAchievementConfig> bestAchievements = AchievementRecords.getBestRecords();
        Stage stage = ServiceLocator.getRenderService().getStage();

        Entity ui = new Entity();
        ui.addComponent(new AchievementRecordsDisplay(game, bestAchievements))
                .addComponent(new ChapterDisplay())
                .addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);
    }


}
