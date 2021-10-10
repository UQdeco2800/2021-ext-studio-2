package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.buff.BuffDisplay;
import com.deco2800.game.components.buff.InstructionsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstructionsScreen extends ScreenAdapter {

    private InstructionsDisplay instructionsDisplay;
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private Renderer renderer;
    private static final String[] InstructionTextures =
            {"images/TutorialScreen1.png"};
    private Entity ui;
    public InstructionsScreen(GdxGame game) {
        ui = new Entity();
        instructionsDisplay = new InstructionsDisplay(game);
        logger.debug("drawing props shop ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        Stage stage = ServiceLocator.getRenderService().getStage();
        ui.addComponent(instructionsDisplay).addComponent(new InputDecorator(stage, 10))
                .addComponent(new BackgroundSoundComponent("sounds/mainmenu_bgm.mp3", 0.5f));
        loadAssets();
        ServiceLocator.getEntityService().register(ui);
    }
    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(InstructionTextures);
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
        resourceService.unloadAssets(InstructionTextures);
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
