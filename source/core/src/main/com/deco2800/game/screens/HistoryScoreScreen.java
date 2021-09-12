package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.gameover.GameOverDisplay;
import com.deco2800.game.components.score.HistoryScoreDisplay;
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

public class HistoryScoreScreen extends ScreenAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private final Renderer renderer;
    private final HistoryScoreDisplay historyScoreDisplay;


    public HistoryScoreScreen(GdxGame game) {
        logger.debug("drawing history score board ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        historyScoreDisplay = new HistoryScoreDisplay(game);
        ui.addComponent(historyScoreDisplay).addComponent(new InputDecorator(stage, 10));
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

    @Override
    public void dispose() {
        renderer.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }
}
