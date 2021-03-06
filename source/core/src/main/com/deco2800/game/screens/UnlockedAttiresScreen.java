package com.deco2800.game.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BackgroundSelectionComponent;
import com.deco2800.game.components.BackgroundSoundComponent;
import com.deco2800.game.components.player.UnlockedAttiresDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.files.meta.BackgroundMusic;
import com.deco2800.game.files.stats.GameRecordUtils;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Screen displaying unlocked attires
 */
public class UnlockedAttiresScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UnlockedAttiresScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    private Entity UIEntity;


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

    /**
     * Load all attires' and achievements' assets
     */

    private void loadAssets() {
        logger.debug("Loading assets");
        ServiceLocator.getResourceService().loadAll();
    }

    /**
     * Unload all attires' and achievements' assets
     */
    private void unloadAssets() {
        logger.debug("Unloading assets");
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

    /**
     * Create UI for displaying unlocked attires
     */
    private void createUI() {
        logger.debug("Creating Unlocked Attires screen UI");
        int goldAchievements = GameRecordUtils.getGoldAchievementsCount();
        Stage stage = ServiceLocator.getRenderService().getStage();

        UIEntity = new Entity();

        UIEntity.addComponent(new UnlockedAttiresDisplay(game, goldAchievements))
                .addComponent(new BackgroundSelectionComponent("UnlockedAttires", "tl"))
                .addComponent(new BackgroundSoundComponent(BackgroundMusic.getSelectedMusic("UnlockedAttires"), 0.5f))
                .addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(UIEntity);
    }


}
