package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.items.PropStoreDisplay;
import com.deco2800.game.components.items.PropStoreGoldDisplay;
import com.deco2800.game.components.items.PropStoreItemDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.PropStoreFactory;
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


/**
 * The game screen containing the props shop.
 */
public class PropsShopScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] mainGameTextures = {"images/heart.png", "images/Items/props/add_food.png", "images/Items/props/add_health.png", "images/Items/props/add_water.png", "images/Items/props/shield.png", "images/achievements/crossButton.png", "images/Items/props/background_image_prop.png"};
    private static final String[] itemTextures = PropStoreFactory.getPropTextures();
    private final Entity ui;
    private final Renderer renderer;

    public PropsShopScreen(GdxGame game) {
        logger.debug("drawing props shop ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        Stage stage = ServiceLocator.getRenderService().getStage();
        loadAssets();

        ui = new Entity();
        PropStoreDisplay propStoreDisplay = new PropStoreDisplay(game);
        ui.addComponent(new PropStoreItemDisplay());
        ui.addComponent(new PropStoreGoldDisplay());
        ui.addComponent(propStoreDisplay).addComponent(new InputDecorator(stage, 10));
        ui.addComponent(new BackgroundSelectionComponent("PropsShop", "br"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("PropsShop"), 0.5f));
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
        resourceService.loadTextures(mainGameTextures);
        resourceService.loadTextures(itemTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(mainGameTextures);
        resourceService.loadTextures(itemTextures);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        ui.dispose();
        unloadAssets();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }
}
