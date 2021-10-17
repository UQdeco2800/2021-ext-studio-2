package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.obstacle.MonsterDetails;
import com.deco2800.game.components.obstacle.MonsterDispay;
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

/**
 * A screen for monster manual.
 */
public class MonsterMenuScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] MonsterMenuTextures =
            {"images/monster_menu/Monsterbox.jpg",
                    "images/monster_menu/background.png",
                    "images/obstacle_1_new.png",
                    "images/obstacle2_vision2.png",
                    "images/stone1.png",
                    "images/monkey_original.png",
                    "images/Facehugger.png",
                    "images/ufo.png",
                    "images/monster_menu/secret.jpg",
                    "images/monster_menu/plant.png",
                    "images/monster_menu/hugger.png",
                    "images/monster_menu/monkey.png",
                    "images/monster_menu/stone.png",
                    "images/monster_menu/thorns.png",
                    "images/monster_menu/box_covered.png",
                    "images/monster_menu/plant.jpg",
                    "images/monster_menu/monkey.jpg",
                    "images/monster_menu/stone.jpg",
                    "images/monster_menu/hugger.jpg",
                    "images/monster_menu/thorns.jpg",
                    "images/monster_menu/ship.jpg",
                    "images/monster_menu/Facehugger_over.png",
                    "images/monster_menu/monkey_original_over.png",
                    "images/monster_menu/plant_over.png",
                    "images/monster_menu/stone_over.png",
                    "images/monster_menu/ufo_over.png",
                    "images/monster_menu/thorns_over.png",
                    "images/monster_menu/missile_over.png",
                    "images/historyScoreBg.png"


            };
    private final Renderer renderer;
    private final MonsterDispay monsterDispay;
    private final Entity ui;

    /**
     * Generate a monster manual UI.
     *
     * @param game this game
     */
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
        ui = new Entity();
        monsterDispay = new MonsterDispay(game);
        ui.addComponent(monsterDispay).addComponent(new InputDecorator(stage, 10))
                .addComponent(new MonsterDetails())
                .addComponent(new BackgroundSelectionComponent("MonsterMenu"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("MonsterMenu"), 0.5f));
        ServiceLocator.getEntityService().register(ui);
    }

    private void loadAssets() {

        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(MonsterMenuTextures);
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
        resourceService.unloadAssets(MonsterMenuTextures);
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
