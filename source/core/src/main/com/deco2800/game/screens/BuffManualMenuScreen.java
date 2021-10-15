package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.buff.BuffDisplay;
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

public class BuffManualMenuScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] buffTextures =
            {"buff-debuff-manual/manual-box.png",
                    "buff-debuff-manual/manual-backgorund.png",
                    "images/monster_menu/secret.jpg",
                    "buff-debuff-manual/decrease_health1.png",
                    "buff-debuff-manual/decrease_speed1.png",
                    "buff-debuff-manual/dizziness1.png",
                    "buff-debuff-manual/increase_health_limit1.png",
                    "buff-debuff-manual/increase_health1.png",
                    "buff-debuff-manual/low_statu_hunger1.png",
                    "buff-debuff-manual/low_statu_thirst1.png",
                    "buff-debuff-manual/poisoning1.png",
                    "buff-debuff-manual/recovery.png",
            };
    private final Renderer renderer;
    private final BuffDisplay buffDisplay;
    private final Entity ui;

    public BuffManualMenuScreen(GdxGame game) {
        logger.debug("drawing monster menu ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        loadAssets();
        Stage stage = ServiceLocator.getRenderService().getStage();
        ui = new Entity();
        buffDisplay = new BuffDisplay(game);
        ui.addComponent(buffDisplay).addComponent(new InputDecorator(stage, 10))
                .addComponent(new BackgroundSelectionComponent("BuffManual"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("BuffManual"), 0.5f));
        ServiceLocator.getEntityService().register(ui);
    }

    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(buffTextures);
        ServiceLocator.getResourceService().loadAll();
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

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(buffTextures);
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
