package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.obstacle.MonsterDetails;
import com.deco2800.game.components.obstacle.MonsterDispay;
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

public class MonsterMenuScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private final Renderer renderer;
    private final MonsterDispay monsterDispay ;
    private static final String[] historyScoreTextures =
            {"images/monster_menu/Monsterbox.jpg",
                    "images/monster_menu/background.png",
            "images/obstacle_1_new.png",
             "images/obstacle2_vision2.png",
                    "images/stone1.png",
                    "images/monkey_original.png",
                    "images/Facehugger.png",
                    "images/monster_menu/secret.jpg"



            };

    public MonsterMenuScreen(GdxGame game) {
        logger.debug("drawing monster menu ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        loadAssets();
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        monsterDispay = new MonsterDispay(game);
        ui.addComponent(monsterDispay).addComponent(new InputDecorator(stage, 10))
                .addComponent(new MonsterDetails());
        ServiceLocator.getEntityService().register(ui);
    }

    private void loadAssets() {

        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(historyScoreTextures);
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
        resourceService.unloadAssets(historyScoreTextures);
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
