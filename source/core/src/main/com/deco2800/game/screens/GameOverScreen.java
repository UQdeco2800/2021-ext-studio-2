package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.gameover.GameOverDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
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

public class GameOverScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GameOverScreen.class);
    private static final String[] gameOverTextures = {"images/gameOver.png", "images/background.png"};
    private final Renderer renderer;
    private final GameOverDisplay gomd;
    private final Entity ui;

    public GameOverScreen(GdxGame game) {
        logger.debug("drawing game over ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        Stage stage = ServiceLocator.getRenderService().getStage();
        ui = new Entity();
        loadAssets();
        gomd = new GameOverDisplay(game);
        ui.addComponent(new BackgroundSelectionComponent("GameOver"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("GameOver"), 0.5f))
                .addComponent(gomd).addComponent(new InputDecorator(stage, 10));

        ServiceLocator.getEntityService().register(ui);


    }

    public void setPoints(double points) {
        this.gomd.setPoints(points);
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

    @Override
    public void dispose() {
        renderer.dispose();
        ui.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(gameOverTextures);
        ServiceLocator.getResourceService().loadAll();
    }

}