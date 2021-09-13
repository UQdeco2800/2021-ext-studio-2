package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.items.PropsShopDisplay;
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


/**
 * The game screen containing the props shop.
 */
public class PropsShopScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private final Renderer renderer;
    private final PropsShopDisplay propsShopDisplay;
    private static final String[] mainGameTextures = {"images/heart.png","images/Items/props/add_food.png","images/Items/props/add_health.png","images/Items/props/add_water.png","images/Items/props/shield.png"};

    public PropsShopScreen(GdxGame game){
        logger.debug("drawing props shop ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        Stage stage = ServiceLocator.getRenderService().getStage();
        loadAssets();
        Entity ui = new Entity();
        propsShopDisplay = new PropsShopDisplay(game);
        ui.addComponent(propsShopDisplay).addComponent(new InputDecorator(stage, 10));
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
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(mainGameTextures);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        unloadAssets();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }
}
