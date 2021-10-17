package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.buff.InstructionsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.meta.BackgroundMusic;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstructionsScreen extends ScreenAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] InstructionTextures =
            {"images/TutorialScreen1.png"};
    private final InstructionsDisplay instructionsDisplay;
    private final Renderer renderer;
    private final Entity ui;

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
        ui
                .addComponent(instructionsDisplay)
                .addComponent(new BackgroundSelectionComponent("Instructions", "br"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("Instructions"), 0.5f))
                .addComponent(new InputDecorator(stage, 10));

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
