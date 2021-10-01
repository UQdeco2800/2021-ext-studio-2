package com.deco2800.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.player.UnlockedAttiresDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.GameRecords;
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

/**
 * Screen displaying unlocked attires
 */
public class UnlockedAttiresScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UnlockedAttiresScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    private Entity UIEntity;
    private static final String[] playerAttires = {"images/mpc/veteranSilver.png", "images/mpc/veteranGold.png"};
    private static final String[] backgroundImages = {"images/achievements/achievementBackground.png"};
    private static final String[] achievements = {"images/achievements/veteranSilverTrophy.png", "images/achievements/veteranGoldTrophy.png"};
    private static final String[] mainMenuTextures = {"images/box_boy_title.png","images/menu_background/menu_background.png"};


    public UnlockedAttiresScreen(GdxGame game) {
        this.game = game;


        logger.debug("Initialising Unlocked Attires screen services");
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
        resourceService.loadTextures(playerAttires);
        resourceService.loadTextures(backgroundImages);
        resourceService.loadTextures(achievements);
        resourceService.loadTextures(mainMenuTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(playerAttires);
        resourceService.unloadAssets(backgroundImages);
        resourceService.unloadAssets(achievements);
        resourceService.unloadAssets(mainMenuTextures);
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

        UIEntity.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }

    private void createUI() {
        logger.debug("Creating Unlocked Attires screen UI");
        List<BaseAchievementConfig> bestAchievements= GameRecords.getAllTimeBestAchievements();
        Stage stage = ServiceLocator.getRenderService().getStage();

        UIEntity = new Entity();

        UIEntity.addComponent(new UnlockedAttiresDisplay(game, bestAchievements))
                .addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(UIEntity);
    }


}
